package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static Map<String, Short> instructionNames;

    static {
        instructionNames = new HashMap<>();
        // put instructions;
    }

    private short opcode;

    @Override
    boolean checkFormat(String functionName) {

        return false;
    }

    @Override
    void parseInstruction(String line) {

    }

}
