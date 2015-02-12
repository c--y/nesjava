/**
 * 
 */
package nesjava.hardware.ppu;

import java.util.Arrays;

import nesjava.hardware.MemoryAccessable;

/**
 * Pattern Table
 * 
 *   The Pattern Table contains the actual 8x8 tiles which the Name Table
 *   refers to. It also holds the lower two (2) bits of the 4-bit colour
 *   matrix needed to access all 16 colours of the NES palette. 
 * 
 * @author chenyan
 *
 */
public class PatternTables implements MemoryAccessable {
    
    /**
     * one tile = 16 bytes
     */
    public static final int TILE_SIZE = 16;
    
    /**
     * Pattern Tables
     */
    byte[][] tables = new byte[2][0x1000];

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#read(short)
     */
    @Override
    public byte read(short addr) {
        return 0;
    }

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#write(short, byte)
     */
    @Override
    public void write(short addr, byte value) {
        
    }
    
    /**
     * Read a tile from Pattern Table located by video ram address.
     * 
     * @param addr
     * @return
     */
    public byte[] readTile(short addr) {
        if (addr < 0 || addr >= 2000) {
            throw new IllegalArgumentException("PatternTables:readTile");
        }
        
        int ptIndex = addr / 1000;
        int byteIndex = addr % 1000;
        
        byte[] table = tables[ptIndex];
        return Arrays.copyOfRange(table, byteIndex, TILE_SIZE);
    }

}
