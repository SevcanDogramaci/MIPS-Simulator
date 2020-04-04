package sample;

import java.util.HashMap;
import java.util.Map;

public class RFormatInstruction extends Instruction {

    private Register sourceReg1, sourceReg2, destinationReg;
    private short shiftAmount, functionCode;

    private static Map<String, Short> instructionMap;

    public RFormatInstruction(String line) {

        this.opcode = 0;

        try {
            parseInstruction(line);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkFormat(String functionName) {
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) throws Exception {
        String[] instruction = line.split(",");
        String functionName = instruction[0].split(" ")[0];

        if(checkFormat(functionName)){
            this.functionCode = instructionMap.get(functionName);

            sourceReg1 = new Register(Register.extractRegisterName(instruction[0].split(" ")[1].trim()));
            sourceReg2 = new Register(Register.extractRegisterName(instruction[1].trim()));

            if(instruction[2].trim().contains("$")){
                destinationReg =  new Register(Register.extractRegisterName(instruction[2].trim()));
                shiftAmount = 0;
            }
            else{
                destinationReg = null;
                shiftAmount = Short.parseShort(instruction[2].trim());
            }
        }
        else
            throw new Exception();
    }

    static {
        instructionMap = new HashMap<>();
        // put instructions;

        instructionMap.put("add", (short) 32);
        instructionMap.put("addu", (short) 33);
        instructionMap.put("and", (short) 36);
        instructionMap.put("break", (short) 13);
        instructionMap.put("div", (short) 26);
        instructionMap.put("divu", (short) 27);
        instructionMap.put("jalr", (short) 9);
        instructionMap.put("jr", (short) 8);

        instructionMap.put("mfhi", (short) 16);
        instructionMap.put("mflo", (short) 18);
        instructionMap.put("mthi", (short) 17);
        instructionMap.put("mtlo", (short) 19);
        instructionMap.put("mult", (short) 24);
        instructionMap.put("multu", (short) 25);
        instructionMap.put("nor", (short) 39);
        instructionMap.put("or", (short) 37);

        instructionMap.put("sll", (short) 0);
        instructionMap.put("sllv", (short) 4);
        instructionMap.put("slt", (short) 42);
        instructionMap.put("sltu", (short) 43);
        instructionMap.put("sra", (short) 3);
        instructionMap.put("srav", (short) 7);
        instructionMap.put("srl", (short) 2);
        instructionMap.put("srlv", (short) 6);

        instructionMap.put("sub", (short) 34);
        instructionMap.put("subu", (short) 35);
        instructionMap.put("syscall", (short) 12);
        instructionMap.put("xor", (short) 38);
    }
}
