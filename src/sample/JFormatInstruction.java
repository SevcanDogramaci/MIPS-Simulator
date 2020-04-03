package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static Map<String, Short> instructionMap;

    private short opcode;

    public JFormatInstruction(String line) {
        super();
    }

    public static boolean checkFormat(String functionName) {
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) {

    }

    // instructions
    static {
        instructionMap = new HashMap<>();
        // put instructions;
        instructionMap.put("j", (short)2);
        instructionMap.put("jal", (short)3);
    }
}
