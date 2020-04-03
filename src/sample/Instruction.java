package sample;

public abstract class Instruction {

    private short opcode;

    public static Instruction createInstruction (String line) {

        return null;
    }

    abstract void parseInstruction (String line);

}
