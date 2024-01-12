import java.io.PrintStream;

public class Cache {
    private int totalCapacity;
    private int cacheLineSize;
    private RAM ram;
    private CacheLine[] lines;

    public Cache(int totalCapacity, int cacheLineSize, RAM ram) {
        this.totalCapacity = totalCapacity;
        this.cacheLineSize = cacheLineSize;
        this.ram = ram;
        this.lines = new CacheLine[totalCapacity / cacheLineSize];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = new CacheLine(cacheLineSize);
        }
    }

    public int read(int x) {
        int[] decodedAddress = decodeAddress(x);
        int r = decodedAddress[0];
        int t = decodedAddress[1];
        int w = decodedAddress[2];
        int s = decodedAddress[3];

        CacheLine line = lines[r];

        if (line.getTag() != null && line.getTag() == t) {
            System.out.println("Cache hit!");
            return line.getData()[w];
        } else {
            System.out.println("Cache miss!");
            if (line.isModified()) {
                writeBack(line);
            }
            line.setTag(t);
            readFromRAM(line, s);
            return line.getData()[w];
        }
    }

    public void write(int x, int data) {
        int[] decodedAddress = decodeAddress(x);
        int r = decodedAddress[0];
        int t = decodedAddress[1];
        int w = decodedAddress[2];
        int s = decodedAddress[3];

        CacheLine line = lines[r];

        if (line.getTag() == t) {
            System.out.println("Cache hit!");
            line.getData()[w] = data;
            line.setModified(true);
        } else {
            System.out.println("Cache miss!");
            if (line.isModified()) {
                writeBack(line);
            }
            line.setTag(t);
            readFromRAM(line, s);
            line.getData()[w] = data;
            line.setModified(true);
        }
    }

    private int[] decodeAddress(int x) {
        int w = x % cacheLineSize;
        int s = x / cacheLineSize;
        int r = s % (totalCapacity / cacheLineSize);
        int t = s / (totalCapacity / cacheLineSize);
        return new int[]{r, t, w, s};
    }

    private void readFromRAM(CacheLine line, int s) {
        int blockStart = s * cacheLineSize;
        for (int i = 0; i < cacheLineSize; i++) {
            try {
                line.getData()[i] = ram.read(blockStart + i);
            } catch (InvalidAddressException e) {
                PrintStream err = System.err;
                err.println(e.getAddress());
            }
        }
        line.setModified(false);
    }

    private void writeBack(CacheLine line) {
        int blockStart = line.getTag() * (totalCapacity / cacheLineSize);
        for (int i = 0; i < cacheLineSize; i++) {
            try {
                ram.write(blockStart + i, line.getData()[i]);
            } catch (InvalidAddressException e) {
                PrintStream err = System.err;
                err.println(e.getAddress());
            }
        }
    }
}