package sample;

public class Register {

    private String name;
    private int no;
    private int value;

    public Register(int no, String name) {
        this.no = no;
        this.name = name;

        if (name.equals("sp")){
            this.value = MemoryFile.STACK_START;
        }
        else
            this.value = 0;
    }

    public void setValue(int value) {
        if (!name.equalsIgnoreCase("zero"))
            this.value = value;
    }

    public int getNo() { return no; }

    public String getName() { return name; }

    public int getValue() { return value; }

    public static String extractRegisterName(String name) {
        if (name.contains("$"))
            name = name.trim().replace("$", "");
        return name;
    }
}