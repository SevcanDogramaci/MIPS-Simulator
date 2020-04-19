package sample;

public class MemoryFile {

    public static final int STACK_START = 4000;
    private Register stackPointer;

    //private int data[];

    private byte data[][];

    public MemoryFile(int size){
        stackPointer = RegisterFile.getRegister("sp");

        data = new byte[1000][4];
        // data = new int[size];
    }

    public void resetData(){
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < 4; j++) {
                data[i][j] = 0;
            }
        }
    }

    public int cycle(boolean read, boolean write, int index, int writeValue, int accessLength, boolean signed){

        if (read){
            return get(index, accessLength, signed);
        }
        else if (write){
            set(index, writeValue, accessLength);
        }

        return 0;
    }

    private void set(int index, int value, int type){
        byte[] row = data[index >> 2];
        byte offset = (byte) (index % 4);

        int j = 0;
        for (int i = offset; i < offset + type; i++, j++) {
            row[i] = (byte) (value >> (type-1-j) * 8);
        }
    }

    private int get(int index, int type, boolean signed){
        byte[] row = data[index >> 2];
        byte offset = (byte) (index % 4);
        int ret = 0;

        int j = 0;
        for (int i = offset; i < offset + type; i++, j++) {
            if(i == offset && signed)
                ret += (row[i]<< (type-1-i) * 8);
            else
                ret += (unsignedToBytes(row[i])<< (type-1-j) * 8);
        }

        return ret;
    }


    public String getMemoryData (){
        StringBuilder sb = new StringBuilder();

        sb.append("Address\t\tData\n---------\t\t-------------------------------\n");


        for (int i = stackPointer.getValue() >> 2; i < data.length; i++) {
            sb.append(String.format("%6d", i << 2)).append("\t\t");

            byte[] row = data[i];

            for (int j = 0; j < row.length; j++) {

                sb.append(String.format("%8s", Integer.toBinaryString(row[j] & 0xFF)).replace(' ', '0'))
                        .append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }

    public static int unsignedToBytes(byte b) {
        return b & 0xFF;
    }


}
