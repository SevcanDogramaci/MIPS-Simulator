package sample;

import java.util.List;

public class InstructionMemoryFile {
    private Instruction[] instructions;

    public void load(List<Instruction> instructions) {
        this.instructions = new Instruction[instructions.size()];
        this.instructions = instructions.toArray(this.instructions);
    }

    public Instruction fetch(ProgramCounter pc) {

        return instructions[pc.get()/4];
    }

    public int length() {
        return instructions.length * 4;
    }
}
