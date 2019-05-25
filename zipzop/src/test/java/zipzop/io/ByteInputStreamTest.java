package zipzop.io;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ByteInputStream tests")
public class ByteInputStreamTest {
    
    private ByteInputStream stream;
    
    @BeforeEach
    public void setUp() {
        String path = getClass().getClassLoader().getResource("testfile").getPath();
        stream = new ByteInputStream(path);
    }
    
    @Test
    @DisplayName("nextByte returns right byte")
    public void nextByteReturnsRightByte() {
        String result = "" + (char) stream.nextByte() + (char) stream.nextByte();
        
        assertEquals("he", result);
    }
    
    @Test
    @DisplayName("nextDoubleWord returns a right int")
    public void nextDoubleWordReturnsRightInt() {
        assertEquals(1751477356, stream.nextDoubleWord());
    }
    
    @Test
    @DisplayName("close termintates stream")
    public void closeTerminatesStream() {
        stream.close();
        
        assertEquals(-1, stream.nextByte());
    }
}
