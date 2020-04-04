package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static Map<String, Short> instructionNames;

    private long targetOffset;

    public JFormatInstruction(String line) {

        try{
            parseInstruction(line);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static boolean checkFormat(String functionName) {
        return instructionNames.containsKey(functionName) ? true : false;
    }

    @Override
    void parseInstruction(String line) throws Exception {
        String[] instruction = line.split(" ");

        String functionName, offset;
        functionName = instruction[0];
        offset = instruction[1];

        if(checkFormat(functionName)){
            this.opcode = instructionNames.get(functionName);
            this.targetOffset = Long.parseLong(offset);
        }
        else
            throw new Exception();
    }

    // instructions
    static {
        instructionNames = new HashMap<>();
        // put instructions;
        instructionNames.put("j", (short)2);
        instructionNames.put("jal", (short)3);
    }
}
