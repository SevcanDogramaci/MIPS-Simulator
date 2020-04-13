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

    public int cycle(boolean read, boolean write, int index, int writeValue){

        if (read){
            return get(index, 4);
        }
        else if (write){
            set(index, writeValue, 4);
        }

        return 0;
    }

    private void set(int index, int value, int type){
        byte row[] = data[index >> 2];
        byte offset = (byte) (index % 4);

        for (int i = offset; i < offset + type; i++) {
            row[i] = (byte) (value >> (type-1-i) * 8);
        }

    }

    private int get(int index, int type){
        byte[] row = data[index >> 2];
        byte offset = (byte) (index % 4);
        int ret = 0;

        for (int i = offset; i < offset + type; i++) {
            ret += (byte) (row[i] << (type-1-i) * 8);
        }

        return ret;
    }


    public String getMemoryData (){
        StringBuilder sb = new StringBuilder();

        sb.append("Address\tData\n-------\t-------\n");

        System.out.println("");

        for (int i = stackPointer.getValue() >> 2; i < data.length; i++) {
            //sb.append(Integer.toHexString(i << 2)).append("\t").append(Integer.toHexString(data[i]));
            sb.append(i << 2).append("\t\t");

            byte row[] = data[i];

            for (int j = 0; j < row.length; j++) {
                sb.append(row[j]).append(" ");
            }

            sb.append("\n");
        }

        return sb.toString();
    }


}
