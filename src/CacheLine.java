
public class CacheLine {
    private int k;
    private Integer tag;
    private int[] data;
    private boolean modified;

    public CacheLine(int k) {
        this.k = k;
        this.tag = null;
        this.data = new int[k];
        this.modified = false;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public int[] getData() {
        return data;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
