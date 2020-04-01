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
import java.util.ArrayList;

public class Controller {

    @FXML
    private Button btnChooseFile;
    @FXML private TextArea assemblyCodeArea;
    @FXML private TableView<Register> rTableLeft;
    @FXML private TableColumn<Register, String> rNameLeft;
    @FXML private TableColumn<Register, Integer> rValueLeft;

    @FXML
    public void initialize(){
        setupRegisterTable();
    }

    private void setupRegisterTable() {
        rNameLeft.setCellValueFactory(new PropertyValueFactory<Register, String>("name"));
        rValueLeft.setCellValueFactory(new PropertyValueFactory<Register, Integer>("value"));
        rTableLeft.setItems(ALU.getRegisters());
    }

    @FXML
    public void chooseFilePressed(ActionEvent event){
        ALU.a();
        rTableLeft.refresh();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./")); // set initial directory to cwd.

        // filter file formats that can be selected.
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Assembly Files", "*.s")
                ,new FileChooser.ExtensionFilter("Assembly Files", "*.asm")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile == null) return;

        Parser parser = new Parser(selectedFile);

        assemblyCodeArea.setText(parser.getLines());
    }

}
