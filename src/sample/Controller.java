package sample;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.util.Optional;
import java.util.List;
import java.io.File;

public class Controller {

    @FXML private Rectangle rectangle;
    @FXML private Button btnRun, btnStep, btnChoose, btnReset;
    @FXML private TextArea assemblyCodeArea;
    @FXML private Label lblLine;
    @FXML private TableView<Register> rTable;
    @FXML private TableColumn<Register, Integer> rNo, rValue;
    @FXML private TableColumn<Register, String > rName;
    @FXML private TableColumn<Data, String > textSegAddress, textSegValue, sAddress, sValue;
    @FXML private TableView<Data> textSegTable, sTable;

    private Parser parser;
    private Processor processor;
    private double rectStartingY;

    @FXML
    private void chooseRect(){ assemblyCodeArea.requestFocus(); }

    @FXML
    private void handleRect(){
        if (btnStep.isDisabled()) {
            int position = assemblyCodeArea.getCaretPosition();
            String text = assemblyCodeArea.getText();
            //setLineNumber(text);
            int count = 0;
            for (int i = 0; i < position; i++) {
                if (i < text.length() && text.charAt(i) == '\n')
                    count++;
            }
            setRectY(count);
        }
    }

    private void setRectY(int count){ if(count < 28) rectangle.setY(rectStartingY + count*19); }
    private void setLineNumber(String text){
        System.out.println("I was here: " + text);
        StringBuilder sb = new StringBuilder();
        int count = 1;
        sb.append(count).append("\n");
        for(int i=0; i < text.length(); i++)
        {    if(text.charAt(i) == '\n')
                sb.append(++count).append("\n");
        }

        lblLine.setText(sb.toString());
    }

    @FXML
    public void initialize(){
        setUpAssemblyCodeAreas();
        setUpTablePlaceholders();
        setupRegisterTable();

        Tooltip runTip = new Tooltip("Run");
        runTip.setShowDelay(Duration.millis(10));
        Tooltip.install(btnRun, runTip);

        Tooltip chooseTip = new Tooltip("Choose File");
        chooseTip.setShowDelay(Duration.millis(10));
        Tooltip.install(btnChoose, chooseTip);

        Tooltip resetTip = new Tooltip("Reset");
        resetTip.setShowDelay(Duration.millis(10));
        Tooltip.install(btnReset, resetTip);

        Tooltip stepTip = new Tooltip("Step");
        stepTip.setShowDelay(Duration.millis(10));
        Tooltip.install(btnStep, stepTip);

    }

    private void setUpAssemblyCodeAreas() {
        assemblyCodeArea.addEventHandler(KeyEvent.KEY_RELEASED, keyEvent -> handleRect());
        rectStartingY = rectangle.getY();
        assemblyCodeArea.textProperty().addListener((observableValue, s, t1) -> setLineNumber(t1));
        rectangle.widthProperty().bind(assemblyCodeArea.widthProperty().subtract(assemblyCodeArea.getPadding().getRight()));
    }

    private void setUpTablePlaceholders() {
        sTable.setPlaceholder(new Label("Stack is empty"));
        textSegTable.setPlaceholder(new Label("No instruction in memory"));
    }

    private void setupRegisterTable() {
        rNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        rValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        rName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rTable.setItems(RegisterFile.getRegisters());
    }

    private void setupTextSegmentTable() throws Exception {
        textSegAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        textSegValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        textSegTable.setItems(InstructionMemoryFile.getInstructions());
    }

    private void setupStackTable(ObservableList<Data> data) {
        sAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        sValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        sTable.setItems(data);
    }

    @FXML
    public void runPressed(ActionEvent event) throws Exception {

        if(assemblyCodeArea.getText().equals("") && parser == null) {
            return;
        }
        setRectY(0);
        btnRun.setDisable(true);
        btnStep.setDisable(false);

        if(assemblyCodeArea.editableProperty().getValue()) {
            parser = new Parser(assemblyCodeArea.getText());
            assemblyCodeArea.setText(parser.getLines());
        }

        try{
            parser.createInstructions();
            assemblyCodeArea.setText(parser.getLines());

            List<Instruction> instructions = parser.getInstructions();

            processor = new Processor();
            processor.loadInstructionsToMemory(instructions);
            setupTextSegmentTable();
            setupStackTable(processor.getStackData());
            selectLine(0);
        } catch (Exception e){
            showAlertDialog("Error", e.getMessage(), true);
        }

    }

    private void startAgain () throws Exception {
        List<Instruction> instructions = parser.getInstructions();
        processor = new Processor();
        processor.loadInstructionsToMemory(instructions);
        rTable.refresh();
        setupTextSegmentTable();
        setupStackTable(processor.getStackData());
        selectLine(0);
    }

    @FXML
    public void onStep(ActionEvent event) throws Exception {
        try {
            if (!processor.isDone()) {
                processor.step();
                rTable.refresh();
                setupStackTable(processor.getStackData());
                selectLine(processor.getIndex());
                setRectY(processor.getIndex());
            } else {
                setRectY(0);
                showAlertDialog("The program has finished!", "Do you want to run again ?", false);
            }
        } catch (Exception e){
            showAlertDialog("Problem at line " + (processor.getIndex() + 1), e.getMessage(), false);
        }
    }

    private int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    private void selectLine(int lineNum){
        textSegTable.getSelectionModel().select(lineNum);
        String txt = assemblyCodeArea.getText();

        int start = lineNum == 0 ? 0 : ordinalIndexOf(txt, "\n", lineNum - 1);
        int end = ordinalIndexOf(txt, "\n", lineNum );

        assemblyCodeArea.selectRange(start, end);
    }

    @FXML
    public void chooseFilePressed(ActionEvent event) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./")); // set initial directory to cwd.

        // filter file formats that can be selected.
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Assembly Files", "*.s")
                ,new FileChooser.ExtensionFilter("Assembly Files", "*.asm")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile == null) return;

        assemblyCodeArea.setEditable(false);

        parser = new Parser(selectedFile);

        assemblyCodeArea.setText(parser.getLines());
    }

    @FXML
    public void resetApplication() {
        setRectY(0);
        assemblyCodeArea.setText("");
        assemblyCodeArea.setEditable(true);

        btnRun.setDisable(false);
        btnStep.setDisable(true);
        btnChoose.setDisable(false);

        parser = null;
        textSegTable.setItems(null);

        RegisterFile.resetData();
        rTable.refresh();

        try {
            MemoryFile.resetData();
        } catch (Exception e){

        }

        sTable.setItems(null);
    }

    public void showAlertDialog(String header, String content, boolean isResetApplication) throws Exception {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(header);
        alert.setContentText(content);

        ButtonType btnExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType btnOK =
                isResetApplication ?
                        new ButtonType("Reset", ButtonBar.ButtonData.OK_DONE):
                        new ButtonType("Start Again", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(btnOK, btnExit);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btnOK) {
            if (isResetApplication)
                resetApplication();
            else
                startAgain();
        } else
            System.exit(0);
    }
}
