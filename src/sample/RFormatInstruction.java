package sample;

import java.util.HashMap;
import java.util.Map;

public class RFormatInstruction extends Instruction {

    private short opcode;

    private static Map<String, Short> instructionNames;

    static {
        instructionNames = new HashMap<>();
        // put instructions;
    }

    @Override
    boolean checkFormat(String functionName) {
        return false;
    }

    @Override
    void parseInstruction(String line) {

    }
}
