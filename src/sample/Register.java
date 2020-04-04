package sample;

public class Register {

    private String name;
    private int no;
    private int value;

    public Register(int no) {
        this.no = no;
        this.value = 0;
    }

    public Register(int no, String name, int value) {
        this.no = no;
        this.name = name;
        this.value = value;
    }

    public static Register getRegister(String extractRegisterName) {
        return null;
    }

    public void setNo(int no) { this.no = no; }

    public void setName(String name) { this.name = name; }

    public void setValue(int value) { this.value = value; }

    public int getNo() { return no; }

    public String getName() { return name; }

    public int getValue() { return value; }
}
