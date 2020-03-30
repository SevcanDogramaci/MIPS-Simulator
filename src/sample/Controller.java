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

            lines = clearComments(lines);

            for (String l : lines) {
                System.out.println(l);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }


    public ArrayList<String> clearComments(ArrayList<String> lines){

        boolean commentFlag = false;

        ArrayList<String> clearedLines = new ArrayList<>();

        for (String line : lines){

            if(line.length()< 2) continue;
            line = line.trim();

            if (commentFlag){  // skips until the end of comment block --> only works for good formatted files.
                if (!line.contains("*/")) continue;
                else {
                    commentFlag = false;
                    line = line.substring(line.indexOf("*/") + 1);
                }
            }

            if (line.startsWith("//")){ // eliminate comment line
                continue;
            }


            if (line.contains("//")){  // eliminate comment part of the line
                line = line.substring(0, line.indexOf("//")).trim();
            }

            if (line.contains("/*")){
                commentFlag = true;

                if (line.startsWith("/*")){
                    //System.out.println(line);
                    continue;
                }else{
                    line = line.substring(0, line.indexOf("/*")+1);
                }
            }
            clearedLines.add(line);
        }

        return clearedLines;

    }

}
