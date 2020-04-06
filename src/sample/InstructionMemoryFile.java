package sample;

import java.util.List;

public class InstructionMemoryFile {
    private Instruction[] instructions;

    public void load(List<Instruction> instructions) {
        this.instructions = new Instruction[instructions.size()];
        this.instructions = instructions.toArray(this.instructions);
    }

    public Instruction fetch(ProgramCounter pc) {
        System.out.println(instructions[pc.get()/4]);
        System.out.println(pc.get()/4);
        return instructions[pc.get()/4];
    }

    public int length() {
        return instructions.length * 4;
    }
}
