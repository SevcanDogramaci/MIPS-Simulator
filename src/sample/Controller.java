package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

public class Controller {

    @FXML
    private Button btnChooseFile;

    @FXML
    public void chooseFilePressed(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("./")); // set initial directory to cwd.

        // filter file formats that can be selected.
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Assembly Files", "*.s")
                ,new FileChooser.ExtensionFilter("Assembly Files", "*.asm")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile == null) return;

        // read this file and save into a string.
        try (FileReader reader = new FileReader(selectedFile);
             BufferedReader br = new BufferedReader(reader)) {

            // read line by line
            String line;
            ArrayList<String> lines = new ArrayList<>(); //keeps lines

            while ((line = br.readLine()) != null) {
                lines.add(line);
                System.out.println(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }


    public void clearComments(ArrayList<String> lines){

        boolean commentFlag = false;

        ListIterator<String> iterator
                = lines.listIterator();

        while (iterator.hasNext()) {


            String line = iterator.next();
            line = line.trim();

            if (commentFlag){  // skips until the end of comment block --> only works for good formatted files.

                if (!line.contains("*/")) {
                    iterator.remove();
                    continue;
                }
                else commentFlag = false;
            }

            if (line.startsWith("//")){ // eliminate comment line
                iterator.remove();
                continue;
            }

            if (line.contains("//")){  // eliminate comment part of the line
                iterator.set(line.substring(0, line.indexOf("//")).trim());
            }

            if (line.contains("/*")){
                commentFlag = true;

            }

        }

    }

}
