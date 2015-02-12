/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.util.ByteUtils;

/**
 * Sprite Object
 * 
 * @author chenyan
 *
 */
public class Sprite {
    
    byte yCoord;
    
    byte xCoord;
    
    byte attr;
    
    byte tileIndex;
    
    /**
     * BG Priority, spr-ram byte 2 D5
     * 0 = false = In front
     * 1 = true = Behind
     */
    public boolean getBgPri() {
        return ByteUtils.isBitSet(attr, 5);
    }
    
    public boolean getHFlip() {
        return ByteUtils.isBitSet(attr, 6);
    } 
    
    public boolean getVFlip() {
        return ByteUtils.isBitSet(attr, 7);
    }
    
    /**
     * Get upper 2 bits of color.
     * 
     * @return
     */
    public byte getU2bColor() {
        return (byte) (attr & 0x3);
    }
    
    public int getYCoord() {
        return yCoord & 0xff;
    }
    
    public int getXCoord() {
        return xCoord & 0xff;
    }
}
