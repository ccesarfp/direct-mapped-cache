
import java.io.PrintStream;

public class Main {
    public static void main(String[] args) {
        try {
            IO io = new IO();
            RAM ram = new RAM(11);  // 2K RAM (2**11)
            Cache cache = new Cache(128, 16, ram);  // Total cache = 128, cache line = 16 words
            CPU cpu = new CPU(cache, io);

            int start = 0;
            ram.write(start, 110);
            ram.write(start + 1, 130);
            cpu.run(start + 1);
        } catch (InvalidAddressException e) {
            PrintStream err = System.err;
            err.println("Invalid address: " + e.getAddress());
        }
    }
}
