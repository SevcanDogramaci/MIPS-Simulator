package sample;

public class ALU {
    public static final short AND = 0;
    public static final short OR = 1;
    public static final short ADD = 2;
    public static final short SUBTRACT = 6;
    public static final short SLT = 7;
    public static final short NOR = 12;

    private int out;
    private boolean zero;

    public void setOperation(short control, int srcv, int rsv) {
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
        }
    }

    public int getOut() {
        return out;
    }


    public boolean isZero() {
        return zero;
    }
}


/*

public class ALU {

    public static Register[] registers;

    static {
        registers = new Register[32];
        for (int i = 0; i < 32; i++)
            registers[i] = new Register(i);
    }




    public static void a(){
        for (int i = 0; i < 32; i++)
            registers[i].setValue(registers[i].getValue() + 1);
    }


}
*/