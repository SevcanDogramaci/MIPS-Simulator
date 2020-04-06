package sample;

public class Register {

    private String name;
    private int no;
    private int value;

    public Register(int no, String name) {
        this.no = no;
        this.name = name;
        this.value = 1;
    }

    public void setNo(int no) { this.no = no; }

    public void setName(String name) { this.name = name; }

    public void setValue(int value) { this.value = value; }

    public int getNo() { return no; }

    public String getName() { return name; }

    public int getValue() { return value; }

    public static String extractRegisterName(String name){
        if(name.contains("$"))
            name = name.replace("$", "");
        return name;
    }
}
