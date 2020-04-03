package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ALU {

    public static Register[] registers;

    static {
        registers = new Register[32];
        for (int i = 0; i < 32; i++)
            registers[i] = new Register(i);
    }


    public static ObservableList<Register> getRegisters(){
        return FXCollections.observableArrayList(registers);
    }

    public static void a(){
        for (int i = 0; i < 32; i++)
            registers[i].setValue(registers[i].getValue() + 1);
    }


}
