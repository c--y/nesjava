package nesjava.util;

import org.testng.annotations.Test;

public class ByteUtilsTest {

    @Test
    public void highByte() {
        short i = (short) 0xaad6;
        System.out.println(Integer.toBinaryString(i));
        System.out.println(Integer.toBinaryString(ByteUtils.highByte(i)));
    }

    @Test
    public void lowByte() {
        short i = (short) 0xaad6;
        System.out.println(Integer.toBinaryString(ByteUtils.lowByte(i)));   
        
        short a = 32222;
        short b = 32495;
        short c = (short) (a + b);
        System.out.println(Integer.toBinaryString(c & 0xFFFF));
        
        a += b;
        System.out.println(Integer.toBinaryString(a & 0xFFFF));
    }
}
