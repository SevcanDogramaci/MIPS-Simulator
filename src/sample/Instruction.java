package sample;

public abstract class Instruction {

    protected short opcode;

    public static Instruction createInstruction (String line) {

        return null;
    }

    abstract void parseInstruction (String line) throws Exception;

}
