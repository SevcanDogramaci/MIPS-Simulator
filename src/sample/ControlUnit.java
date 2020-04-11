package sample;

public class ControlUnit {

    private boolean RegDst; // rt or rd for write
    private boolean Branch;
    private boolean BranchNotEqual;
    private boolean Jump;
    private boolean JumpReg;
    private boolean MemRead;
    private boolean MemtoReg;
    private boolean ALUOp1;
    private boolean ALUOp0;
    private boolean MemWrite;
    private boolean ALUsrc;  // reg2 or immediate
    private boolean RegWrite;

    public ControlUnit(Instruction instruction) {

        if (instruction.isRFormat()){
//<<<<<<< HEAD
            if (instruction.getFunction() == 8){
                //System.out.println(instruction.getSourceReg().getName());
                System.out.println(instruction.getLine());
                JumpReg = true;
            } else if(instruction.getFunction() == 9){
                JumpReg = true;
                RegWrite = true;
                ALUOp0 = true;
                RegDst = true;
            } else{
                RegDst = true;
                RegWrite = true;
                ALUOp1 = true;
            }
/*=======
            if(instruction.getFunction() == 8){ // jr
                JumpReg = true;
                return;
            }
            else if(instruction.getFunction() == 9){ // jalr
                JumpReg = true;
            }
            RegDst = true;
            RegWrite = true;
            ALUOp1 = true;
>>>>>>> fc6e8c3381c697e86b9bf9f40d8988961fcfa58c*/
        }

        else if (instruction.isIFormat()){
            System.out.println("Immediate : " + instruction.immediate);
            if(instruction.opcode > 7 && instruction.opcode < 10){
                System.out.println("Here1 " + instruction.opcode);
                ALUsrc = true;
                RegWrite = true;
            }
            else if(instruction.opcode > 9 && instruction.opcode < 15){
                System.out.println("Here2 " + instruction.opcode);
                ALUOp1 = true;
                ALUsrc = true;
                RegWrite = true;
            }
            else if (instruction.opcode >= 32 && instruction.opcode <= 37){ // lw
                MemRead = true;
                MemtoReg = true;
                RegWrite = true;
                ALUsrc = true;
            }else if(instruction.opcode == 43){ // sw
                MemWrite = true;
                ALUsrc = true;
            }
            else if (instruction.opcode == 4){ // beq
                Branch = true;
                ALUOp0 = true;
            }
            else if (instruction.opcode == 5){ // bne
                BranchNotEqual = true;
                ALUOp0 = true;
            }
        }

        else if (instruction.isJFormat()){
            Jump = true;
            ALUOp0 = true;
            if (instruction.opcode == 3){
                RegWrite = true;
            }
        }


    }

    public boolean isRegDst() {
        return RegDst;
    }

    public boolean isBranch() {
        return Branch;
    }

    public boolean isBranchNotEqual() {
        return BranchNotEqual;
    }

    public boolean isJump() {
        return Jump;
    }

    public boolean isJumpReg() {
        return JumpReg;
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
