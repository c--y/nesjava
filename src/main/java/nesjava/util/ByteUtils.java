/**
 * 
 */
package nesjava.util;

/**
 * @author chenyan
 *
 */
public class ByteUtils {

    public static byte highByte(short word) {
        return (byte) (0xff & (word >> 8));
    }
    
    public static byte lowByte(short word) {
        return (byte) (0xff & word);
    }
    
    public static short makeWord(byte high, byte low) {
        short ret = high;
        ret <<= 8;
        return (short) (ret | low);
    }
    
    public static boolean isBitSet(short word, int index) {
        return ((1 << index) & word) > 0 ?true: false;
    }
    
    public static boolean isBitSet(byte b, int index) {
        return ((1 << index) & b) > 0? true: false;
    }
}
