package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.chrono.IsoChronology;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private Button btnChooseFile, btnRun, btnStep;
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
        btnRun.setDisable(true);
        btnStep.setDisable(false);
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
    public void onStep(ActionEvent event){
        selectLine(processor.getIndex());
        processor.step();
        rTable.refresh();
    }

    public int ordinalIndexOf(String str, String substr, int n) {
        int pos = -1;
        do {
            pos = str.indexOf(substr, pos + 1);
        } while (n-- > 0 && pos != -1);
        return pos;
    }

    public void selectLine(int lineNum){
        String txt = assemblyCodeArea.getText();
        int start = lineNum == 0 ? 0 : ordinalIndexOf(txt, "\n", lineNum  );
        int end = ordinalIndexOf(txt, "\n", lineNum + 1);
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
