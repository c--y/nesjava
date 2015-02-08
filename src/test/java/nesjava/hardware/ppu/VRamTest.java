package nesjava.hardware.ppu;

import org.testng.annotations.Test;

public class VRamTest {

  @Test
  public void dumpTile() {
      byte[] tile = {
              0x10, 0x00, 0x44, 0x00, (byte) 0xfe, 0x00, (byte) 0x82, 0x00,
              0x00, 0x28, 0x44, (byte) 0x82, 0x00, (byte) 0x82, (byte) 0x82, 0x00
      };
      
      System.out.println(VRam.dumpTile(tile));
      
  }
}
