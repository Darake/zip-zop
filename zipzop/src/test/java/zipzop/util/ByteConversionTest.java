package zipzop.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ByteConversion tests")
public class ByteConversionTest {
  
  private ByteConversion converter;
  
  @BeforeEach
  public void setUp() {
    this.converter = new ByteConversion();
  }
  
  @Test
  @DisplayName("byteAsString returns a correct byte string")
  public void byteAsStringReturnsCorrectValue() {
    assertEquals("11001000", converter.byteAsString(200));
  }
  
  @Test
  @DisplayName("stringAsByte returns a correct byte")
  public void stringAsByteReturnsCorrectValue() {
    assertEquals((byte) 123, converter.stringAsByte("01111011"));
  }
  
  @Test
  @DisplayName("intInFourBytes turns int into four bytes")
  public void intInFourBytesTurnsIntIntoFourBytes() {
    assertArrayEquals(new byte[]{0, 0, 5, 57}, converter.intInFourBytes(1337));
  }
}
