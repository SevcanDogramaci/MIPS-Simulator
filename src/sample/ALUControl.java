package sample;

public class ALUControl {

    public static short getControl(boolean ALUOp1, boolean ALUOp0, short funct) {

        if(!ALUOp1 && !ALUOp0) {
                System.out.println("ADD");
                return ALU.ADD;
        }
        if(ALUOp0) {
            System.out.println("SUB");
            return ALU.SUBTRACT;
        }

        System.out.println("result = " + (funct & 15));

        switch(funct & 63) {
            case 32:
            case 33:
                System.out.println("ADD");
                return ALU.ADD;

            case 34:
            case 35:
                System.out.println("SUB");
                return ALU.SUBTRACT;

            case 36:
            case 12:
                System.out.println("AND");
                return ALU.AND;

            case 37:
            case 11:
                System.out.println("OR");
                return ALU.OR;

            case 39:
                System.out.println("NOR");
                return ALU.NOR;
            case 38:
            case 14:
                System.out.println("XOR");
                return ALU.XOR;

            case 42:
            case 43:
                System.out.println("SLT");
                return ALU.SLT;

            case 0:
            case 4:
                System.out.println("SLL");
                return ALU.SLL;

            case 2:
            case 6:
                System.out.println("SRL");
                return ALU.SRL;

            case 3:
            case 7:
                System.out.println("SRA");
                return ALU.SRA;
        }

        System.out.println("NO RESULT");
        assert false;
        return 0;
    }
}
