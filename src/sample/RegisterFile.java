package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RegisterFile {

    private Register rr1, rr2, wr;
    private static ArrayList<Register> registers = new ArrayList<>();
    private static String[] registerNames = {
            "zero",
            "at",
            "v0",
            "v1",
            "a0",
            "a1",
            "a2",
            "a3",
            "t0",
            "t1",
            "t2",
            "t3",
            "t4",
            "t5",
            "t6",
            "t7",
            "s0",
            "s1",
            "s2",
            "s3",
            "s4",
            "s5",
            "s6",
            "s7",
            "t8",
            "t9",
            "k0",
            "k1",
            "gp",
            "sp",
            "fp",
            "ra"
        };

    static {
        int registerNo = 0;
        for (String registerName : registerNames) {
            registers.add(new Register(registerNo ++, registerName));
        }
    }
    
    public static void setRegisterData(int index, int dataValue){
        registers.get(index).setValue(dataValue);
    }

    public static Register getRegister(String name) {
        for (Register r: registers) {
            if (r.getName().equalsIgnoreCase(name))
                return r;
        }
        return null;
    }

    public static ObservableList<Register> getRegisters(){
        return FXCollections.observableArrayList(registers);
    }

    public static void resetData(){
        for(Register register : registers) {
            register.setValue(0);
            if (register.getName().equals("sp")) register.setValue(MemoryFile.STACK_START);
        }
    }

    public void setRegisters(Register rr1, Register rr2, Register wr) {
        this.rr1 = rr1;
        this.rr2 = rr2;
        this.wr = wr;
    }

    public int readData1() { return  rr1 == null ? 0 : rr1.getValue(); }

    public int readData2() { return  rr2 == null ? 0 : rr2.getValue(); }

    public void write(boolean regWrite, int dataValue) {
        if(regWrite) {
            if(wr != null)
                setRegisterData(wr.getNo(), dataValue);
        }
    }

    public int[] getData(){
        int[] registerData = new int[registers.size()];

        for(int i = 0; i < registers.size(); i++) {
            registerData[i] = registers.get(i).getValue();
        }
        return registerData;
    }
}
