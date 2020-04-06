package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class Controller {

    @FXML
    private Button btnChooseFile, btnRun;
    @FXML private TextArea assemblyCodeArea;
    @FXML private TableView<Register> rTable;
    @FXML private TableColumn<Register, Integer> rNo;
    @FXML private TableColumn<Register, Integer> rValue;
    @FXML private TableColumn<Register, String > rName;

    private Parser parser;
    private Processor processor;

    @FXML
    public void initialize(){
        setupRegisterTable();
    }

    private void setupRegisterTable() {

        rNo.setCellValueFactory(new PropertyValueFactory<Register, Integer>("no"));
        rValue.setCellValueFactory(new PropertyValueFactory<Register, Integer>("value"));
        rName.setCellValueFactory(new PropertyValueFactory<Register, String >("name"));
        rTable.setItems(RegisterFile.getRegisters());
    }

    @FXML
    public void runPressed(ActionEvent event) throws Exception {
        if(assemblyCodeArea.editableProperty().getValue()){
            parser = new Parser(assemblyCodeArea.getText());
        } else {
            parser.createInstructions();
        }

        List<Instruction> instructions = parser.getInstructions();
        processor = new Processor();
        processor.loadInstructionsToMemory(instructions);
    }

    @FXML
    public void onStep(ActionEvent event) throws Exception {
        selectLine(processor.getIndex());

        if(!processor.isDone()){
            processor.step();
            rTable.refresh();
        }
        else
            alertProgramFinish(event);
    }

    private void alertProgramFinish(ActionEvent event) throws Exception {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText("The program has finished!");
        alert.setContentText("Do you want to run again ?");

        ButtonType btnExit = new ButtonType("Exit", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType btnStartAgain = new ButtonType("Start Again", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(btnStartAgain, btnExit);

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == btnStartAgain)
            runPressed(event);
        else
            System.exit(0);

    }

    public int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    public void selectLine(int lineNum){
        System.out.println("line num : " + lineNum);
        String txt = assemblyCodeArea.getText();
        int start = lineNum == 0 ? 0 : ordinalIndexOf(txt, "\n", lineNum - 1);
        int end = ordinalIndexOf(txt, "\n", lineNum);
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

}
