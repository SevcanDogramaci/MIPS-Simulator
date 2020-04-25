package sample;

import java.util.HashMap;
import java.util.Map;

public class JFormatInstruction extends Instruction {

    private static final Map<String, Short> instructionMap;
    private Parser parser;

    public JFormatInstruction(String line, int i, Parser parser) {
        this.parser = parser;
        index = i;
        this.line = line;

        parseInstruction(line);
        machineCode = getMachineCode();
    }

    public static boolean checkFormat(String functionName) {
        // check if instruction map contains the function
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) {
        String[] instruction = line.split(" ");

        // extract function name and offset
        String functionName, offset;
        functionName = instruction[0];
        offset = instruction[1];

        // specify instruction fields
        this.opcode = instructionMap.get(functionName);
        this.immediate = calculateLabel(offset);
        this.targetReg = RegisterFile.getRegister("ra");
    }

    @Override
    public String getMachineCode() {
        StringBuilder sb = new StringBuilder();

        sb.append(fillWithZero(Integer.toBinaryString(opcode), 6))
                .append(" ")
                .append(fillWithZero(Integer.toBinaryString(immediate), 26));
        return sb.toString();
    }

    private int calculateLabel(String s) {
        // get label address from label address map
        return parser.getLabelAddress(s.trim()) - index - 1;
    }

    static {
        instructionMap = new HashMap<>();

        // put instructions
        instructionMap.put("j", (short)2);  // +
        instructionMap.put("jal", (short)3);// +
    }
}
