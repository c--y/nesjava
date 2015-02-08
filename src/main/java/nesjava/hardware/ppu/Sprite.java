/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.util.ByteUtils;

/**
 * @author chenyan
 *
 */
public class Sprite {

    byte index;

    SprRam sprRam;

    VRam vRam;
    
    public Sprite(byte index, SprRam sprRam, VRam VRam) {
        this.index = index;
        this.sprRam = sprRam;
        this.vRam = vRam;
    }
    
    /**
     * BG Priority, spr-ram byte 2 D5
     * 0 = false = In front
     * 1 = true = Behind
     */
    public boolean getBgPri() {
        byte attrByte = sprRam.getAttr(index);
        return ByteUtils.isBitSet(attrByte, 5);
    }
    
    public boolean getHFlip() {
        byte attrByte = sprRam.getAttr(index);
        return ByteUtils.isBitSet(attrByte, 6);
    } 
    
    public boolean getVFlip() {
        byte attrByte = sprRam.getAttr(index);
        return ByteUtils.isBitSet(attrByte, 7);
    }
    
    /**
     * Get upper 2 bits of color.
     * 
     * @return
     */
    public byte getU2bColor() {
        byte attrByte = sprRam.getAttr(index);
        return (byte) (attrByte & 0x3);
    }
    
    public int getYCoord() {
        byte yCoordByte = sprRam.getYCoord(index);
        return yCoordByte & 0xff;
    }
    
    public int getXCoord() {
        byte xCoordByte = sprRam.getXCoord(index);
        return xCoordByte & 0xff;
    }
}
