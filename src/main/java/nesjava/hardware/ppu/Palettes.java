/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.hardware.MemoryAccessable;

/**
 * Palette
 * 
 * 16 colors.
 * 
 * @author chenyan
 *
 */
public class Palettes implements MemoryAccessable {
    
    

    /**
     * Color table
     */
    public static final long[] PALETTE_RGB = {
        0x666666, 0x002a88, 0x1412a7, 0x3b00a4, 0x5c007e,
        0x6e0040, 0x6c0600, 0x561d00, 0x333500, 0x0b4800,
        0x005200, 0x004f08, 0x00404d, 0x000000, 0x000000,
        0x000000, 0xadadad, 0x155fd9, 0x4240ff, 0x7527fe,
        0xa01acc, 0xb71e7b, 0xb53120, 0x994e00, 0x6b6d00,
        0x388700, 0x0c9300, 0x008f32, 0x007c8d, 0x000000,
        0x000000, 0x000000, 0xfffeff, 0x64b0ff, 0x9290ff,
        0xc676ff, 0xf36aff, 0xfe6ecc, 0xfe8170, 0xea9e22,
        0xbcbe00, 0x88d800, 0x5ce430, 0x45e082, 0x48cdde,
        0x4f4f4f, 0x000000, 0x000000, 0xfffeff, 0xc0dfff,
        0xd3d2ff, 0xe8c8ff, 0xfbc2ff, 0xfec4ea, 0xfeccc5,
        0xf7d8a5, 0xe4e594, 0xcfef96, 0xbdf4ab, 0xb3f3cc,
        0xb5ebf2, 0xb8b8b8, 0x000000, 0x000000
    };

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#read(short)
     */
    @Override
    public byte read(short addr) {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see nesjava.hardware.MemoryAccessable#write(short, byte)
     */
    @Override
    public void write(short addr, byte value) {
        // TODO Auto-generated method stub
        
    }
}
