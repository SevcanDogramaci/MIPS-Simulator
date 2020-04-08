package sample;

public class ALUControl {

    public static short getControl(boolean ALUOp1, boolean ALUOp0, short funct) {

        if(!ALUOp1 && !ALUOp0) {
            return ALU.ADD;
        }
        if(ALUOp0) {
            return ALU.SUBTRACT;
        }

        System.out.println("result = " + (funct & 15));

        switch(funct & 15) {
            case 0:
                return ALU.ADD;
            case 2:
                return ALU.SUBTRACT;
            case 12:
                System.out.println("AND");
                return ALU.AND;
            case 11:
                System.out.println("OR");
                return ALU.OR;
            case 7:
                return ALU.NOR;
            case 10:
                return ALU.SLT;
        }

        System.out.println("NO RESULT");
        assert false;
        return 0;
    }
}
