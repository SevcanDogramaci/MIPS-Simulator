package sample;

public abstract class Instruction {

    protected short opcode;
    protected int index, shiftAmount, immediate;
    protected Register sourceReg, targetReg, destinationReg;
    protected String line;

    public static Instruction createInstruction(String line, int i, Parser parser) throws Exception {

        String funcName = line.split(" ")[0].replace("\n", "");

        if (IFormatInstruction.checkFormat("ori"))
             System.out.println("I " );
        if (RFormatInstruction.checkFormat("add"))
            System.out.println("R");
        else if (JFormatInstruction.checkFormat("jal"))
            System.out.println("J");

        if (RFormatInstruction.checkFormat(funcName))
            return new RFormatInstruction(line, i);
        else if (IFormatInstruction.checkFormat(funcName))
            return new IFormatInstruction(line, i, parser);
        else if (JFormatInstruction.checkFormat(funcName))
            return new JFormatInstruction(line, i, parser);

        System.out.println("I return null: " + line + " " + funcName);
        return null;
    }

    abstract void parseInstruction (String line) throws Exception;

    public boolean isRegSource() {
        return sourceReg != null;
    }

    public boolean isRegTarget() {
        return targetReg != null;
    }

    public boolean isRegDest () {
        return destinationReg != null;
    }

    public Register getSourceReg() { return sourceReg; }

    public Register getTargetReg() { return targetReg; }

    public Register getDestinationReg() { return destinationReg; }

    public short getFunction() { return opcode; }

    public int getShiftAmount() { return shiftAmount; }

    public int getImmediate() { return immediate; }

    public boolean isRFormat(){
        return this instanceof RFormatInstruction;
    }

    public boolean isIFormat(){
        return this instanceof IFormatInstruction;
    }

    public boolean isJFormat(){
        return this instanceof JFormatInstruction;
    }

    public String getLine() {
        return line;
    }
}
