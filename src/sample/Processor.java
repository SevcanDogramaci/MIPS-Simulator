package sample;

import java.util.List;

public class Processor {

    private ProgramCounter pc;
    private RegisterFile registerFile;
    private InstructionMemoryFile instructionMemoryFile;
    private MemoryFile memory;
    private ALU alu;

    public Processor() {
        pc = new ProgramCounter();
        registerFile = new RegisterFile();
        instructionMemoryFile = new InstructionMemoryFile();
        memory = new MemoryFile(1000);
        alu = new ALU();
    }

    public void loadInstructionsToMemory(List<Instruction> instructions) {
        this.instructionMemoryFile.load(instructions);
        reset();
    }

    private void reset() {

        pc.reset();
        registerFile.resetData();
        memory.resetData();
    }

    public void step() {

        if(isDone()) {
            return;
        }

        Instruction instruction;
        int alu_out = 0, data_out = 0, regData1 = 0, regData2 = 0,
            new_pc = pc.get(), branch_pc = new_pc, write_data;
        boolean alu_zero = false;

        // fetch instruction
        instruction = instructionMemoryFile.fetch(pc);


        // send instruction to control unit
        ControlUnit controlUnit = new ControlUnit(instruction);


        // extract registers' data that will be used
        Register sourceReg = instruction.getSourceReg(),
                targetReg = instruction.getTargetReg(),
                destinationReg = instruction.getDestinationReg();

        Register writeReg = (Register) mux(targetReg, destinationReg , controlUnit.isRegDst());
        registerFile.setRegisters(sourceReg, targetReg, writeReg);
        regData1 = registerFile.readData1();
        regData2 = registerFile.readData2();

        System.out.println(regData1 + " " + regData2);
        System.out.println(sourceReg);
        System.out.println(targetReg);
        System.out.println(destinationReg);

        // ALU performs operation
        alu.setOperation(
                ALUControl.getControl(controlUnit.isALUOp1(), controlUnit.isALUOp0(), instruction.getFunction()),
                (int)mux(regData2, instruction.getImmediate(), controlUnit.isALUsrc()),
                regData1);
        alu_out = alu.getOut();
        alu_zero = alu.isZero();


        // memory operations
        data_out = memory.cycle(controlUnit.isMemRead(), controlUnit.isMemWrite(), alu_out, regData2);


        // writeback
        write_data = (int)mux(alu_out, data_out, controlUnit.isMemtoReg());
        registerFile.write(controlUnit.isRegWrite(), write_data);


        // update pc 
        updatePc(instruction, new_pc, branch_pc, alu_zero, controlUnit);
    }

    private void updatePc(Instruction instruction, int new_pc, int branch_pc, boolean alu_zero, ControlUnit controlUnit) {
        new_pc += 4;

        branch_pc = new_pc + (instruction.getImmediate() << 2);


        // update pc if branching or jumping exists
        new_pc = (int)mux(new_pc, branch_pc, (controlUnit.isBranch() || controlUnit.isJump()) && alu_zero);
        pc.set(new_pc);
    }

    private Object mux (Object value1, Object value2, boolean getSecond) {
        if(getSecond) {
            return value2;
        }
        return value1;
    }

    public boolean isDone() {
        return pc.get() >= instructionMemoryFile.length();
    }

    public int getIndex() {
        return pc.get()/4;
    }
}
