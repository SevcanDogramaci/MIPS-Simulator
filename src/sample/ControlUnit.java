package sample;

public class ControlUnit {

    private boolean RegDst; // rt or rd for write
    private boolean Branch;
    private boolean Jump;
    private boolean MemRead;
    private boolean MemtoReg;
    private boolean ALUOp1;
    private boolean ALUOp0;
    private boolean MemWrite;
    private boolean ALUsrc;  // reg2 or immediate
    private boolean RegWrite;

    public ControlUnit(Instruction instruction) {

        if (instruction.isRFormat()){
            RegDst = true;

        }


    }

    public boolean isRegDst() {
        return RegDst;
    }

    public boolean isBranch() {
        return Branch;
    }

    public boolean isJump() {
        return Jump;
    }

    public boolean isMemRead() {
        return MemRead;
    }

    public boolean isMemtoReg() {
        return MemtoReg;
    }

    public boolean isALUOp1() {
        return ALUOp1;
    }

    public boolean isALUOp0() {
        return ALUOp0;
    }

    public boolean isMemWrite() {
        return MemWrite;
    }

    public boolean isALUsrc() {
        return ALUsrc;
    }

    public boolean isRegWrite() {
        return RegWrite;
    }
}
