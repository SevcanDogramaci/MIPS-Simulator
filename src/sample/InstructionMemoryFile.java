package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class InstructionMemoryFile {
    private static Instruction[] instructions;

    public void load(List<Instruction> instructions) {
        this.instructions = new Instruction[instructions.size()];

        for(int i = 0; i <instructions.size(); i++){
            instructions.get(i).setAddress(i);
        }

        this.instructions = instructions.toArray(this.instructions);
    }

    public Instruction fetch(ProgramCounter pc) {
        return instructions[pc.get()/4];
    }

    public int length() {
        return instructions.length * 4;
    }

    public static ObservableList<Instruction> getInstructions() {
        return FXCollections.observableArrayList(instructions);
    }
}
