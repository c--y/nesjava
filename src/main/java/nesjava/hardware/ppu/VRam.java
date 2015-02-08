/**
 * 
 */
package nesjava.hardware.ppu;

import java.util.Arrays;

import nesjava.hardware.PPU;
import nesjava.util.ByteUtils;

/**
 * Video Ram
 * 
 * 16k bits = 2048(0x800) bytes
 * 
 * @author chenyan
 *
 */
public class VRam {
    
    /** 
     * PPU
     */
    PPU ppu;
    
    /**
     * one tile = 16 bytes
     */
    public static final int TILE_SIZE = 16;
    
    /**
     * Internal VRam
     */
    byte[] units = new byte[0x800];
    
    /**
     * 2 Pattern Tables, each one stores 0x100(256) tiles.
     * 
     * Memory Address:
     *  #0 0x0000, size=0x1000
     *  #1 0x1000, size=0x1000
     */
    byte[][] patternTables = new byte[2][0x1000];
    
    /**
     * 4 Attribute Tables
     */
    byte[][] attributeTables = new byte[4][0x40];
    
    /**
     * NameTables
     */
    NameTables nameTables = new NameTables();
    
    /**
     * Constructor
     * 
     * @param ppu
     */
    public VRam(PPU ppu) {
        this.ppu = ppu;
    }

    /**
     * Read VRam byte
     * 
     * @param addr
     * @return
     */
    public byte readVRam(short addr) {
        if (addr >= 0x0000 && addr < 0x1000) {
            // pattern table #0
            return patternTables[0][addr];
        } else if (addr >= 0x1000 && addr < 0x2000) {
            // pattern table #1
            return patternTables[1][addr & 0xfff];
        } 
        return 0;
    }
    
    /**
     * Write a byte to VRam
     * 
     * @param addr
     * @param value
     */
    public void writeVRam(short addr, byte value) {
        
    }
    
    public byte[] readTile(int ptIndex, int tileIndex) {
        if (ptIndex < 0 || ptIndex > 1) {
            throw new IllegalArgumentException();
        }
        
        byte[] pt = patternTables[ptIndex];
        int offset = tileIndex * TILE_SIZE;
        return Arrays.copyOfRange(pt, offset, TILE_SIZE);
    }
    
    /**
     * Dump a tile bytes.
     * 
     * For debug.
     * 
     * @param tile
     * @return
     */
    public static String dumpTile(byte[] tile) {
        // TODO params check
        
        StringBuilder sb = new StringBuilder();
        int halfSize = TILE_SIZE / 2;
        
        for (int i = 0; i < halfSize; i++) {
            // each bit
            for (int j = 7; j >= 0; j--) {
                int bit0 = ByteUtils.isBitSet(tile[i], j)? 1: 0;
                int bit1 = ByteUtils.isBitSet(tile[i + halfSize], j)? 1 << 1: 0;
                sb.append(Integer.toString(bit1 + bit0));
            }
            sb.append("\n");
        }
        
        return sb.toString();
    }
}
