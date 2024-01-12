
public class CPU {
    private Cache cache;
    private IO io;

    public CPU(Cache cache, IO io) {
        this.cache = cache;
        this.io = io;
    }

    public void run(int x) {
        try {
            int data = cache.read(x);
            System.out.println("Data read: " + data);
        } catch (Exception e) {
            System.out.println("Invalid address: " + e);
        }
    }
}
