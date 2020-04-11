package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static final Map<String, Short> instructionMap = new HashMap<>();
    private Parser parser;

    public JFormatInstruction(String line, int i, Parser parser) throws Exception {
        this.parser = parser;
        index = i;
        this.line = line;

        parseInstruction(line);
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
            System.out.println(offset);
            this.immediate = calculateLabel(offset);
            this.targetReg = RegisterFile.getRegister("ra");
        }
        else
            throw new Exception();
    }

    private int calculateLabel(String s) {
        return parser.getLabelAddress(s.trim()) - index - 1;
    }

    // instructions
    static {
        instructionMap.put("j", (short)2);  // +
        instructionMap.put("jal", (short)3);// +
    }
}
