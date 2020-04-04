package sample;

public abstract class Instruction {

    protected short opcode;

    public static Instruction createInstruction(String line, Parser parser) {

        String funcName = line.split(" ")[0];

        if (RFormatInstruction.checkFormat(funcName))
            return new RFormatInstruction(line);
        else if (IFormatInstruction.checkFormat(funcName))
            return new IFormatInstruction(line);
        else if (JFormatInstruction.checkFormat(funcName))
            return new JFormatInstruction(line);

        return null;
    }

    abstract void parseInstruction (String line) throws Exception;

}
