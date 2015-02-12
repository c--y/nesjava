/**
 * 
 */
package nesjava.hardware;

/**
 * 访存接口
 * 
 * @author chenyan
 *
 */
public interface MemoryAccessable {

    /**
     * Read a byte from ~.
     * 
     * @param addr
     * @return
     */
    public byte read(short addr);
    
    /**
     * Write a byte to ~.
     * 
     * @param addr
     * @param value
     */
    public void write(short addr, byte value);
}
