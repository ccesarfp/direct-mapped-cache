
public class InvalidAddressException extends Exception {
    private int address;

    public InvalidAddressException(int address) {
        this.address = address;
    }

    public int getAddress() {
        return this.address;
    }
}