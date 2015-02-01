/**
 * 
 */
package nesjava.machine;

import nesjava.hardware.APU;
import nesjava.hardware.CPU;
import nesjava.hardware.PPU;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Nes 模拟器
 * 
 * @author chenyan
 *
 */
@Data
@Slf4j
public class Nes {
    
    /**
     * Memory Size
     */
    public static final int MEMORY_SIZE = 0x4000;
    
    /**
     * Cpu
     */
    CPU cpu;
    
    /**
     * Picture Process Unit
     */
    PPU ppu;
    
    /**
     * 
     */
    APU apu;
    
    /**
     * Internal Memory
     * 
     * Memory layout:
     * <p>
     *    0000-00FF  - RAM for Zero-Page & Indirect-Memory Addressing
     *    0100-01FF  - RAM for Stack Space & Absolute Addressing
     *    0200-3FFF  - RAM for programmer use
     *    4000-7FFF  - Memory mapped I/O
     *    8000-FFF9  - ROM for programmer useage
     *    FFFA       - Vector address for NMI (low byte)
     *    FFFB       - Vector address for NMI (high byte)
     *    FFFC       - Vector address for RESET (low byte)
     *    FFFD       - Vector address for RESET (high byte)
     *    FFFE       - Vector address for IRQ & BRK (low byte)
     *    FFFF       - Vector address for IRQ & BRK  (high byte)
     * </p>
     * 
     * 0000-3FFF为RAM, 长度0x4000个字节.
     */
    byte[] ram = new byte[MEMORY_SIZE]; 
        
}
