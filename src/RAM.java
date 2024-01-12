
public class RAM {
    private int k;
    private int[] memory;

    public RAM(int k) {
        this.k = (int) Math.pow(2, k);
        this.memory = new int[this.k];
    }

    public int read(int x) throws InvalidAddressException {
        if (x < 0 || x >= memory.length) {
            throw new InvalidAddressException(x);
        }
        return memory[x];
    }

    public void write(int x, int data) throws InvalidAddressException {
        if (x < 0 || x >= memory.length) {
            throw new InvalidAddressException(x);
        }
        memory[x] = data;
    }
}
