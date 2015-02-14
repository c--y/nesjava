/**
 * 
 */
package nesjava.hardware;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import nesjava.hardware.ppu.RegControl;
import nesjava.hardware.ppu.RegMask;
import nesjava.hardware.ppu.RegStatus;
import nesjava.hardware.ppu.SprRam;
import nesjava.hardware.ppu.Sprite;
import nesjava.hardware.ppu.VRam;

/**
 * Picture Processing Unit(2C02)
 * 
 * @author chenyan
 *
 */
public class PPU {
    
    /**
     * Referenced CPU
     */
    CPU cpu;

    // Registers
    
    /**
     * Controller(0x2000) write
     */
    RegControl control;
    
    /**
     * Mask(0x2001) write
     */
    RegMask mask;
    
    /**
     * Status(0x2002) read
     */
    RegStatus status;
    
    /**
     * OAM Address(0x2003)
     */
    byte oamAddress;
    
    /**
     * OAM Data(0x2004)
     */
    byte oamData;
    
    /**
     * Scroll(0x2005)
     */
    byte scroll;
    
    /**
     * Address(0x2006), 16bits
     *
     *   Used to set the address of PPU Memory to be accessed via
     *   $2007. The first write to this register will set 8 lower
     *   address bits. The second write will set 6 upper bits. The
     *   address will increment either by 1 or by 32 after each
     *   access to $2007 (see "PPU Memory").
     */
    short address;
    
    /**
     * Data(0x2007)
     */
    byte data;
    
    // Registers end
    
    /**
     * Address Latch.
     */
    short latchAddress;
    
    /**
     * Latch to control the twice write with address register.
     * if not writeLatch then write lower 8bits of address else upper 8bits.
     */
    boolean writeLatch = false;
    
    /**
     * Video Ram
     */
    VRam vram;
    
    /**
     * 256 bytes Sprite Ram
     */
    SprRam sprRam = new SprRam();
    
    /**
     * 64 Sprites
     */
    Sprite[] sprites = new Sprite[SPRITES_NUM];
    
    /**
     * Frame Buffer lock
     */
    Lock frameLock = new ReentrantLock();
    
    /**
     * Screen Frame Buffer
     */
    long[] frame = new long[256 * 240]; 
    
    
    long cycle;
    
    int scanLine;
    
    
    /**
     * PPU constructor.
     */
    public PPU() {
        vram = new VRam(this);
        for (byte i = 0; i < SPRITES_NUM; i++) {
            sprites[i] = new Sprite(i, sprRam, vram);
        }
        
        cycle = 0;
        scanLine = 241;
    }
    
    /**
     * Inject the CPU dependency.
     * 
     * @param cpu
     */
    public void initial(CPU cpu) {
        this.cpu = cpu;
    }
    
    /**
     * Write the PPU Registers.
     * 
     * @param addr
     * @param value
     */
    public void writeReg(short addr, byte value) {
        // 0x07 = 111b
        switch (addr & 0x07) {
        case 0x00:
            control.write(value);
            break;
        case 0x01:
            mask.write(value);
            break;
        case 0x02:
            status.write(value);
            break;
        case 0x03:
            oamAddress = value;
            break;
        case 0x04:
            writeOamData(value);
            break;
        case 0x05:
            scroll = value;
            break;
        case 0x06:
            address = value;
            break;
        case 0x07:
            data = value;
            break;
        }
    }
    
    /**
     * Read the PPU Registers.
     * 
     * @param addr
     * @return
     */
    public byte readReg(short addr) {
        byte value = 0;
        // 0x07 = 111b
        switch (addr & 0x07) {
        case 0x00:
            value = control.read();
            break;
        case 0x01:
            value = mask.read();
            break;
        case 0x02:
            value = status.read();
            break;
        case 0x03:
            value = oamAddress;
            break;
        case 0x04:
            value = readOamData();
            break;
        case 0x05:
            value = scroll;
            break;
        case 0x07:
            value = data;
            break;
        }
        return value;
    }
    
    public byte readOamData() {
        return sprRam.read(oamAddress);
    }
    
    public void writeOamData(byte value) {
        sprRam.write(oamAddress, value);
        // TODO updateBufferedSpriteMem
        
        oamAddress++;
        oamAddress %= 256;
        
    }
    
    public void writeAddress(byte value) {
        if (writeLatch) {
            latchAddress = (short) (latchAddress & 0xff);
            latchAddress |= ((value) & 0x3f << 8);
        } else {
            // TODO
            latchAddress = (short) (latchAddress & 0x7f00);
            latchAddress |= value;
            address = latchAddress;
        }
        
        writeLatch = !writeLatch;
    }
    
    /**
     * Increment the VRAM address.
     */
    public void incVramAddr() {
        if (control.isVerticalWrite()) {
            address += 0x20;
        } else {
            address += 0x01;
        }
    }
    
    public void spriteEvaluation() {
        
    }

    public void stepRun() {        
    }

}
