/**
 * 
 */
package nesjava.hardware;

/** 
 * Internal Memory
 * 
 * Memory layout:
 * <p>
 *    0000-00FF  - RAM for Zero-Page & Indirect-Memory Addressing
 *    0100-01FF  - RAM for Stack Space & Absolute Addressing
 *    0200-3FFF  - RAM for programmer use
 *    4000-7FFF  - Memory mapped I/O
 *    8000-FFF9  - ROM for programmer usage
 *    FFFA       - Vector address for NMI (low byte)
 *    FFFB       - Vector address for NMI (high byte)
 *    FFFC       - Vector address for RESET (low byte)
 *    FFFD       - Vector address for RESET (high byte)
 *    FFFE       - Vector address for IRQ & BRK (low byte)
 *    FFFF       - Vector address for IRQ & BRK  (high byte)
 * </p>
 * 
 * 0000-3FFF为RAM, 长度0x4000个字节.
 *
 * @author chenyan
 * 
 */
public class Memory {

    /**
     * Internal memory size
     */
    public static final int MEMORY_SIZE = 0x4000;
    
    public static final int xxx = 0;
    
    byte[] units = new byte[MEMORY_SIZE];
    
    public void write(short address, byte value) {
        units[address] = value;
    }
    
    public byte read(short address) {
        return units[address];
    }
    
    /**
     * Check whether address a1 and address a2 in a same page.
     * 
     * @param a1
     * @param a2
     * @return
     */
    public static boolean isSamePage(short a1, short a2) {
        return ((0xff00 & a1) == (0xff00 & a2))? true: false;
    }
    
}
