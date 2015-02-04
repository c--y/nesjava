/**
 * 
 */
package nesjava.hardware.ppu;

/**
 * @author chenyan
 *
 */
public class Nametable {
    
    /**
     * 镜像类型
     */
    NametableMirrorType mirrorType;

    /**
     * 物理表0
     */
    byte[] hardTable0 = new byte[0x400];
    
    /**
     * 物理表1
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
    public void changeMirror(NametableMirrorType mirrorType) {
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
        case FourScreen:
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
    public byte read(short addr) {
        return logicalTables[(addr & 0xc00) >> 10][(addr & 0x3ff)];
    }
    
    /**
     * 写nametable
     * 
     * @param addr
     * @param value
     */
    public void write(short addr, byte value) {
        logicalTables[(addr & 0xc00) >> 10][(addr & 0x3ff)] = value;
    }
}
