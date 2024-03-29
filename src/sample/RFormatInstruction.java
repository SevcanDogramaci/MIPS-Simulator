package sample;

import java.util.HashMap;
import java.util.Map;

public class RFormatInstruction extends Instruction {

    private short functionCode;
    private static final Map<String, String> instructionMap;

    public RFormatInstruction(String line, int i) throws Exception {
        index = i;
        this.opcode = 0;
        this.line = line;

        parseInstruction(line);
        machineCode = getMachineCode();
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

        String[] temp = instruction[0].split(" ");

        if (temp.length <= 1) // syscall or exit.
            return;

        instruction[0] = temp[1].trim();


        int lastIdx = -1;

        for(int i = 0; i < instruction.length; i++){
            try {
                lastIdx = getNextRegister(registerUsage, lastIdx);

                switch (lastIdx) {
                    case 0:
                        destinationReg = RegisterFile.getRegister(extractRegisterName(instruction[i]));
                        break;
                    case 1:
                        System.out.println(instruction[i]);
                        sourceReg = RegisterFile.getRegister(extractRegisterName(instruction[i]));
                        break;
                    case 2:
                        targetReg = RegisterFile.getRegister(extractRegisterName(instruction[i]));
                        break;
                    case 3:
                        shiftAmount = Short.parseShort(instruction[i]);
                        break;
                }
            }catch (Exception e){
                System.out.println(line + " " + instruction[i] + " " +  lastIdx);

                for (char a: registerUsage) {
                    System.out.print(a);
                }
                e.printStackTrace();
            }
        }
    }
    @Override
    public short getFunction() {
        return functionCode;
    }

    @Override
    public String getMachineCode() {

        System.out.println("denem: " + line);
        StringBuilder sb = new StringBuilder();

        sb.append(fillWithZero(Integer.toBinaryString(opcode), 6))
                .append(" ");

        String code = instructionMap.get(line.split(",")[0].split(" ")[0]);
        char[] registerUsage = code.substring(7).toCharArray();

        if (registerUsage[1] == '1')
            sb.append(fillWithZero(Integer.toBinaryString(sourceReg.getNo()), 5));
        else
            sb.append(fillWithZero("", 5));

        sb.append(" ");
        if (registerUsage[2] == '1')
            sb.append(fillWithZero(Integer.toBinaryString(targetReg.getNo()), 5));
        else
            sb.append(fillWithZero("", 5));

        sb.append(" ");
        if (registerUsage[0] == '1')
            sb.append(fillWithZero(Integer.toBinaryString(destinationReg.getNo()), 5));
        else
            sb.append(fillWithZero("", 5));

        sb.append(" ");
        if (registerUsage[3] == '1')
            sb.append(fillWithZero(Integer.toBinaryString(shiftAmount), 5));
        else
            sb.append(fillWithZero("", 5));

        sb.append(" ").append(fillWithZero(Integer.toBinaryString(functionCode), 6));

        return sb.toString();
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

    static {
        instructionMap = new HashMap<>();
        // put instructions;

        instructionMap.put("add", "100000 1110");   // +
        instructionMap.put("addu", "100001 1110");  // +
        instructionMap.put("and", "100100 1110");   // +
        instructionMap.put("break", "00001101 0000");
        instructionMap.put("div", "011010 0110");
        instructionMap.put("divu", "011011 0110");

        instructionMap.put("jalr", "001001 1100");  // +
        instructionMap.put("jr", "001000 0100");    // +

        instructionMap.put("mfhi", "010000 1000");
        instructionMap.put("mflo", "010010 1000");
        instructionMap.put("mthi", "010001 0100");
        instructionMap.put("mtlo", "010011 0100");
        instructionMap.put("mult", "011000 0110");
        instructionMap.put("multu", "011001 0110");
        instructionMap.put("nor", "100111 1110");
        instructionMap.put("or", "100101 1110");    // +

        instructionMap.put("sll", "000000 1011");
        instructionMap.put("sllv", "000100 1110");
        instructionMap.put("slt", "101010 1110");   // +
        instructionMap.put("sltu", "101011 1110");  // büyük ihtimal olacak ama case çakışmasından dolayı eklenmedi ALUControl'e
        instructionMap.put("sra", "000011 1011");
        instructionMap.put("srav", "000111 1110");
        instructionMap.put("srl", "000010 1011");
        instructionMap.put("srlv", "000110 1110");

        instructionMap.put("sub", "100010 1110");   // +
        instructionMap.put("subu", "100011 1110");  // +
        instructionMap.put("syscall", "001100 0000");
        instructionMap.put("xor", "100110 1110");
    }
}
