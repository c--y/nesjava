/**
 * 
 */
package nesjava.hardware;

import nesjava.util.ByteUtils;

/**
 * 6502处理器
 * 
 * @author chenyan
 *
 */
public class CPU {
    
    /**
     * Program Counter
     */
    short pc;
    
    /**
     * Accumulator
     */
    byte a;
    
    /**
     * X Index
     */
    byte x;
    
    /**
     * Y Index
     */
    byte y;
    
    /**
     * Stack Pointer
     */
    byte sp;
    
    /**
     * Flag Register
     * 
     * Structure:
     * <p>
     *  Bit no.:  7  6  5  4  3  2  1  0
        Flag ID:  N  V  -  B  D  I  Z  C
                  |  |     |  |  |  |  |
                  |  |     |  |  |  |  +-- Carry
                  |  |     |  |  |  +-- Zero
                  |  |     |  |  +-- block IRQ Interrupts
                  |  |     |  +-- enable binary coded Decimal
                  |  |     +-- BRK was executed
                  |  |
                  |  +-- oVerflow
                  +-- Negative*
     * </p>
     */
    byte p;
       
    /**
     * Clock
     */
    long clock;
    
    /**
     * Memory Access
     */
    Memory ram;
    
    /**
     * Opcode Executor
     */
    OpcodeExecutor opExec;
    
    /**
     * 
     * @param ram
     */
    public CPU(Memory ram) {
        this.ram = ram;
        this.opExec = new OpcodeExecutor(this);
    }
    
    /**
     * Absolute Indexed Mode
     * 
     * @param index
     * @return address
     */
    public short absoluteIndexedMode(short index) {
        
        // 2bytes inst length
        byte high = ram.read((short) (pc + 1));
        byte low = ram.read(pc);
        
        short addr = ByteUtils.makeWord(high, low);
        
        // Cross page penalty.
        if (Memory.isSamePage(addr, (short) (addr + index))) {
            clock++;
        }
        
        addr += index;
        pc += 2;
        return addr;
    }
    
    /**
     * Absolute Mode
     * 
     * @return address
     */
    public short absoluteMode() {
        // 2bytes inst length
        byte high = ram.read((short) (pc + 1));
        byte low = ram.read(pc);
        pc += 2;
        return ByteUtils.makeWord(high, low);
    }
    
    /**
     * Accumulator Mode
     * XXX use A register, not memory.
     * 
     * @return nothing
     */
    public short accumulatorMode() {
        return 0;
    }
    
    /**
     * Immediate Mode
     * 
     * @return address
     */
    public short immediateMode() {
        pc++;
        return (short) (pc - 1);
    }

    /**
     * Indexed Indirect Mode
     * 
     * @return address
     */
    public short indexedIndirectMode() {
        byte base = ram.read(pc);
        base += x;
        pc++;
        byte high = ram.read((short) (base + 1));
        byte low = ram.read((short) base);
        return ByteUtils.makeWord(high, low);
    }

    /**
     * 
     * 
     * @param address
     * @return
     */
    public short indirectAbsoluteMode(short address) {
        byte high = ram.read((short) (address + 1));
        byte low = ram.read(address);
        
        short highAddr = ByteUtils.makeWord(high, (byte) (low + 1));
        short lowAddr = ByteUtils.makeWord(high, low);
        
        byte indHigh = ram.read(highAddr);
        byte indLow = ram.read(lowAddr);
        
        return ByteUtils.makeWord(indHigh, indLow);
    }

    /**
     * Indirect Indexed Mode
     * 
     * @return address
     */ 
    public short indirectIndexedMode() {
        byte base = ram.read(pc);
        byte high = ram.read((short) (base + 1));
        byte low = ram.read(base);
        
        short addr = ByteUtils.makeWord(high, low);
        if (Memory.isSamePage(addr, (short) (addr + y))) {
            clock++;
        }
        
        addr += y;
        pc++;
        return addr;
    }

    /**
     * Indirect Mode
     * 
     * @return
     */
    public short indirectMode() {
        return indirectAbsoluteMode(pc);
    }

    /**
     * RelativeMode
     * 
     * @return
     */
    public short relativeMode() {
        short b = ram.read(pc);
        b = (short) ((b < 0x80)? b + pc: b + (pc - 0x100));
        return ++b;
    }

    /**
     * Zero Page Mode
     * 
     * @return
     */
    public short zeroPageMode() {
        pc++;
        short ret = ram.read((short) (pc - 1));
        return ret;
    }

    /**
     * ZeroPage Indexed Mode
     * 
     * @param index
     * @return
     */
    public short zeroPageIndexedMode(short index) {
        byte base = ram.read(pc);
        pc++;
        return (short) (base + index);
    }

    /**
     * Flag Methods
     */
    public boolean p_B() {
        return (p & 0x10) == 0x10;
    }
    
    public boolean p_C() {
        return (p & 0x01) == 0x01;
    }

    public boolean p_D() {
        return (p & 0x08) == 0x08;
    }

    public boolean p_I() {
        return (p & 0x04) == 0x04;
    }

    public boolean p_N() {
        return (p & 0x80) == 0x80;
    }

    public boolean p_V() {
        return (p & 0x40) == 0x40;
    }

    public boolean p_Z() {
        return (p & 0x02) == 0x01;
    }

    public void p_setB(boolean val) {
        p = (byte) (val? (p | 0x10): (p & 0xef));
    }

    public void p_setC(boolean val) {
        p = (byte) (val? (p | 0x01): (p & 0xfe));
    }

    public void p_setD(boolean val) {
        p = (byte) (val? (p | 0x08): (p & 0xf7));
    }

    public void p_setI(boolean val) {
        p = (byte) (val? (p | 0x04): (p & 0xfb));
        p |= 0x04;
    }

    public void p_setN(boolean val) {
        p = (byte) (val? (p | 0x80): (p & 0x7f));
    }

    public void p_setV(boolean val) {
        p = (byte) (val? (p | 0x40): (p & 0xbf));
    }

    public void p_setZ(boolean val) {
        p = (byte) (val? (p | 0x02): (p & 0xfd));
    }

    /**
     * Stack Methods
     */
    
    /**
     * Poke value onto Stack.
     * 
     * @param val
     */
    public void pushStack(byte val) {
        ram.write(sp, val);
        sp--;
    }
    
    /**
     * Peek value from Stack.
     * 
     * @return
     */
    public byte pullStack() {
        return ram.read(sp++);
    }
}
