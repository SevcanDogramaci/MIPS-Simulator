package sample;

import java.util.HashMap;
import java.util.Map;

public class IFormatInstruction extends Instruction {

    private Parser parser;
    private static final Map<String, Short> instructionMap;

    public IFormatInstruction(String line, int i, Parser parser) {
        this.parser = parser;
        index = i;
        this.line = line;

        parseInstruction(line);

        System.out.println(sourceReg+" "+destinationReg + " " +targetReg);
    }

    public int getImmediate() {
        return immediate;
    }

    public static boolean checkFormat(String functionName) {
        System.out.println(instructionMap.containsKey(functionName));
        return instructionMap.containsKey(functionName);
    }

    @Override
    void parseInstruction(String line) {
        String[] instruction = line.toLowerCase().split(",");
        String functionName = instruction[0].split(" ")[0];

        opcode = instructionMap.get(functionName);

        instruction[0] = instruction[0].split(" ")[1].trim();

        if(functionName.startsWith("b")){
            sourceReg = RegisterFile.getRegister(extractRegisterName(instruction[0]));

            if (instruction.length == 2){

                if (functionName.equalsIgnoreCase("bgez")){
                    targetReg = RegisterFile.getRegister("t1");
                    targetReg.setValue(1);
                } else
                    targetReg = RegisterFile.getRegister("zero");

                immediate = calculateLabel(instruction[1]);
            }else {
                targetReg = RegisterFile.getRegister(extractRegisterName(instruction[1]));

                try {
                    immediate = Integer.parseInt(instruction[2].trim());
                }catch (Exception e){
                    immediate = calculateLabel(instruction[2]);
                }
            }

        } else {
            targetReg = RegisterFile.getRegister(extractRegisterName(instruction[0]));

            if (instruction.length == 2) {
                String ins = instruction[1].trim();
                immediate = Integer.parseInt(ins.substring(0, ins.indexOf("(")));
                sourceReg = RegisterFile.getRegister(
                        extractRegisterName(ins.substring(ins.indexOf("(") + 1, ins.indexOf(")"))));

            } else {
                sourceReg = RegisterFile.getRegister(extractRegisterName(instruction[1]));
                immediate = Integer.parseInt(instruction[2].trim());
            }
        }

    }

    private int calculateLabel(String s) {
        return parser.getLabelAddress(s.trim()) - index;
    }

    private String extractRegisterName(String name){
        if(name.contains("$"))
            name = name.trim().replace("$", "");
        return name;
    }

    static {
        instructionMap = new HashMap<>();
        // put instructions;
        instructionMap.put("addi", (short)8);   // +
        instructionMap.put("addiu", (short)9);  // +
        instructionMap.put("andi", (short)12);  // +
        instructionMap.put("beq", (short)4);
        instructionMap.put("bgez", (short)1);   // //
        instructionMap.put("bgtz", (short)7);
        instructionMap.put("blez", (short)6);
        instructionMap.put("bltz", (short)1);   // //
        instructionMap.put("bne", (short)5);
        instructionMap.put("lb", (short)32);
        instructionMap.put("lbu", (short)33);
        instructionMap.put("lhu", (short)37);
        instructionMap.put("lui", (short)15);
        instructionMap.put("lw", (short)35);
        instructionMap.put("lwcl", (short)49);
        instructionMap.put("ori", (short)11);   // +
        instructionMap.put("sb", (short)40);
        instructionMap.put("slti", (short)10);  // +
        instructionMap.put("sh", (short)41);
        instructionMap.put("sw", (short)43);
        instructionMap.put("swcl", (short)57);
        instructionMap.put("xori", (short)14);
    }

}
