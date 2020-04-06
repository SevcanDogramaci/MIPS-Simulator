package sample;

import java.util.HashMap;
import java.util.Map;



public class IFormatInstruction extends Instruction {

    private enum RegisterType {

    }

    private short opcode;
    private Parser parser;

    private static Map<String, Short> instructionMap;

    public IFormatInstruction(String line, int i, Parser parser) {
        super();
        this.parser = parser;
        index = i;
    }

    public int getImmediate() {
        return immediate;
    }

    public static boolean checkFormat(String functionName) {
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) {

    }

    static {
        instructionMap = new HashMap<>();
        // put instructions;
        instructionMap.put("addi", (short)8);
        instructionMap.put("addiu", (short)9);
        instructionMap.put("andi", (short)10);
        instructionMap.put("beq", (short)4);
        instructionMap.put("bgez", (short)1); //
        instructionMap.put("bgtz", (short)7);
        instructionMap.put("blez", (short)6);
        instructionMap.put("bltz", (short)1); //
        instructionMap.put("bne", (short)5);
        instructionMap.put("lb", (short)32);
        instructionMap.put("lbu", (short)33);
        instructionMap.put("lhu", (short)37);
        instructionMap.put("lui", (short)15);
        instructionMap.put("lw", (short)35);
        instructionMap.put("lwcl", (short)49);
        instructionMap.put("ori", (short)11);
        instructionMap.put("sb", (short)40);
        instructionMap.put("slti", (short)10);
        instructionMap.put("sh", (short)41);
        instructionMap.put("sw", (short)43);
        instructionMap.put("swcl", (short)57);
        instructionMap.put("xori", (short)14);
    }

}
