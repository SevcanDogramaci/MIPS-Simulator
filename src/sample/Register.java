package sample;

public class Register {

    private String name;
    private int value;

    public Register(String name) {
        this.name = name;
        this.value = 0;
    }

    public Register(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
