/**
 * 
 */
package nesjava.hardware.ppu;

import lombok.Data;
import nesjava.util.ByteUtils;

/**
 * 
 * @author chenyan
 *
 */
@Data
public class RegStatus {

    byte value;

    // Options
    /**
     * D5, This flag is set during sprite evaluation and cleared at 
     *     dot 1 (the second dot) of the pre-render line.
     */
    boolean spriteOverflow;
    
    /**
     * Set when a nonzero pixel of sprite0 overlaps a nonzero background pixel.
     * Cleared at dot 1 of the pre-render line.
     * Used for raster timeing.
     */
    boolean sprite0Hit;
    
    /**
     * 0 = not in vblank
     * 1 = in vblank
     */
    boolean inVBlank;
    
    /**
     * Write the status register.
     * 
     * @param value
     */
    public void write(byte value) {
        this.value = value;
        
        spriteOverflow = ByteUtils.isBitSet(value, 5);
        sprite0Hit = ByteUtils.isBitSet(value, 6);
        inVBlank = ByteUtils.isBitSet(value, 7);
    }
    
    /**
     * Read the status register.
     * 
     * @return
     */
    public byte read() {
        return value;
    }
}
