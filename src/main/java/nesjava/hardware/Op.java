/**
 * 
 */
package nesjava.hardware;

/**
 * @author chenyan
 *
 */
public abstract class Op {
    
    byte opcode;

    boolean mayCross;
    
    boolean mayBranch;
    
    byte length;
    
    byte cycles;
    
    abstract void execute(CPU cpu);
}
