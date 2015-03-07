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
import nesjava.hardware.ppu.VRam;

/**
 * Picture Processing Unit(2C02)
 * 
 * @author chenyan
 *
 */
public class PPU {
    
    /**
     * NTSC width
     */
    public static final int SCREEN_WIDTH = 256;
    
    /**
     * NTSC height
     */
    public static final int SCREEN_HEIGHT = 240;
    
    /**
     * Referenced CPU
     */
    CPU cpu;

    // Registers
    
    /**
     * Controller(0x2000) write
     */
    RegControl controlReg;
    
    /**
     * Mask(0x2001) write
     */
    RegMask maskReg;
    
    /**
     * Status(0x2002) read
     */
    RegStatus statusReg;
    
    /**
     * OAM Address(0x2003)
     */
    byte oamAddressReg;
    
    /**
     * OAM Data(0x2004)
     */
    byte oamDataReg;
    
    /**
     * Scroll(0x2005), fine x
     */
    byte scrollReg;
    
    /**
     * Address(0x2006), 16bits
     *
     *   Used to set the address of PPU Memory to be accessed via
     *   $2007. The first write to this register will set 8 lower
     *   address bits. The second write will set 6 upper bits. The
     *   address will increment either by 1 or by 32 after each
     *   access to $2007 (see "PPU Memory").
     */
    short addressReg;
    
    /**
     * 0x2005 and 0x2006 share this latch.
     */
    boolean scrollLatch = false;
    
    /**
     * Data(0x2007)
     */
    /**
     * 
     */
    byte dataReg;
    
    // Registers end
    
    /**
     * Address Latch.
     */
    short latchAddress;
    
    /**
     * Video Ram
     */
    VRam vram;
    
    /**
     * 256 bytes Sprite Ram
     */
    SprRam sprRam = new SprRam();
    
    /**
     * Frame Buffer lock
     */
    Lock frameLock = new ReentrantLock();
    
    /**
     * Screen Frame Buffer
     */
    long[] frameBuffer = new long[SCREEN_WIDTH * SCREEN_HEIGHT]; 
    
    /**
     * Frame Count 
     */
    long frameCount = 0;
    
    /**
     * Current cycle
     */
    long cycle;
    
    /**
     * 
     */
    long cycles;
    
    /**
     * Current scanline
     */
    int scanline;
    
    boolean enabledNmi = false;
    
    boolean enabledVbl = false;
    
    boolean enabledOverscan = true;
    
    boolean enabledSpriteLimit = true;
    
    
    /**
     * PPU constructor.
     */
    public PPU() {
        cycle = 0;
        scanline = 241;
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
            writeControl(value);
            break;
        case 0x01:
            maskReg.write(value);
            break;
        case 0x02:
            statusReg.write(value);
            break;
        case 0x03:
            oamAddressReg = value;
            break;
        case 0x04:
            writeOamData(value);
            break;
        case 0x05:
            scrollReg = value;
            break;
        case 0x06:
            addressReg = value;
            break;
        case 0x07:
            dataReg = value;
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
            value = controlReg.read();
            break;
        case 0x01:
            value = maskReg.read();
            break;
        case 0x02:
            value = statusReg.read();
            break;
        case 0x03:
            value = oamAddressReg;
            break;
        case 0x04:
            value = readOamData();
            break;
        case 0x05:
            value = scrollReg;
            break;
        case 0x07:
            value = dataReg;
            break;
        }
        return value;
    }
    
    /**
     * Read the OAM Data register.
     * 
     * @return
     */
    public byte readOamData() {
        return sprRam.read(oamAddressReg);
    }
    
    /**
     * Write the OAM Data register.
     * 
     * @param value
     */
    public void writeOamData(byte value) {
        sprRam.write(oamAddressReg, value);
        // TODO updateBufferedSpriteMem
        
        oamAddressReg++;
        oamAddressReg %= 256;
        
    }
    
    /*
     * Writing to 0x2000, 0x2005, 0x2006 are affected by scrolling.
     */
    
    /**
     *  Write the control reg(0x2000)
     * 
     * @param value
     */
    public void writeControl(byte value) {
        controlReg.write(value);
        latchAddress = (short) ((latchAddress & 0xf3ff) | (controlReg.getBaseNameTable() << 10));
    }
    
    /**
     * Write the scroll reg(0x2005).
     * 
     * @param value
     */
    public void writeScroll(byte value) {
        if (scrollLatch) {
            latchAddress &= 0x7fe0;
            latchAddress |= ((value & 0xf8) >> 3);
            scrollReg = (byte) (value & 0x07);
        } else {
            latchAddress &= 0xc1f;
            latchAddress |= (((value & 0xf8) << 2) | ((value & 0x07) << 12)); 
        }
        
        scrollLatch = !scrollLatch;
    }
    
    /**
     * Write the address reg(0x2006)
     * 
     * @param value
     */
    public void writeAddress(byte value) {
        if (scrollLatch) {
            latchAddress &= 0xff;
            latchAddress |= ((value & 0x3f) << 8);
        } else {
            latchAddress &= 0x7f00;
            latchAddress |= value;
            addressReg = latchAddress;
        }
        
        scrollLatch = !scrollLatch;
    }
      
    /**
     * Increment the VRAM address.
     */
    public void incVramAddr() {
        if (controlReg.isVerticalWrite()) {
            addressReg += 0x20;
        } else {
            addressReg += 0x01;
        }
    }
    
    /**
     * Sprite Evaluation
     */
    public void evaluateSprite() {
        
    }

    /**
     * One run of PPU, just used in NTSC.
     */
    public void runStep() {     
        if (scanline == 240) {
            // Idle scanline
            if (cycle == 1) {
                if (!enabledVbl) {
                    statusReg.setInVBlank(true);
                }
                
                // 0x2000:d7 enable/disable NMIs
                if (controlReg.isEnabledVBlank() && enabledNmi) {
                    cpu.nmi();
                }
                
                render();
            }
        } else if (scanline == 260) {
            // End of vblank.
            if (cycle == 1) {
                statusReg.setInVBlank(false);
            } else if (cycle == 341) {
                // End of scanline cycles.
                scanline--;
                cycle = 1;
                frameCount++;
                return;
            }        
        } else if (scanline < 240 && scanline > -1) {
            if (cycle == 254) {
                if (maskReg.isShowBackground()) {
                    // TODO mmc5
                    
                    
                }
                
                if (maskReg.isShowSprites()) {
                    // TODO mmc5
                    
                    evaluateSprite();
                }
            } else if (cycle == 256) {
                if (controlReg.isSpritePatternTable() &&
                        !controlReg.isScreenPatternTable()) {
                    // TODO mmc3
                }
            }
        } else if (scanline == -1) {
            // Invisible scanline
            if (cycle == 1) {
                statusReg.setSprite0Hit(false);
                statusReg.setSpriteOverflow(false);
            } else if (cycle == 304) {
                // Copy scroll latch into VRAMADDR reg.
                if (maskReg.isShowBackground() || maskReg.isShowSprites()) {
                    addressReg = latchAddress;
                }
            }
        }
        
        // Scanline in 240 ~ 260, are vblank time, electron gun repositions.
        // End of one line scan.
        if (cycle == 341) {
            cycle = 0;
            scanline++;
            
            // mmc5
        }
        
        cycle++;
    }

    void render() {
        for (int i = 0; i < frameBuffer.length; i++) {
            
        }
    }
}
