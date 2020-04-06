package sample;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RFormatInstruction extends Instruction {

    private short shiftAmount, functionCode;
    private int index;
    private Register registers[];

    private static Map<String, String> instructionMap;

    public RFormatInstruction(String line, int i) throws Exception {
        index = i;
        this.opcode = 0;
        setRegisters();

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

        String code = instructionMap.get(functionName);

        this.functionCode = Short.parseShort(code.substring(0, 6), 2);
        char[] registerUsage = code.substring(7).toCharArray();

        if (instruction.length == 1) // syscall or exit.
            return;

        instruction[0] = instruction[0].split(" ")[1].trim();


        int lastIdx = -1;

        for(int i = 0; i < instruction.length; i++){
            try {
                lastIdx = getNextRegister(registerUsage, lastIdx);
                if (lastIdx == 3)
                    shiftAmount = Short.parseShort(instruction[i]);
                registers[lastIdx] = RegisterFile.getRegister(extractRegisterName(instruction[i]));
            }catch (Exception e){
                System.out.println(line + " " + instruction[i] + " " +  lastIdx);

                for (char a: registerUsage) {
                    System.out.print(a);
                }
                e.printStackTrace();
            }
        }
    }

    private int getNextRegister(char usage[], int idx){
        for (idx = idx + 1; idx < usage.length; idx++){
            if (usage[idx] == '1')
                return idx;
        }
        return -1;
    }

    private String extractRegisterName(String name){
        if(name.contains("$"))
            name = name.trim().replace("$", "");
        return name;
    }


    private void setRegisters() {
        registers = new Register[3];
        registers[0] = destinationReg;
        registers[1] = sourceReg1;
        registers[2] = sourceReg2;
    }

    static {
        instructionMap = new HashMap<>();
        // put instructions;

        instructionMap.put("add", "100000 1110");
        instructionMap.put("addu", "100001 1110");
        instructionMap.put("and", "100100 1110");
        instructionMap.put("break", "00001101 0000");
        instructionMap.put("div", "011010 0110");
        instructionMap.put("divu", "011011 0110");
        instructionMap.put("jalr", "001001 1100");
        instructionMap.put("jr", "001001 1100");

        instructionMap.put("mfhi", "010000 1000");
        instructionMap.put("mflo", "010010 1000");
        instructionMap.put("mthi", "010001 0100");
        instructionMap.put("mtlo", "010011 0100");
        instructionMap.put("mult", "011000 0110");
        instructionMap.put("multu", "011001 0110");
        instructionMap.put("nor", "100111 1110");
        instructionMap.put("or", "100101 1110");

        instructionMap.put("sll", "000000 1011");
        instructionMap.put("sllv", "000100 1110");
        instructionMap.put("slt", "101010 1110");
        instructionMap.put("sltu", "101011 1110");
        instructionMap.put("sra", "000011 1011");
        instructionMap.put("srav", "000111 1110");
        instructionMap.put("srl", "000010 1011");
        instructionMap.put("srlv", "000110 1110");

        instructionMap.put("sub", "100010 1110");
        instructionMap.put("subu", "100011 1110");
        instructionMap.put("syscall", "001100 0000");
        instructionMap.put("xor", "100110 1110");
    }
}
