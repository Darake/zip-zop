package zipzop.util;

/** Helper class used for different kinds of byte conversions. */
public class ByteConversion {
  
  /**
   * Converts an int into a binary string.
   * 
   * @param b int or byte as int to be converted
   * @return Returns a byte in a binary string format
   */
  public String byteAsString(int b) {
    return String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
  }
  
  /**
   * Converts a String into a byte. An integer is parsed to avoid "Value out of range" error.
   * 
   * @param string A byte in binary string representation
   * @return Returns the byte that represents the binary string
   */
  public byte stringAsByte(String string) {
    return (byte) Integer.parseInt(string, 2);
  }
  
  /**
   * Converts an int into a byte array with a size of 4 using bit shifting.
   *
   * @param number int to be converted into 4 byte array
   * @return byte array of the int with a size of 4
   */
  public byte[] intInFourBytes(int number) {
    return new byte[]{
      (byte) (number >>> 24),
      (byte) (number >>> 16),
      (byte) (number >>> 8),
      (byte) (number)
    };
  }
}
