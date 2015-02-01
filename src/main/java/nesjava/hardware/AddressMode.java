/**
 * 
 */
package nesjava.hardware;

/**
 * 内存寻址模式
 * 
 * 15种.
 * 
 * @author chenyan
 *
 */
public enum AddressMode {

    Accumulator,
    
    Implied,
    
    Immediate,
    
    Absolute,
    
    AbsoluteX,
    
    AbsoluteY,
    
    Indirect,
    
    Relative,
    
    IndexedIndirect,
    
    IndirectIndexed,
    
    ZeroPage,
    
    ZeroPageX,
    
    ZeroPageY
}
