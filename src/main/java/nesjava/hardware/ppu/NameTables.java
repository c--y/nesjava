/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.hardware.MemoryAccessable;

/**
 * Name Table
 * 
 *   The NES displays graphics using a matrix of "tiles"; this grid is called
 *   a Name Table. Tiles themselves are 8x8 pixels. The entire Name Table
 *   itself is 32x30 tiles (256x240 pixels). Keep in mind that the displayed
 *   resolution differs between NTSC and PAL units.
 * 
 * RAM Memory Map
 * <code>
 *     +---------+-------+--------------------+
 *     | Address | Size  | Description        |
 *     +---------+-------+--------------------+
 *     | $0000   | $1000 | Pattern Table #0   |
 *     | $1000   | $1000 | Pattern Table #1   |
 *     | $2000   | $800  | Name Tables        |
 *     | $3F00   | $20   | Palettes           |
 *     +---------+-------+--------------------+
 * </code>
 *
 * Programmer Memory Map
 * <code>
 *     +---------+-------+-------+--------------------+
 *     | Address | Size  | Flags | Description        |
 *     +---------+-------+-------+--------------------+
 *     | $0000   | $1000 | C     | Pattern Table #0   |
 *     | $1000   | $1000 | C     | Pattern Table #1   |
 *     | $2000   | $3C0  |       | Name Table #0      |
 *     | $23C0   | $40   |  N    | Attribute Table #0 |
 *     | $2400   | $3C0  |  N    | Name Table #1      |
 *     | $27C0   | $40   |  N    | Attribute Table #1 |
 *     | $2800   | $3C0  |  N    | Name Table #2      |
 *     | $2BC0   | $40   |  N    | Attribute Table #2 |
 *     | $2C00   | $3C0  |  N    | Name Table #3      |
 *     | $2FC0   | $40   |  N    | Attribute Table #3 |
 *     | $3000   | $F00  |   R   |                    |
 *     | $3F00   | $10   |       | Image Palette #1   |
 *     | $3F10   | $10   |       | Sprite Palette #1  |
 *     | $3F20   | $E0   |    P  |                    |
 *     | $4000   | $C000 |     F |                    |
 *     +---------+-------+-------+--------------------+
 *                         C = Possibly CHR-ROM
 *                         N = Mirrored (see Subsection G)
 *                         P = Mirrored (see Subsection H)
 *                         R = Mirror of $2000-2EFF (VRAM)
 *                         F = Mirror of $0000-3FFF (VRAM)
 * </code>
 * 
 * @author chenyan
 *
 */
public class NameTables implements MemoryAccessable {
    
    /**
     * 镜像类型
     */
    NameTableMirrorType mirrorType;

    /**
     * 物理表0, 0x000
     */
    byte[] hardTable0 = new byte[0x400];
    
    /**
     * 物理表1, 0x400
     */
    byte[] hardTable1 = new byte[0x400];
    
    /**
     * 4个逻辑表
     */
    byte[][] logicalTables = new byte[4][];
    
    /**
     * 改变镜像模式
     * 
     * @param mirrorType
     */
    public void changeMirror(NameTableMirrorType mirrorType) {
        this.mirrorType = mirrorType;
        
        switch (mirrorType) {
        case Horizontal:
            logicalTables[0] = hardTable0;
            logicalTables[1] = hardTable0;
            logicalTables[2] = hardTable1;
            logicalTables[3] = hardTable1;
            break;
        case Vertical:
            logicalTables[0] = hardTable0;
            logicalTables[1] = hardTable1;
            logicalTables[2] = hardTable0;
            logicalTables[3] = hardTable1;
            break;
        case OneScreen:
            logicalTables[0] = hardTable0;
            logicalTables[1] = hardTable0;
            logicalTables[2] = hardTable0;
            logicalTables[3] = hardTable0;
            break;
        case FourScreen: // FIXME
            logicalTables[0] = hardTable1;
            logicalTables[1] = hardTable1;
            logicalTables[2] = hardTable1;
            logicalTables[3] = hardTable1;
            break;
        }
    }
    
    /**
     * 读取nametable
     * 
     * @param addr
     * @return
     */
    @Override
    public byte read(short addr) {
        return logicalTables[(addr & 0xc00) >> 10][(addr & 0x3ff)];
    }
    
    /**
     * 写nametable
     * 
     * @param addr
     * @param value
     */
    @Override
    public void write(short addr, byte value) {
        logicalTables[(addr & 0xc00) >> 10][(addr & 0x3ff)] = value;
    }
}
