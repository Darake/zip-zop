package zipzop.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ByteInputStream tests")
public class ByteInputStreamTest {
    
    private ByteInputStream stream;
    
    @BeforeEach
    public void setUp() throws FileNotFoundException {
        String path = getClass().getClassLoader().getResource("testfile").getPath();
        stream = new ByteInputStream(path);
    }
    
    @Test
    @DisplayName("nextByte returns right byte")
    public void nextByteReturnsRightByte() throws IOException {
        String result = "" + (char) stream.nextByte() + (char) stream.nextByte();
        
        assertEquals("he", result);
    }
    
    @Test
    @DisplayName("close termintates stream")
    public void closeTerminatesStream() throws IOException {
        stream.close();
        assertThrows(IOException.class, () -> {
            stream.nextByte();
        });
    }
}
