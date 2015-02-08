/**
 * 
 */
package nesjava.hardware.ppu;

/**
 * Sprite Ram
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
public class SprRam {
    
    byte[] ram = new byte[256];
    
    public byte read(short addr) {
        return ram[addr];
    }
    
    public void write(short addr, byte value) {
        ram[addr] = value;
    }
    
    private void checkBound(byte sprIndex) {
        if (sprIndex < 0 || sprIndex > 63) {
            throw new IllegalArgumentException("SpriteIndex out of bounds.");
        }
    }
    
    public byte getYCoord(byte sprIndex) {
        checkBound(sprIndex);
        return ram[sprIndex * 4];
    }
    
    public byte getTileIndex(byte sprIndex) {
        checkBound(sprIndex);
        return ram[sprIndex * 4 + 1];
    }
    
    public byte getAttr(byte sprIndex) {
        checkBound(sprIndex);
        return ram[sprIndex * 4 + 2];
    }
    
    public byte getXCoord(byte sprIndex) {
        checkBound(sprIndex);
        return ram[sprIndex * 4 + 3];
    }
}
