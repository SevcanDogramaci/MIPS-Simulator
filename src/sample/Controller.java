package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML private Button btnRun, btnStep, btnChoose;
    @FXML private TextArea assemblyCodeArea, sTable;
    @FXML private TableView<Register> rTable;
    @FXML private TableColumn<Register, Integer> rNo;
    @FXML private TableColumn<Register, Integer> rValue;
    @FXML private TableColumn<Register, String > rName;
    @FXML private TableColumn<Instruction, Short > textSegAddress;
    @FXML private TableColumn<Instruction, String > textSegValue;
    @FXML private TableView<Instruction> textSegTable;

    private Parser parser;
    private Processor processor;

    @FXML
    public void initialize(){
        setupRegisterTable();
    }

    private void setupRegisterTable() {
        rNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        rValue.setCellValueFactory(new PropertyValueFactory<>("value"));
        rName.setCellValueFactory(new PropertyValueFactory<>("name"));
        rTable.setItems(RegisterFile.getRegisters());
    }

    private void setupTextSegmentTable() {
        textSegAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        textSegValue.setCellValueFactory(new PropertyValueFactory<>("machineCode"));
        textSegTable.setItems(InstructionMemoryFile.getInstructions());
    }

    @FXML
    public void runPressed(ActionEvent event) {

        if(assemblyCodeArea.getText().equals("") && parser == null) {
            return;
        }

        btnRun.setDisable(true);
        btnStep.setDisable(false);

        if(assemblyCodeArea.editableProperty().getValue()) {
            parser = new Parser(assemblyCodeArea.getText(), this);
            assemblyCodeArea.setText(parser.getLines());
        }

        try{
            parser.createInstructions();
            assemblyCodeArea.setText(parser.getLines());

            List<Instruction> instructions = parser.getInstructions();

            processor = new Processor();
            processor.loadInstructionsToMemory(instructions);
            setupTextSegmentTable();
            selectLine(0);
        }catch (Exception e){
            showAlertDialog("Error", e.getMessage(), true);
        }

    }

    private void startAgain () {
        List<Instruction> instructions = parser.getInstructions();
        processor = new Processor();
        processor.loadInstructionsToMemory(instructions);
        rTable.refresh();
        setupTextSegmentTable();
        selectLine(0);
    }

    @FXML
    public void onStep(ActionEvent event) {
        try {
            if (!processor.isDone()) {
                processor.step();
                rTable.refresh();
                sTable.setText(processor.getStackData());
                selectLine(processor.getIndex());
            } else
                alertProgramFinish(event);
        } catch (Exception e){
            showAlertDialog("Problem at line " + (processor.getIndex() + 1), e.getMessage(), false);
        }
    }

    private void alertProgramFinish(ActionEvent event) throws Exception {

        showAlertDialog("The program has finished!", "Do you want to run again ?", false);
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

        assemblyCodeArea.setText("");
        assemblyCodeArea.setEditable(true);

        btnRun.setDisable(false);
        btnStep.setDisable(true);
        btnChoose.setDisable(false);

        parser = null;
        textSegTable.setItems(null);

        RegisterFile.resetData();
        rTable.refresh();
        sTable.setText("");
    }

    public void showAlertDialog(String header, String content, boolean isResetApplication) {
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
