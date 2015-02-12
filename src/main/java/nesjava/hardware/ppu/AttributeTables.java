/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.hardware.MemoryAccessable;

/**
 * @author chenyan
 *
 */
public class AttributeTables implements MemoryAccessable {
    
    /*
     * vram start points
     */
    public static final short[] START_POINTS = {0x23c0, 0x27c0, 0x2bc0, 0x2fc0};
    
    /**
     * 64 bytes per table.
     */
    public static final int TABLE_SIZE = 0x40;

    /**
     * 4 Attribute Tables(each 64 bytes)
     *  
     */
    byte[][] tables = new byte[4][TABLE_SIZE];
    
    /**
     * Calculate the AttributeTable of addr.
     * 
     * @param addr
     * @return
     */
    private int getIndex(short addr) {
        if ((addr >= START_POINTS[0]) && (addr < (START_POINTS[0] + TABLE_SIZE))) {
            return 0;
        }
        if ((addr >= START_POINTS[1]) && (addr < (START_POINTS[1] + TABLE_SIZE))) {
            return 1;
        }
        if ((addr >= START_POINTS[2]) && (addr < (START_POINTS[2] + TABLE_SIZE))) {
            return 2;
        }
        if ((addr >= START_POINTS[3]) && (addr < (START_POINTS[3] + TABLE_SIZE))) {
            return 3;
        }
        
        throw new IllegalArgumentException("addr not in attribute table range.");
    }

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#read(short)
     */
    @Override
    public byte read(short addr) {
        int index = getIndex(addr);
        int offset = index - START_POINTS[0];
        return tables[index][offset];
    }

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#write(short, byte)
     */
    @Override
    public void write(short addr, byte value) {
        int index = getIndex(addr);
        int offset = index - START_POINTS[0];
        tables[index][offset] = value;        
    }
    
}
