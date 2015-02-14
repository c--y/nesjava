/**
 * 
 */
package nesjava.hardware.ppu;

import nesjava.hardware.MemoryAccessable;
import nesjava.hardware.PPU;

/**
 * Video Ram
 * 
 * 16k bits = 2048(0x800) bytes
 * 
 * @author chenyan
 *
 */
public class VRam implements MemoryAccessable {
    
    /** 
     * PPU
     */
    PPU ppu;
    

    
    /**
     * Internal VRam
     */
    byte[] units = new byte[0x800];
    
    /**
     * 2 Pattern Tables, each one stores 0x100(256) tiles.
     * 
     * Memory Address:
     *  #0 0x0000, size=0x1000
     *  #1 0x1000, size=0x1000
     */
    // byte[][] patternTables = new byte[2][0x1000];
    PatternTables patternTables = new PatternTables();
    
    /**
     * 4 Attribute Tables
     */
    AttributeTables attributeTables = new AttributeTables();
    
    /**
     * NameTables
     */
    NameTables nameTables = new NameTables();
    
    /**
     * 2 Palettes
     */
    Palettes palettes = new Palettes();
    
    /**
     * Constructor
     * 
     * @param ppu
     */
    public VRam(PPU ppu) {
        this.ppu = ppu;
    }

    /**
     * Read VRam byte
     * 
     * @param addr
     * @return
     */
    public byte read(short addr) {
        if (addr >= 0x0000 && addr < 0x2000) {
            // pattern table #0, #1
            return patternTables.read(addr);
        } else if (addr >= 0x2000 && addr < 0x23c0) {
            return nameTables.read(addr);
        } else if (addr >= 0x23c0 && addr < 0x2400) {
            return attributeTables.read(addr);
        } else if (addr >= 0x2400 && addr < 0x27c0) {
            return nameTables.read(addr);
        } else if (addr >= 0x27c0 && addr < 0x2800) {
            return attributeTables.read(addr);
        } else if (addr >= 0x2800 && addr < 0x2bc0) {
            return nameTables.read(addr);
        } else if (addr >= 0x2bc0 && addr < 0x2c00) {
            return nameTables.read(addr);
        } else if (addr >= 0x2c00 && addr < 0x2fc0) {
            return nameTables.read(addr);
        } else if (addr >= 0x2fc0 && addr < 0x3000) {
            return nameTables.read(addr);
        } else if (addr >= 0x3000 && addr < 0x3f00) {
            // Mirror of 0x2000~0x2eff
            return this.read((short) (addr - 0x1000));
        } else if (addr >= 0x3f00 && addr < 0x4000) {
            return palettes.read(addr);
        } else if (addr >= 0x4000 && addr < 0xc000) {
            // TODO optimize
            // Mirror of 0x0000~0x3fff
            short mirrorAddr = (short) (addr - (addr * (addr / 0x4000)));
            return this.read(mirrorAddr);
        }
       
        throw new IllegalArgumentException();
    }
    
    /**
     * Write a byte to VRam
     * 
     * @param addr
     * @param value
     */
    public void write(short addr, byte value) {
        if (addr >= 0x0000 && addr < 0x2000) {
            // pattern table #0, #1
            patternTables.write(addr, value);
        } else if (addr >= 0x2000 && addr < 0x23c0) {
            nameTables.write(addr, value);
        } else if (addr >= 0x23c0 && addr < 0x2400) {
            attributeTables.write(addr, value);
        } else if (addr >= 0x2400 && addr < 0x27c0) {
            nameTables.write(addr, value);
        } else if (addr >= 0x27c0 && addr < 0x2800) {
            attributeTables.write(addr, value);
        } else if (addr >= 0x2800 && addr < 0x2bc0) {
            nameTables.write(addr, value);
        } else if (addr >= 0x2bc0 && addr < 0x2c00) {
            nameTables.write(addr, value);
        } else if (addr >= 0x2c00 && addr < 0x2fc0) {
            nameTables.write(addr, value);
        } else if (addr >= 0x2fc0 && addr < 0x3000) {
            nameTables.write(addr, value);
        } else if (addr >= 0x3000 && addr < 0x3f00) {
            // Mirror of 0x2000~0x2eff
            this.write((short) (addr - 0x1000), value);
        } else if (addr >= 0x3f00 && addr < 0x4000) {
            palettes.write(addr, value);
        } else if (addr >= 0x4000 && addr < 0xc000) {
            // TODO optimize
            // Mirror of 0x0000~0x3fff
            short mirrorAddr = (short) (addr - (addr * (addr / 0x4000)));
            this.write(mirrorAddr, value);
        }
       
        throw new IllegalArgumentException();
    }
    
}
