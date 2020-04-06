package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static Map<String, Short> instructionMap;

    private long targetOffset;
    private Parser parser;

    public JFormatInstruction(String line, int i, Parser parser) {
        this.parser = parser;
        index = i;
        try{
            parseInstruction(line);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long getTargetOffset() {
        return targetOffset;
    }

    public static boolean checkFormat(String functionName) {
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) throws Exception {
        String[] instruction = line.split(" ");

        String functionName, offset;
        functionName = instruction[0];
        offset = instruction[1];

        if(checkFormat(functionName)){
            this.opcode = instructionMap.get(functionName);
            this.targetOffset = calculateLabel(offset);
        }
        else
            throw new Exception();
    }

    private long calculateLabel(String s) {
        return parser.getLabelAddress(s.trim()) - index;
    }

    // instructions
    static {
        instructionMap = new HashMap<>();
        // put instructions;
        instructionMap.put("j", (short)2);
        instructionMap.put("jal", (short)3);
    }
}
