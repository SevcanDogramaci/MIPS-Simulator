package sample;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private File file;
    private ArrayList<String> inputLines;
    private ArrayList<String> clearedLines;
    private Map<String, Integer> labelAddressesMap;

    private ArrayList<Instruction> instructions;

    public Parser(File file) {
        this.file = file;
        readFile();
        clearComments(inputLines);

        instructions = new ArrayList<>();
        labelAddressesMap = new HashMap<>();

        for (String line: clearedLines) {
            instructions.add(Instruction.createInstruction(line, this));
        }
    }

    private void extractLabels(){

        int i = 0;
        String line;

        while (i < this.clearedLines.size()){

            line = this.clearedLines.get(i);

            if(line.contains(":")){
                String labelName = line.substring(0, line.indexOf(":"));
                String instruction;

                this.labelAddressesMap.put(labelName, i);

                if(line.indexOf(":") < line.length() - 1){
                    instruction = line.substring(line.indexOf(":")+1, line.length()-1);
                    this.clearedLines.set(i, instruction);
                    i++;
                }
                else {
                    this.clearedLines.remove(i);
                }
            }
            else{
                i++;
            }
        }
    }

    public int getLabelAddress(String labelName){
        return this.labelAddressesMap.get(labelName);
    }

    private void readFile(){
        // read this file and save into a string.
        try (FileReader reader = new FileReader(this.file);
             BufferedReader br = new BufferedReader(reader)) {

            // read line by line
            String line;
            this.inputLines = new ArrayList<>(); //keeps lines
            while ((line = br.readLine()) != null) {
                this.inputLines.add(line);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }

    private void clearComments(ArrayList<String> lines){

       // boolean commentFlag = false; // for block comments, true when a block comment opened.
        clearedLines = new ArrayList<>();

        for (String line : lines){

            line = line.replace("\t", " ").trim(); // transform into required format.

            // for empty lines.
            if(line.length()< 2) continue;

            // eliminate comment part of the line
            if (line.contains("#")){
                line = line.substring(0, line.indexOf("#")).trim();
            }
            line = line.trim();

            if (line.length()>1 && !line.contains(".")){
                clearedLines.add(line);
            }
        }
    }

    public String getLines() {
        StringBuilder sb = new StringBuilder();
        for (String s: clearedLines){
            sb.append(s);
            sb.append("\n");
            System.out.println(s);
        }
        return sb.toString();
    }
}
