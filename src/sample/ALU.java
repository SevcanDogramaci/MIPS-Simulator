package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class ALU {

    public static ArrayList<Register> registers;

    static {
        registers = new ArrayList<>();
        for (int i = 0; i < 32; i++)
            registers.add(new Register(i));
    }

    public static ObservableList<Register> getRegisters(){
        return FXCollections.observableArrayList(registers);
    }

}
