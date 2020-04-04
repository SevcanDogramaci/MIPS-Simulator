package sample;

public abstract class Instruction {

    protected short opcode;
    protected int index;

    public static Instruction createInstruction(String line, int i, Parser parser) {

        String funcName = line.split(" ")[0];

        if (RFormatInstruction.checkFormat(funcName))
            return new RFormatInstruction(line, i);
        else if (IFormatInstruction.checkFormat(funcName))
            return new IFormatInstruction(line, i, parser);
        else if (JFormatInstruction.checkFormat(funcName))
            return new JFormatInstruction(line, i, parser);

        return null;
    }

    abstract void parseInstruction (String line) throws Exception;

}
