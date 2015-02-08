/**
 * 
 */
package nesjava.hardware.ppu;

import lombok.Data;
import nesjava.util.ByteUtils;

/**
 * @author chenyan
 *
 */
@Data
public class RegControl {

    /**
     * 0x2000
     */
    byte value;
    
    // Options
    
    /**
     * Base nametable address.
     *  0 = 0x2000
     *  1 = 0x2400
     *  2 = 0x2800
     *  3 = 0x2c00
     */
    byte baseNameTable;
    
    /**
     * 0 = PPU memory address inc = 1,
     * 1 = PPU memory address inc = 32, vertical
     */
    boolean verticalWrite;
    
    /**
     * 0 = 0x0000
     * 1 = 0x1000
     */
    boolean spritePatternTable;
    
    /**
     * 0 = 0x0000
     * 1 = 0x1000
     */
    boolean screenPatternTable;
    
    /**
     * 0 = 8*8
     * 1 = 8*16
     */
    boolean spriteSize;
    
    /**
     * PPU master/slave mode, not used in NES.
     */
    boolean masterSlave;
    
    /**
     * 1 = generate interrupts on VBlank.
     */
    boolean enabledVBlank;
    
    /**
     * Write the control register.
     * 
     * @param value
     */
    public void write(byte value) {
        this.value = value;
        
        int bit0 = ByteUtils.isBitSet(value, 0)? 1: 0;
        int bit1 = ByteUtils.isBitSet(value, 1)? 1 << 1: 0;
        baseNameTable = (byte) (bit1 + bit0);
        verticalWrite = ByteUtils.isBitSet(value, 2);
        spritePatternTable = ByteUtils.isBitSet(value, 3);
        screenPatternTable = ByteUtils.isBitSet(value, 4);
        spriteSize = ByteUtils.isBitSet(value, 5);
        masterSlave = ByteUtils.isBitSet(value, 6);
        enabledVBlank = ByteUtils.isBitSet(value, 7);
    }
    
    /**
     * Read the control register.
     * 
     * @return
     */
    public byte read() {
        return value;
    }
}
