package sample;

public class ALU {
    public static final short AND = 0;
    public static final short OR = 1;
    public static final short ADD = 2;
    public static final short SUBTRACT = 6;
    public static final short SLT = 7;
    public static final short NOR = 12;
    public static final short SLL = 13;
    public static final short SRL = 14;
    public static final short SRA = 15;

    private int out;
    private boolean zero;

    public void setOperation(short control, int srcv, int rsv, int shamt) {
        zero = false;
        out = 0;

        switch(control) {
            case ADD:
                out =  rsv + srcv;
                break;
            case SUBTRACT:
                out = rsv - srcv;
                if(out == 0) {
                    zero = true;
                }
                break;
            case AND:
                out = rsv & srcv;
                break;
            case OR:
                out = rsv | srcv;
                break;
            case NOR:
                out = ~(rsv | srcv);
                break;
            case SLT:
                out = rsv < srcv ? 1 : 0;
                break;
            case SLL:
                out = srcv << shamt;
                System.out.println("OUT : " + out);
                break;
            case SRL:
                out = srcv >>> shamt;
                System.out.println("OUT : " + out);
                break;
            case SRA:
                out = srcv >> shamt;
                System.out.println("OUT : " + out);
        }
    }

    public int getOut() {
        return out;
    }

    public boolean isZero() {
        return zero;
    }
}
