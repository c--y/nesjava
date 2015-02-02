/**
 * 
 */
package nesjava.hardware;

import nesjava.util.ByteUtils;

/**
 * 字节码执行器
 * 
 * @author chenyan
 *
 */
public class OpcodeExecutor {

    CPU cpu;
    
    /**
     * Constructor
     * 
     * @param cpu 
     */
    public OpcodeExecutor(CPU cpu) {
        if (cpu == null) {
            throw new IllegalArgumentException("cpu is null");
        }
        this.cpu = cpu;
    }
    
    /**
     * Opcodes
     * 
     * Address Mode abbreviation:
     *  Absolute = Abs
     *  Absolute Indexed = AbsX, AbsY
     *  Accumulator = Acc
     *  Immediate = Immed
     *  Implied = Implied
     *  Indexed Indirect = IndX
     *  Indirect Indexed = Ind_Y
     *  Indirect = Ind
     *  Relative = Rel
     *  ZeroPage = ZP
     *  ZeroPage Indexed = ZPX, ZPY
     */
    
    public void execute(byte opcode) {
        int iop = opcode;
        switch (iop) {
        // BRK
        case 0x00:
            cpu.opcodeCycles = 7;
            BRK();
            break;
        // ORA (Indirect,X)
        case 0x01:
            cpu.opcodeCycles = 6;
            ORA(cpu.indirectIndexedMode());
            break;
        case 0x05:
            cpu.opcodeCycles = 2;
            ORA(cpu.zeroPageMode());
            break;
        // ORA (Zero Page)
        case 0x06:
            cpu.opcodeCycles = 5;
            ASL(cpu.zeroPageMode());
            break;
        // PHP (Implied)
        case 0x08:
            cpu.opcodeCycles = 3;
            PHP();
            break;
        // ORA (Immediate)
        case 0x09:
            cpu.opcodeCycles = 2;
            ORA(cpu.immediateMode());
            break;
        case 0x0a:
            cpu.opcodeCycles = 2;
            cpu.accumulatorMode();
            ASLAcc();
            break;
        // ORA (Absolute)
        case 0x0d:
            cpu.opcodeCycles = 4;
            ORA(cpu.absoluteMode());
            break;
        // ASL (Absolute)
        case 0x0e:
            cpu.opcodeCycles = 6;
            ASL(cpu.absoluteMode());
            break;
        // BPL (Implied)
        case 0x10:
            cpu.opcodeCycles = 2;
            BPL(cpu.relativeMode());
            break;
        // ORA (Indirect), Y
        case 0x11:
            cpu.opcodeCycles = 5;
            ORA(cpu.indirectIndexedMode());
            break;
        // ORA (Zero Page, X)
        case 0x15:
            cpu.opcodeCycles = 3;
            ORA(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // ASL (Zero Page, X)
        case 0x16:
            cpu.opcodeCycles = 6;
            ASL(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // CLC (Implied)
        case 0x18:
            cpu.opcodeCycles = 2;
            CLC();
            break;
        // ORA (Absolute, Y)
        case 0x19:
            cpu.opcodeCycles = 4;
            ORA(cpu.absoluteIndexedMode(cpu.y));
            break;
        // ORA (Absolute, X)
        case 0x1d:
            cpu.opcodeCycles = 4;
            ORA(cpu.absoluteIndexedMode(cpu.x));
            break;
        // ASL (Absolute, X)
        case 0x1e:
            cpu.opcodeCycles = 7;
            ASL(cpu.absoluteIndexedMode(cpu.x));
            break;
        // JSR (Implied)
        case 0x20:
            cpu.opcodeCycles = 6;
            JSR(cpu.absoluteMode());
            break;
        // AND (Indirect, X)
        case 0x21:
            cpu.opcodeCycles = 6;
            AND(cpu.indirectIndexedMode());
            break;
        // BIT (Zero Page)
        case 0x24:
            cpu.opcodeCycles = 3;
            BIT(cpu.zeroPageMode());
            break;
        // AND (Zero Page)
        case 0x25:
            cpu.opcodeCycles = 2;
            AND(cpu.zeroPageMode());
            break;
        // ROL (Zero Page)
        case 0x26:
            cpu.opcodeCycles = 5;
            ROL(cpu.zeroPageMode());
            break;
        // PLP (Implied)
        case 0x28:
            cpu.opcodeCycles = 4;
            PLP();
            break;
        // AND (Immediate)
        case 0x29:
            cpu.opcodeCycles = 2;
            AND(cpu.immediateMode());
            break;
        // ROL (Accumulator)
        case 0x2a:
            cpu.opcodeCycles = 2;
            cpu.accumulatorMode();
            ROLAcc();
            break;
        // BIT (Absolute)
        case 0x2c:
            cpu.opcodeCycles = 4;
            BIT(cpu.absoluteMode());
            break;
        // AND (Absolute)
        case 0x2d:
            cpu.opcodeCycles = 4;
            AND(cpu.absoluteMode());
            break;
        // ROL (Absolute)
        case 0x2e:
            cpu.opcodeCycles = 6;
            ROL(cpu.absoluteMode());
            break;
        // BMI (Relative)
        case 0x30:
            cpu.opcodeCycles = 2;
            BMI(cpu.relativeMode());
            break;
        // AND (Indirect), Y
        case 0x31:
            cpu.opcodeCycles = 5;
            AND(cpu.indirectIndexedMode());
            break;
        // AND (ZeroPage, X)
        case 0x35:
            cpu.opcodeCycles = 3;
            AND(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // ROL (ZeroPage, X)
        case 0x36:
            cpu.opcodeCycles = 6;
            ROL(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // SEC (Implied)
        case 0x38:
            cpu.opcodeCycles = 2;
            SEC();
            break;
        // AND (Absolute, Y)
        case 0x39:
            cpu.opcodeCycles = 4;
            AND(cpu.absoluteIndexedMode(cpu.y));
            break;
        // AND (Absolute, X)
        case 0x3d:
            cpu.opcodeCycles = 4;
            AND(cpu.absoluteIndexedMode(cpu.x));
            break;
        // ROL (Absolute, X)
        case 0x3e:
            cpu.opcodeCycles = 7;
            ROL(cpu.absoluteIndexedMode(cpu.x));
            break;
        // RTI (Implied)
        case 0x40:
            cpu.opcodeCycles = 6;
            RTI();
            break;
        // EOR (Indirect, X)
        case 0x41:
            cpu.opcodeCycles = 6;
            EOR(cpu.indirectIndexedMode());
            break;
        // EOR (Zero Page)
        case 0x45:
            cpu.opcodeCycles = 3;
            EOR(cpu.zeroPageMode());
            break;
        // LSR (Zero Page)
        case 0x46:
            cpu.opcodeCycles = 5;
            LSR(cpu.zeroPageMode());
            break;
        // PHA (Implied)
        case 0x48:
            cpu.opcodeCycles = 3;
            PHA();
            break;
        // EOR (Immediate)
        case 0x49:
            cpu.opcodeCycles = 2;
            EOR(cpu.immediateMode());
            break;
        // LSR (Accumulator)
        case 0x4a:
            cpu.opcodeCycles = 2;
            cpu.accumulatorMode();
            LSRAcc();
            break;
        // JMP (Absolute)
        case 0x4c:
            cpu.opcodeCycles = 3;
            JMP(cpu.absoluteMode());
            break;
        // EOR (Absolute)
        case 0x4d:
            cpu.opcodeCycles = 4;
            EOR(cpu.absoluteMode());
            break;
        // LSR (Absolute)
        case 0x4e:
            cpu.opcodeCycles = 6;
            LSR(cpu.absoluteMode());
            break;
        // BVC (Relative)
        case 0x50:
            BVC(cpu.relativeMode());
            break;
        // EOR (Indirect, Y)
        case 0x51:
            cpu.opcodeCycles = 5;
            EOR(cpu.indirectIndexedMode());
            break;
        // EOR (ZeroPage, X)
        case 0x55:
            cpu.opcodeCycles = 4;
            EOR(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // LSR (ZeroPage, X)
        case 0x56:
            cpu.opcodeCycles = 6;
            LSR(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // CLI (Implied)
        case 0x58:
            CLI();
            break;
        // EOR (Absolute, Y)
        case 0x59:
            cpu.opcodeCycles = 4;
            EOR(cpu.absoluteIndexedMode(cpu.y));
            break;
        // EOR (Absolute, X)    
        case 0x5d:
            cpu.opcodeCycles = 4;
            EOR(cpu.absoluteIndexedMode(cpu.x));
            break;
        // LSR (Absolute, X)
        case 0x5e:
            cpu.opcodeCycles = 7;
            LSR(cpu.absoluteIndexedMode(cpu.x));
            break;
        // RTS (Implied)
        case 0x60:
            cpu.opcodeCycles = 6;
            RTS();
            break;
        // ADC (Indirect, X)
        case 0x61:
            cpu.opcodeCycles = 6;
            ADC(cpu.indirectIndexedMode());
            break;
        // ADC (Zero Page)    
        case 0x65:
            cpu.opcodeCycles = 3;
            ADC(cpu.zeroPageMode());
            break;
        // ROR (Zero Page)
        case 0x66:
            cpu.opcodeCycles = 5;
            ROR(cpu.zeroPageMode());
            break;
        // PLA (Implied)
        case 0x68:
            cpu.opcodeCycles = 6;
            PLA();
            break;
        // ADC (Immediate)
        case 0x69:
            cpu.opcodeCycles = 2;
            ADC(cpu.immediateMode());
            break;
        // ROR (Accumulator)
        case 0x6a:
            cpu.opcodeCycles = 2;
            cpu.accumulatorMode();
            RORAcc();
            break;
        // JMP (Indirect)
        case 0x6c:
            cpu.opcodeCycles = 5;
            JMP(cpu.indirectMode());
            break;
        // ADC (Absolute)
        case 0x6d:
            cpu.opcodeCycles = 4;
            ADC(cpu.absoluteMode());
            break;
        // ROR (Absolute)
        case 0x6e:
            cpu.opcodeCycles = 6;
            ROR(cpu.absoluteMode());
            break;
        // BVS (Relative)
        case 0x70:
            cpu.opcodeCycles = 2;
            BVS(cpu.relativeMode());
            break;
        // ADC (Indirect), Y
        case 0x71:
            cpu.opcodeCycles = 5;
            ADC(cpu.indirectIndexedMode());
            break;
        // ADC (ZeroPage, X)
        case 0x75:
            cpu.opcodeCycles = 4;
            ADC(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // ROR (ZeroPage, X)
        case 0x76:
            cpu.opcodeCycles = 6;
            ROR(cpu.zeroPageIndexedMode(cpu.x));
            break;
        // SEI (Implied)
        case 0x78:
            cpu.opcodeCycles = 2;
            SEI();
            break;
        case 0x79:
            cpu.opcodeCycles = 4;
            ADC(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0x7d:
            cpu.opcodeCycles = 4;
            ADC(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0x7e:
            cpu.opcodeCycles = 7;
            ROR(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0x81:
            cpu.opcodeCycles = 6;
            STA(cpu.indirectIndexedMode());
            break;
        case 0x84:
            cpu.opcodeCycles = 3;
            STY(cpu.zeroPageMode());
            break;
        case 0x85:
            cpu.opcodeCycles = 3;
            STA(cpu.zeroPageMode());
            break;
        case 0x86:
            cpu.opcodeCycles = 3;
            STX(cpu.zeroPageMode());
            break;
        case 0x88:
            cpu.opcodeCycles = 2;
            DEY();
            break;
        case 0x8a:
            cpu.opcodeCycles = 2;
            TXA();
            break;
        case 0x8c:
            cpu.opcodeCycles = 4;
            STY(cpu.absoluteMode());
            break;
        case 0x8d:
            cpu.opcodeCycles = 4;
            STA(cpu.absoluteMode());
            break;
        case 0x8e:
            cpu.opcodeCycles = 4;
            STX(cpu.absoluteMode());
            break;
        case 0x90:
            cpu.opcodeCycles = 2;
            BCC(cpu.relativeMode());
            break;
        case 0x91:
            cpu.opcodeCycles = 6;
            STA(cpu.indirectIndexedMode());
            break;
        case 0x94:
            cpu.opcodeCycles = 4;
            STY(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0x95:
            cpu.opcodeCycles = 4;
            STA(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0x96:
            cpu.opcodeCycles = 4;
            STX(cpu.zeroPageIndexedMode(cpu.y));
            break;
        case 0x98:
            cpu.opcodeCycles = 2;
            TYA();
            break;
        case 0x99:
            cpu.opcodeCycles = 5;
            STA(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0x9a:
            cpu.opcodeCycles = 2;
            TXS();
            break;
        case 0x9d:
            cpu.opcodeCycles = 5;
            STA(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xa0:
            cpu.opcodeCycles = 2;
            LDY(cpu.immediateMode());
            break;
        case 0xa1:
            cpu.opcodeCycles = 6;
            LDA(cpu.indirectIndexedMode());
            break;
        case 0xa2:
            cpu.opcodeCycles = 2;
            LDX(cpu.immediateMode());
            break;
        case 0xa4:
            cpu.opcodeCycles = 3;
            LDY(cpu.zeroPageMode());
            break;
        case 0xa5:
            cpu.opcodeCycles = 3;
            LDA(cpu.zeroPageMode());
            break;
        case 0xa6:
            cpu.opcodeCycles = 3;
            LDX(cpu.zeroPageMode());
            break;
        case 0xa8:
            cpu.opcodeCycles = 2;
            TAY();
            break;
        case 0xa9:
            cpu.opcodeCycles = 2;
            LDA(cpu.immediateMode());
            break;
        case 0xaa:
            cpu.opcodeCycles = 2;
            TAX();
            break;
        case 0xac:
            cpu.opcodeCycles = 4;
            LDY(cpu.absoluteMode());
            break;
        case 0xad:
            cpu.opcodeCycles = 4;
            LDA(cpu.absoluteMode());
            break;
        case 0xae:
            cpu.opcodeCycles = 4;
            LDX(cpu.absoluteMode());
            break;
        case 0xb0:
            cpu.opcodeCycles = 2;
            BCS(cpu.relativeMode());
            break;
        case 0xb1:
            cpu.opcodeCycles = 5;
            LDA(cpu.indirectIndexedMode());
            break;
        case 0xb4:
            cpu.opcodeCycles = 4;
            LDY(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xb5:
            cpu.opcodeCycles = 4;
            LDA(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xb6:
            cpu.opcodeCycles = 4;
            LDX(cpu.zeroPageIndexedMode(cpu.y));
            break;
        case 0xb8:
            cpu.opcodeCycles = 2;
            CLV();
            break;
        case 0xb9:
            cpu.opcodeCycles = 4;
            LDA(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0xba:
            cpu.opcodeCycles = 2;
            TSX();
            break;
        case 0xbc:
            cpu.opcodeCycles = 4;
            LDY(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xbd:
            cpu.opcodeCycles = 4;
            LDA(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xbe:
            cpu.opcodeCycles = 4;
            LDX(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0xc0:
            cpu.opcodeCycles = 2;
            CPY(cpu.immediateMode());
            break;
        case 0xc1:
            cpu.opcodeCycles = 6;
            CMP(cpu.indirectIndexedMode());
            break;
        case 0xc4:
            cpu.opcodeCycles = 3;
            CPY(cpu.zeroPageMode());
            break;
        case 0xc5:
            cpu.opcodeCycles = 3;
            CMP(cpu.zeroPageMode());
            break;
        case 0xc6:
            cpu.opcodeCycles = 5;
            DEC(cpu.zeroPageMode());
            break;
        case 0xc8:
            cpu.opcodeCycles = 2;
            INY();
            break;
        case 0xc9:
            cpu.opcodeCycles = 2;
            CMP(cpu.immediateMode());
            break;
        case 0xca:
            cpu.opcodeCycles = 2;
            DEX();
            break;
        case 0xcc:
            cpu.opcodeCycles = 4;
            CPY(cpu.absoluteMode());
            break;
        case 0xcd:
            cpu.opcodeCycles = 4;
            CMP(cpu.absoluteMode());
            break;
        case 0xce:
            cpu.opcodeCycles = 6;
            DEC(cpu.absoluteMode());
            break;
        case 0xd0:
            cpu.opcodeCycles = 2;
            BNE(cpu.relativeMode());
            break;
        case 0xd1:
            cpu.opcodeCycles = 5;
            CMP(cpu.indirectIndexedMode());
            break;
        case 0xd5:
            cpu.opcodeCycles = 4;
            CMP(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xd6:
            cpu.opcodeCycles = 6;
            DEC(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xd8:
            cpu.opcodeCycles = 2;
            CLD();
            break;
        case 0xd9:
            cpu.opcodeCycles = 4;
            CMP(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0xdd:
            cpu.opcodeCycles = 4;
            CMP(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xde:
            cpu.opcodeCycles = 7;
            DEC(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xe0:
            cpu.opcodeCycles = 2;
            CPX(cpu.immediateMode());
            break;
        case 0xe1:
            cpu.opcodeCycles = 6;
            SBC(cpu.indirectIndexedMode());
            break;
        case 0xe4:
            cpu.opcodeCycles = 3;
            CPX(cpu.zeroPageMode());
            break;
        case 0xe5:
            cpu.opcodeCycles = 3;
            SBC(cpu.zeroPageMode());
            break;
        case 0xe6:
            cpu.opcodeCycles = 5;
            INC(cpu.zeroPageMode());
            break;
        case 0xe8:
            cpu.opcodeCycles = 2;
            INX();
            break;
        case 0xe9:
            cpu.opcodeCycles = 2;
            SBC(cpu.immediateMode());
            break;
        case 0xea:
            cpu.opcodeCycles = 2;
            NOP();
            break;
        case 0xec:
            cpu.opcodeCycles = 4;
            CPX(cpu.absoluteMode());
            break;
        case 0xed:
            cpu.opcodeCycles = 4;
            SBC(cpu.absoluteMode());
            break;
        case 0xee:
            cpu.opcodeCycles = 6;
            INC(cpu.absoluteMode());
            break;
        case 0xf0:
            BEQ(cpu.relativeMode());
            break;
        case 0xf1:
            cpu.opcodeCycles = 5;
            SBC(cpu.indirectIndexedMode());
            break;
        case 0xf5:
            cpu.opcodeCycles = 4;
            SBC(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xf6:
            cpu.opcodeCycles = 6;
            INC(cpu.zeroPageIndexedMode(cpu.x));
            break;
        case 0xf8:
            cpu.opcodeCycles = 2;
            SED();
            break;
        case 0xf9:
            cpu.opcodeCycles = 4;
            SBC(cpu.absoluteIndexedMode(cpu.y));
            break;
        case 0xfd:
            cpu.opcodeCycles = 4;
            SBC(cpu.absoluteIndexedMode(cpu.x));
            break;
        case 0xfe:
            cpu.opcodeCycles = 7;
            INC(cpu.absoluteIndexedMode(cpu.x));
            break;
    
        default:
            // Future Expansion
            // 
            break;
        }
    }

    /**
     * Add Memory to A with Carry.
     * 
     * @param m
     */
    private void ADC(short addr) {
        byte m = cpu.ram.read(addr);
        short t = (short) (cpu.a + m + (cpu.p_C()? 1: 0));
        // P.v = A.7 != t.7? 1: 0
        cpu.p_setV(ByteUtils.isBitSet(cpu.a, 7) != ByteUtils.isBitSet(t, 7));
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(t == 0);
        /*
         * if (P.d)
         *      t = bcd(A) + bcd(m) + P.c
         *      P.c = (t > 99)? 1: 0
         * else
         *      P.c = (t > 255)? 1: 0
         */
        cpu.p_setC(t > 255);
        cpu.a = (byte) (t & 0xff);
    }

    /**
     * Bitwise-AND with Memory.
     * 
     * @param m
     */
    private void AND(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.a = (byte) (cpu.a & m);
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Arithmetic shift left.
     * 
     * @param addr
     */
    private void ASL(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.p_setC(ByteUtils.isBitSet(m, 7));
        m <<= 1;
        cpu.p_setN(ByteUtils.isBitSet(m, 7));
        cpu.p_setZ(m == 0);
        cpu.ram.write(addr, m);
    }
    
    /**
     * Arithmetic shift left with Accumulator.
     */
    private void ASLAcc() {
        cpu.p_setC(ByteUtils.isBitSet(cpu.a, 7));
        cpu.a <<= 1;
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Branch iff P.c is CLEAR.
     * 
     * @param addr
     */
    private void BCC(short addr) {
        byte m = cpu.ram.read(addr);
        if (!cpu.p_C()) {
            // GOTO
            cpu.pc += m;
            cpu.branchOpcodeCycles(addr);
        }
    }

    /**
     * Branch iff P.c is SET.
     * 
     * @param addr
     */
    private void BCS(short addr) {
        byte m = cpu.ram.read(addr);
        if (cpu.p_C()) {
            // GOTO
            cpu.pc += m;
            cpu.branchOpcodeCycles(addr);
        }
        
    }

    /**
     * Branch iff P.z is SET.
     * 
     * @param addr
     */
    private void BEQ(short addr) {
        byte m = cpu.ram.read(addr);
        if (cpu.p_Z()) {
            // GOTO
            cpu.pc += m;
        }
    }

    /**
     * Test bits in A with M.
     * 
     * @param addr
     */
    private void BIT(short addr) {
        byte m = cpu.ram.read(addr);
        byte t = (byte) (cpu.a & m);
        cpu.p_setN(ByteUtils.isBitSet(t, 7));
        cpu.p_setV(ByteUtils.isBitSet(t, 6));
        cpu.p_setZ(t == 0);
    }

    /**
     * Branch iff P.n is SET.
     * 
     * @param addr
     */
    private void BMI(short addr) {
        byte m = cpu.ram.read(addr);
        if (cpu.p_N()) {
            // GOTO
            cpu.pc += m;
            cpu.branchOpcodeCycles(addr);
        }
    }

    /**
     * Branch iff P.z is CLEAR.
     * 
     * @param addr
     */
    private void BNE(short addr) {
        byte m = cpu.ram.read(addr);
        if (!cpu.p_Z()) {
            // GOTO
            cpu.pc += m;
            cpu.branchOpcodeCycles(addr);
        }
    }

    /**
     * Branch iff P.n is CLEAR.
     * 
     * @param addr
     */
    private void BPL(short addr) {
        if (!cpu.p_N()) {
            // FIXME ? P += M?
            cpu.pc = addr;
            cpu.branchOpcodeCycles(addr);
        }
    }

    /**
     * Simulate interrupt request.(IRQ)
     */
    private void BRK() {
       cpu.pc++;
       cpu.pushStack(ByteUtils.highByte(cpu.pc));
       cpu.pushStack(ByteUtils.lowByte(cpu.pc));
       cpu.pushStack((byte) (cpu.p | 0x10));
       
       byte low = cpu.ram.read((short) 0xfffe);
       // FIXME ? byte << 8 == 0?
       byte high = cpu.ram.read((short) 0xffff);
       high <<= 8;
       cpu.pc = ByteUtils.makeWord(high, low);
    }

    /**
     * Branch iff P.v is CLEAR.
     * 
     * @param addr
     */
    private void BVC(short addr) {
        byte m = cpu.ram.read(addr);
        if (!cpu.p_V()) {
            // GOTO
            cpu.pc += m;
        }
    }

    /**
     * Branch iff P.v is SET.
     * 
     * @param addr
     */
    private void BVS(short addr) {
        byte m = cpu.ram.read(addr);
        if (cpu.p_V()) {
            // GOTO
            cpu.pc += m;
            cpu.branchOpcodeCycles(addr);
        }
    }

    /**
     * Clear Carry flag.
     */
    private void CLC() {
        cpu.p_setC(false);
    }

    /**
     * Clear Decimal flag.
     */
    private void CLD() {
        cpu.p_setD(false);
    }

    /**
     * Clear Interrupt flag.
     */
    private void CLI() {
        cpu.p_setI(false);
    }

    /**
     * Clear Overflow flag.
     */
    private void CLV() {
        cpu.p_setV(false);
    }
    
    /**
     * Compare A with Memory
     * 
     * @param addr
     */
    private void CMP(short addr) {
        byte m = cpu.ram.read(addr);
        byte t = (byte) (cpu.a - m);
        cpu.p_setN(ByteUtils.isBitSet(t, 7));
        cpu.p_setC(cpu.a >= m);
        cpu.p_setZ(t == 0);
    }

    /**
     * Compare X register with Memory.
     * 
     * @param addr
     */
    private void CPX(short addr) {
        byte m = cpu.ram.read(addr);
        byte t = (byte) (cpu.x - m);
        cpu.p_setN(ByteUtils.isBitSet(t, 7));
        cpu.p_setC(cpu.x >= m);
        cpu.p_setZ(t == 0);
    }

    /**
     * Compare Y register with Memory.
     * 
     * @param addr
     */
    private void CPY(short addr) {
        byte m = cpu.ram.read(addr);
        byte t = (byte) (cpu.y - m);
        cpu.p_setN(ByteUtils.isBitSet(t, 7));
        cpu.p_setC(cpu.y >= m);
        cpu.p_setZ(t == 0);
    }

    /**
     * Decrement Memory by 1.
     * 
     * @param addr
     */
    private void DEC(short addr) {
        byte m = cpu.ram.read(addr);
        m = (byte) (m - 1);
        cpu.p_setN(ByteUtils.isBitSet(m, 7));
        cpu.p_setZ(m == 0);
    }

    /**
     * Decrement X register by 1.
     */
    private void DEX() {
        cpu.x = (byte) (cpu.x - 1);
        cpu.p_setZ(cpu.x == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.x, 7));
    }

    /**
     * Decrement Y register by 1.
     */
    private void DEY() {
        cpu.y = (byte) (cpu.y - 1);
        cpu.p_setZ(cpu.y == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.y, 7));
    }
    
    /**
     * Bitwise-Exclusive-OR A with Memory.
     * 
     * @param addr
     */
    private void EOR(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.a = (byte) (cpu.a ^ m);
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Increment Memory by 1.
     * 
     * @param addr
     */
    private void INC(short addr) {
        byte m = cpu.ram.read(addr);
        m = (byte) (m + 1);
        cpu.p_setN(ByteUtils.isBitSet(m, 7));
        cpu.p_setZ(m == 0);
        cpu.ram.write(addr, m);
    }

    /**
     * Increment X register by 1.
     */
    private void INX() {
        cpu.x = (byte) (cpu.x + 1);
        cpu.p_setZ(cpu.x == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.x, 7));
    }

    /**
     * Increment Y register by 1.
     */
    private void INY() {
        cpu.y = (byte) (cpu.y + 1);
        cpu.p_setZ(cpu.y == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.y, 7));
    }

    /**
     * GOTO address
     * 
     * @param addr
     */
    private void JMP(short addr) {
        cpu.pc = addr;
    }

    /**
     * Jump to SubRoutine.
     * 
     * @param addr
     */
    private void JSR(short addr) {
        short t = (short) (cpu.pc - 1);
        cpu.pushStack(ByteUtils.highByte(t));
        cpu.pushStack(ByteUtils.lowByte(t));
        // Jump to absolute address.
        cpu.pc = addr;
    }

    /**
     * Load A with Memory.
     * 
     * @param addr
     */
    private void LDA(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.a = m;
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Load X with Memory.
     * 
     * @param addr
     */
    private void LDX(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.x = m;
        cpu.p_setN(ByteUtils.isBitSet(cpu.x, 7));
        cpu.p_setZ(cpu.x == 0);    
    }

    /**
     * Load Y with Memory
     * 
     * @param addr
     */
    private void LDY(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.y = m;
        cpu.p_setN(ByteUtils.isBitSet(cpu.y, 7));
        cpu.p_setZ(cpu.y == 0);        
    }

    /**
     * Logical shift right.
     * 
     * @param addr
     */
    private void LSR(short addr) {
        byte m = cpu.ram.read(addr);
        cpu.p_setN(false);
        cpu.p_setC(ByteUtils.isBitSet(m, 0));
        m = (byte) (m >> 1);
        cpu.p_setZ(m == 0);
        cpu.ram.write(addr, m);
    }
    
    /**
     * Logical shift right with Accumulator.
     */
    private void LSRAcc() {
        cpu.p_setN(false);
        cpu.p_setC(ByteUtils.isBitSet(cpu.a, 0));
        cpu.a = (byte) (cpu.a >> 1);
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Nop
     */
    private void NOP() {
        // TODO consume the cycles.
    }

    /**
     * Bitwise-OR A with Memory.
     * 
     * @param addr
     */
    private void ORA (short addr) {
        byte m = cpu.ram.read(addr);
        cpu.a = (byte) (cpu.a | m);
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Push A onto Stack.
     */
    private void PHA() {
        cpu.pushStack(cpu.a);
    }

    /**
     * Push P onto Stack.
     */
    private void PHP() {
        cpu.pushStack(cpu.p);
    }

    /**
     * Pull from Stack to A.
     */
    private void PLA() {
        cpu.a = cpu.pullStack();
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Pull from Stack to P.
     */
    private void PLP() {
        cpu.p = cpu.pullStack();
    }

    /**
     * Rotate Left.
     * 
     * @param addr
     */
    private void ROL(short addr) {
        byte m = cpu.ram.read(addr);
        boolean bit7 = ByteUtils.isBitSet(m, 7);
        m = (byte) (m << 1);
        m = (byte) (m | (cpu.p_C()? 1: 0));
        cpu.p_setC(bit7);
        cpu.p_setZ(m == 0);
        cpu.p_setN(ByteUtils.isBitSet(m, 7));
        cpu.ram.write(addr, m);
    }
    
    /**
     * Rotate Left with Accumulator.
     */
    private void ROLAcc() {
        boolean bit7 = ByteUtils.isBitSet(cpu.a, 7);
        cpu.a = (byte) (cpu.a << 1);
        cpu.a = (byte) (cpu.a | (cpu.p_C()? 1: 0));
        cpu.p_setC(bit7);
        cpu.p_setZ(cpu.a == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
    }

    /**
     * Rotate Right.
     * 
     * @param addr
     */
    private void ROR(short addr) {
        byte m = cpu.ram.read(addr);
        boolean bit0 = ByteUtils.isBitSet(m, 0);
        m = (byte) (m >> 1);
        m = (byte) (m | (cpu.p_C()? 0x80: 0x00));
        cpu.p_setC(bit0);
        cpu.p_setZ(m == 0);
        cpu.p_setN(ByteUtils.isBitSet(m, 7));
        cpu.ram.write(addr, m);
    }
    
    /**
     * Rotate Right with Accumulator.
     */
    private void RORAcc() {
        boolean bit0 = ByteUtils.isBitSet(cpu.a, 7);
        cpu.a = (byte) (cpu.a >> 1);
        cpu.a = (byte) (cpu.a | (cpu.p_C()? 0x80: 0x00));
        cpu.p_setC(bit0);
        cpu.p_setZ(cpu.a == 0);
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));        
    }

    /**
     * Return from Interrupt.
     */
    private void RTI() {
        cpu.p = cpu.pullStack();
        byte l = cpu.pullStack();
        byte h = cpu.pullStack();
        cpu.pc = ByteUtils.makeWord(h, l);
    }

    /**
     * Return from SubRoutine.
     */
    private void RTS() {
        byte l = cpu.pullStack();
        byte h = cpu.pullStack();
        cpu.pc = ByteUtils.makeWord(h, l);
    }

    /**
     * Substract Memory from A with Borrow.
     * 
     * @param addr
     */
    private void SBC(short addr) {
        /* FIXME check decimal
         *
         * if (P.D) 
         *      t = bcd(A) - bcd(M) - !P.C
         *      P.V = (t > 99 or t < 0)? 1: 0
         * 
         */
        byte m = cpu.ram.read(addr);
        byte t = (byte) (cpu.a - m - (cpu.p_C()? 0: 1));
        cpu.p_setV(t > 127 || t < -128);
        cpu.p_setC(t >= 0);
        cpu.p_setN(ByteUtils.isBitSet(t, 7));
        cpu.p_setZ(t == 0);
        cpu.a = t;
        
    }

    /**
     * Set Carry flag.
     */
    private void SEC() {
        cpu.p_setC(true);
    }

    /**
     * Set BCD flag.
     */
    private void SED() {
        cpu.p_setD(true);
    }

    /**
     * Set Interrupt flag.
     */
    private void SEI() {
        cpu.p_setI(true);
    }

    /**
     * Store A in Memory.
     * 
     * @param addr
     */
    private void STA(short addr) {
        cpu.ram.write(addr, cpu.a);
    }

    /**
     * Store X in Memory.
     * 
     * @param addr
     */
    private void STX(short addr) {
        cpu.ram.write(addr, cpu.x);;
    }

    /**
     * Store Y in Memory.
     * 
     * @param addr
     */
    private void STY(short addr) {
        cpu.ram.write(addr, cpu.y);
    }

    /**
     * Transfer A to X.
     */
    private void TAX() {
        cpu.x = cpu.a;
        cpu.p_setN(ByteUtils.isBitSet(cpu.x, 7));
        cpu.p_setZ(cpu.x == 0);
    }

    /**
     * Transfer A to Y.
     */
    private void TAY() {
        cpu.y = cpu.a;
        cpu.p_setN(ByteUtils.isBitSet(cpu.y, 7));
        cpu.p_setZ(cpu.y == 0);
    }

    /**
     * Transfer StackPointer to X.
     */
    private void TSX() {
        cpu.x = cpu.sp;
        cpu.p_setN(ByteUtils.isBitSet(cpu.x, 7));
        cpu.p_setZ(cpu.x == 0);       
    }

    /**
     * Transfer X to A.
     */
    private void TXA() {
        cpu.a = cpu.x;
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }

    /**
     * Transfer X to StackPointer.
     */
    private void TXS() {
        cpu.sp = cpu.x;
    }

    /**
     * Transfer Y to A.
     */
    private void TYA() {
        cpu.a = cpu.y;
        cpu.p_setN(ByteUtils.isBitSet(cpu.a, 7));
        cpu.p_setZ(cpu.a == 0);
    }
}
