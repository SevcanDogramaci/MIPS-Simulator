package sample;

import java.util.HashMap;
import java.util.Map;

public class IFormatInstruction extends Instruction {
    private short opcode;

    private static Map<String, Short> instructionNames;
    
    public static boolean checkFormat(String functionName) {
        return false;
    }

    @Override
    void parseInstruction(String line) {

    }

    static {
        instructionNames = new HashMap<>();
        // put instructions;
        instructionNames.put("addi", (short)8);
        instructionNames.put("addiu", (short)9);
        instructionNames.put("andi", (short)10);
        instructionNames.put("beq", (short)4);
        instructionNames.put("bgez", (short)1); //
        instructionNames.put("bgtz", (short)7);
        instructionNames.put("blez", (short)6);
        instructionNames.put("bltz", (short)1); //
        instructionNames.put("bne", (short)5);
        instructionNames.put("lb", (short)32);
        instructionNames.put("lbu", (short)33);
        instructionNames.put("lhu", (short)37);
        instructionNames.put("lui", (short)15);
        instructionNames.put("lw", (short)35);
        instructionNames.put("lwcl", (short)49);
        instructionNames.put("ori", (short)11);
        instructionNames.put("sb", (short)40);
        instructionNames.put("slti", (short)10);
        instructionNames.put("sh", (short)41);
        instructionNames.put("sw", (short)43);
        instructionNames.put("swcl", (short)57);
        instructionNames.put("xori", (short)14);
    }

}
