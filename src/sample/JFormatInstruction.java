package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static Map<String, Short> instructionNames;

    private short opcode;

    public static boolean checkFormat(String functionName) {
        return false;
    }

    @Override
    void parseInstruction(String line) {

    }

    // instructions
    static {
        instructionNames = new HashMap<>();
        // put instructions;
        instructionNames.put("j", (short)2);
        instructionNames.put("jal", (short)3);
    }
}
