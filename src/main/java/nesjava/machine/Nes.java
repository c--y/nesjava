/**
 * 
 */
package nesjava.machine;

import nesjava.hardware.APU;
import nesjava.hardware.CPU;
import nesjava.hardware.Memory;
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
public class Nes implements Runnable {
    
    /**
     * Memory Size
     */
    public static final int MEMORY_SIZE = 0x4000;
    
    /**
     * CPU
     */
    CPU cpu;
    
    /**
     * Picture Process Unit
     */
    PPU ppu;
    
    /**
     * APU
     */
    APU apu;
    
    /**
     * Memory 
     */
    Memory memory;
    
    /**
     * Constructor
     */
    public Nes() {
        ppu = new PPU();
        memory = new Memory();
        cpu = new CPU();
        
        // Inject the dependencies.
        ppu.initial(cpu);
        memory.initialize(ppu);
        cpu.initial(memory);
    }
    
    public void loadRom() {}
    
    public void loadGame() {}
    
    public void saveGame() {}
    
    public void start() {}

    @Override
    public void run() {
        while(true) {
            long stepCycles = cpu.stepRun();
            
            for (int i = 0; i < 3 * stepCycles; i++) {
                ppu.stepRun();
            }
            
            for (int i = 0; i < stepCycles; i++) {
                apu.stepRun();
            }
        }
    }
        
}
