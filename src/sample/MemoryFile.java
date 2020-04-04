package sample;

public class MemoryFile {

    private int data[];

    public MemoryFile(int size){
        data = new int[size];
    }

    private void resetData(){
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
        data[index] = value;
    }

    private int get(int index){
        return data[index];
    }

    public int[] getData(){
        return data.clone();
    }
}
