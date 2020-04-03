package sample;

public class IFormatInstruction extends Instruction {


    @Override
    boolean checkFormat(String functionName) {

        return false;
    }

    @Override
    void parseInstruction(String line) {

    }
}
