package sample;

public abstract class Instruction {

    protected short opcode;
    protected int index;
    protected Register sourceReg1, sourceReg2, destinationReg;

    public static Instruction createInstruction(String line, int i, Parser parser) throws Exception {

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

    public boolean isRegS1 () {
        return sourceReg1 != null;
    }

    public boolean isRegS2 () {
        return sourceReg1 != null;
    }

    public boolean isRegDest () {
        return destinationReg != null;
    }

    public boolean isRFormat(){
        return false;
    }

    public boolean isIFormat(){
        return false;
    }

    public boolean isJFormat(){
        return false;
    }
}
