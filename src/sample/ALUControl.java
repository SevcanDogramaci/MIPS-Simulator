package sample;

public class ALUControl {

    public static short getControl(boolean ALUOp1, boolean ALUOp0, short funct) {

        if(!ALUOp1 && !ALUOp0) {
                return ALU.ADD;
        }
        if(ALUOp0) {
            return ALU.SUBTRACT;
        }

        switch(funct & 63) {
            case 32:
            case 33:
                return ALU.ADD;

            case 34:
            case 35:
                return ALU.SUBTRACT;

            case 36:
            case 12:
                return ALU.AND;

            case 37:
            case 11:
                return ALU.OR;

            case 39:
                return ALU.NOR;
            case 38:
            case 14:
                return ALU.XOR;

            case 42:
            case 43:
                return ALU.SLT;

            case 0:
            case 4:
            case 15:
                return ALU.SLL;

            case 2:
            case 6:
                return ALU.SRL;

            case 3:
            case 7:
                return ALU.SRA;
        }

        assert false;
        return 0;
    }
}
