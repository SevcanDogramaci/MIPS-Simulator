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

        //System.out.println("RegData1 : " + regData1 + " RegData2 : " + regData2);
        //System.out.println("SourceReg:" + sourceReg);
        //System.out.println("TargetReg:" + targetReg);
        //System.out.println("DestinationReg:" + destinationReg);

        // ALU performs operation
        alu.setOperation(
                ALUControl.getControl(controlUnit.isALUOp1(), controlUnit.isALUOp0(), instruction.getFunction()),
                (int)mux(regData2, instruction.getImmediate(), controlUnit.isALUsrc()),
                regData1);
        alu_out = alu.getOut();
        alu_zero = alu.isZero();


        // memory operations
        int accessLength = instruction instanceof IFormatInstruction
                ? ((IFormatInstruction)instruction).getAccessLength() : 4;

        data_out = memory.cycle(controlUnit.isMemRead(), controlUnit.isMemWrite(), alu_out, regData2, accessLength);


        // writeback
        write_data = (int)mux(alu_out, data_out, controlUnit.isMemtoReg());
        write_data = (int)mux(write_data, new_pc + 4,
                (controlUnit.isJump() && !controlUnit.isMemtoReg())
                        || (controlUnit.isRegWrite() && controlUnit.isJumpReg()));
        registerFile.write(controlUnit.isRegWrite(), write_data);

        //System.out.println("OLD PC : " + pc.get());


        // update pc 
        updatePc(instruction, new_pc, branch_pc, regData1, alu_zero, controlUnit);

        //System.out.println("NEW PC : " + pc.get());
    }

    private void updatePc(Instruction instruction, int new_pc, int branch_pc,  int jr_pc, boolean alu_zero, ControlUnit controlUnit) {
        new_pc += 4;

        branch_pc = new_pc + (instruction.getImmediate() << 2);


        // update pc if branching or jumping exists
        new_pc = (int)mux(new_pc, branch_pc, (controlUnit.isBranch() && alu_zero) ||
                (controlUnit.isBranchNotEqual() && !alu_zero) ||
                controlUnit.isJump());

        new_pc = (int)mux(new_pc, jr_pc, controlUnit.isJumpReg());

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

    public String getStackData(){
        return memory.getMemoryData();
    }
}