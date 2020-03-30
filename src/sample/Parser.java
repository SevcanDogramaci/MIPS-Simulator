package sample;

import java.util.ArrayList;

public class Parser {

    private ArrayList<String> inputLines;
    private ArrayList<String> clearedLines;

    public Parser(ArrayList<String> inputLines) {
        this.inputLines = inputLines;
        clearComments(inputLines);

    }

    private void clearComments(ArrayList<String> lines){

        boolean commentFlag = false; // for block comments, true when a block comment opened.
        clearedLines = new ArrayList<>();

        for (String line : lines){

            line = line.replace("\t", " ").trim(); // transform into required format.

            // for empty lines.
            if(line.length()< 2) continue;


            // skips until the end of comment block --> only works for good formatted files.
            if (commentFlag){
                if (!line.contains("*/")) continue;
                else {
                    commentFlag = false;
                    line = line.substring(line.indexOf("*/") + 1);
                }
            }

            // eliminate comment line
            if (line.startsWith("//")){
                continue;
            }

            // eliminate comment part of the line
            if (line.contains("//")){
                line = line.substring(0, line.indexOf("//")).trim();
            }

            // eliminate comment blocks.
            if (line.contains("/*")){
                commentFlag = true;
                line = line.substring(0, line.indexOf("/*")+1);
            }

            line = line.trim();

            if (line.length()>1){
                clearedLines.add(line);
            }
        }
    }
}
