package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class RegisterFile {

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

    public static int getRegisterData(int index){
        return registers.get(index).getValue();
    }

    public static void setRegisterData(int index, int dataValue){
        registers.get(index).setValue(dataValue);
    }

    public static ObservableList<Register> getRegisters(){
        return FXCollections.observableArrayList(registers);
    }

    public void resetData(){
        for(Register register : registers)
            register.setValue(0);
    }
}
