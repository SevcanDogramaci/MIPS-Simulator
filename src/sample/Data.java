package sample;

public class Data {

    private String address, value;

    public Data(int address, String value) {
        this.address = String.format("%04X", address & 0xFFFF);
        this.value = value;
    }

    public  String getAddress() {
        return address;
    }

    public String getValue() {
        return value;
    }
}
