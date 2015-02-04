/**
 * 
 */
package nesjava.hardware;

/**
 * Picture Processing Unit
 * 
 * @author chenyan
 *
 */
public class PPU {
    
    /*
     * Registers
     */
    
    /**
     * Controller(0x2000) write
     */
    byte control;
    
    /**
     * Mask(0x2001) write
     */
    byte mask;
    
    /**
     * Status(0x2002) read
     */
    byte status;
    
    /**
     * Oma Address(0x2003)
     */
    byte omaAddress;
    
    /**
     * Oma Data(0x2004)
     */
    byte omaData;
    
    /**
     * Scroll(0x2005)
     */
    byte scroll;
    
    /**
     * Address(0x2006)
     */
    byte address;
    
    /**
     * Data(0x2007)
     */
    byte data;

    public void stepRun() {        
    }

}
