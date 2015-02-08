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
public class RegMask {
    
    byte value;
    
    // Options
    
    /**
     * 0 = Normal color.
     * 1 = Produce a grayscale display.
     */
    boolean grayscale;
    
    /**
     * 0 = Hide.
     * 1 = Show background in leftmost 8 pixels of screen.
     */
    boolean showBackgroundLeft8;
    
    /**
     * 0 = Hide.
     * 1 = Show sprites in leftmost 8 pixels of screen.
     */
    boolean showSpritesLeft8;
    
    /**
     * 1 = Show.
     */
    boolean showBackground;
    
    /**
     * 1 = Show.
     */
    boolean showSprites;
    
    boolean emphasizeRed;
    
    boolean emphasizeGreen;
    
    boolean emphasizeBlue;
    
    /**
     * Write the mask register.
     * 
     * @param value
     */
    public void write(byte value) {
        this.value = value;
        
        grayscale = ByteUtils.isBitSet(value, 0);
        showBackgroundLeft8 = ByteUtils.isBitSet(value, 1);
        showSpritesLeft8 = ByteUtils.isBitSet(value, 2);
        showBackground = ByteUtils.isBitSet(value, 3);
        showSprites = ByteUtils.isBitSet(value, 4);
        emphasizeRed = ByteUtils.isBitSet(value, 5);
        emphasizeGreen = ByteUtils.isBitSet(value, 6);
        emphasizeBlue = ByteUtils.isBitSet(value, 7);
        
    }
    
    /**
     * Read the mask register.
     * 
     * @return
     */
    public byte read() {
        return value;
    }

}
