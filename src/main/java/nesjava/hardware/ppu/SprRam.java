/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.hardware.MemoryAccessable;

/**
 * Sprite Ram (Object Attribute Memory)
 * 
 *   Sprite attributes such as flipping and priority, are stored in SPR-RAM,
 *   which is a separate 256 byte area of memory, independent of ROM and
 *   VRAM. The format of SPR-RAM is as follows:

 *     +-----------+-----------+-----+------------+
 *     | Sprite #0 | Sprite #1 | ... | Sprite #63 |
 *     +-+------+--+-----------+-----+------------+
 *       |      |   
 *       +------+----------+--------------------------------------+
 *       + Byte | Bits     | Description                          |
 *       +------+----------+--------------------------------------+
 *       |  0   | YYYYYYYY | Y Coordinate - 1. Consider the coor- |
 *       |      |          | dinate the upper-left corner of the  |
 *       |      |          | sprite itself.                       |
 *       |  1   | IIIIIIII | Tile Index #                         |
 *       |  2   | vhp000cc | Attributes                           |
 *       |      |          |   v = Vertical Flip   (1=Flip)       |
 *       |      |          |   h = Horizontal Flip (1=Flip)       |
 *       |      |          |   p = Background Priority            |
 *       |      |          |         0 = In front                 |
 *       |      |          |         1 = Behind                   |
 *       |      |          |   c = Upper two (2) bits of colour   |
 *       |  3   | XXXXXXXX | X Coordinate (upper-left corner)     |
 *       +------+----------+--------------------------------------+
 * 
 * @author chenyan
 *
 */
public class SprRam implements MemoryAccessable {
    
    /**
     * Number of Sprites in NES OAM.
     */
    public static final int SPRITE_NUM = 64;
    
    /**
     * 4 bytes per Sprite.
     */
    public static final int SPRITE_SIZE = 4;
        
    /**
     * 64 Sprites
     */
    Sprite[] sprites = new Sprite[SPRITE_NUM];
    
    /**
     * Constructor
     */
    public SprRam() {
        for (int i = 0; i < SPRITE_NUM; i++) {
            sprites[i] = new Sprite();
        }
    }
    
    /**
     * Check bounds.
     * 
     * @param addr
     */
    private void checkBound(short addr) {
        
    }
    
    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#read(short)
     */
    @Override
    public byte read(short addr) {
        // Must be less than 65
        int sprIndex = addr / 4;
        int byteIndex = addr % 4;
        
        Sprite sprite = sprites[sprIndex];
        
        switch (byteIndex) {
        case 0:
            return sprite.yCoord;
        case 1:
            return sprite.tileIndex;
        case 2:
            return sprite.attr;
        case 3:
            return sprite.xCoord;
        }
        
        throw new IllegalStateException("SprRam:read");
    }
    
    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#write(short, byte)
     */
    @Override
    public void write(short addr, byte value) {
        // Must be less than 65
        int sprIndex = addr / 4;
        int byteIndex = addr % 4;
        
        Sprite sprite = sprites[sprIndex];
        
        switch (byteIndex) {
        case 0:
            sprite.yCoord = value;
        case 1:
            sprite.tileIndex = value;
        case 2:
            sprite.attr = value;
        case 3:
            sprite.xCoord = value;
        }    
    }
    
    /**
     * Get the Sprite by index.
     * 
     * @param index
     * @return
     */
    public Sprite getSprite(int index) {
        if (index < 0 || index > 64) {
            throw new IllegalStateException();
        }
        
        return sprites[index];
    }
    
    /**
     * Make up a OAM view for debugging.
     * 
     * @return
     */
    public byte[] viewOam() {
        byte[] oam = new byte[SPRITE_NUM * SPRITE_SIZE];
        
        for (int i = 0; i < SPRITE_NUM; i++) {
            Sprite sprite = sprites[i];
            oam[i] = sprite.yCoord;
            oam[i + 1] = sprite.tileIndex;
            oam[i + 2] = sprite.attr;
            oam[i + 3] = sprite.xCoord;
        }
        
        return oam;
    }
}
