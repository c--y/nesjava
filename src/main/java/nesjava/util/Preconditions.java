/**
 * 
 */
package nesjava.util;

/**
 * 
 * 
 * @author chenyan06
 *
 */
public class Preconditions {

    /**
     * Ensure the true of expression.
     * 
     * @param expression
     */
    public static final void checkArgument(boolean expression) {
        if (!expression) {
            throw new IllegalArgumentException();
        }
    }
    
    /**
     * Ensure the true of expression, print the message if not.
     * 
     * @param expression
     * @param msg
     * @param objs
     */
    public static final void checkArgument(boolean expression, String msg, Object...objs) {
        if (!expression) {
            throw new IllegalArgumentException(String.format(msg, objs));
        }
    }
}
