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
            case 1:
                System.out.println("ADD");
                return ALU.ADD;

            case 2:
            case 3:
                System.out.println("SUB");
                return ALU.SUBTRACT;

            case 4:
            case 12:
                System.out.println("AND");
                return ALU.AND;

            case 5:
            case 11:
                System.out.println("OR");
                return ALU.OR;

            case 7:
                System.out.println("NOR");
                return ALU.NOR;

            case 10:
                System.out.println("SLT");
                return ALU.SLT;
        }

        System.out.println("NO RESULT");
        assert false;
        return 0;
    }
}
