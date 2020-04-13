package sample;

public class MemoryFile {

    public static final int STACK_START = 3996;
    private Register stackPointer;

    private int data[];

    public MemoryFile(int size){
        stackPointer = RegisterFile.getRegister("sp");
        data = new int[size];
    }

    public void resetData(){
        for (int i = 0; i < data.length; i++) {
            data[i] = 0;
        }
    }

    public int cycle(boolean read, boolean write, int index, int writeValue){

        if (read){
            return get(index);
        }
        else if (write){
            set(index, writeValue);
        }

        return 0;
    }

    private void set(int index, int value){
        data[index >> 2] = value;
    }

    private int get(int index){
        return data[index >> 2];
    }

    public int[] getData(){
        return data.clone();
    }

    public String getMemoryData (){
        StringBuilder sb = new StringBuilder();

        sb.append("Address\tData\n-------\t-------\n");

        System.out.println("");

        for (int i = stackPointer.getValue() >> 2; i < data.length; i++) {
            //sb.append(Integer.toHexString(i << 2)).append("\t").append(Integer.toHexString(data[i]));
            sb.append(i << 2).append("\t\t").append(data[i]).append("\n");
        }

        return sb.toString();
    }


}
