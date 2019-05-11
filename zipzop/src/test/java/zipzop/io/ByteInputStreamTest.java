package zipzop.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ByteInputStreamTest {
    
    private ByteInputStream stream;
    
    @Before
    public void setUp() throws FileNotFoundException {
        var classloader = getClass().getClassLoader();
        var path = classloader.getResource("testfile").getPath();
        stream = new ByteInputStream(path);
    }
    
    @Test
    public void nextByteReturnsRightByte() throws IOException {
        assertEquals('h', stream.nextChar());
        assertEquals('e', stream.nextChar());
    }
    
    @Test(expected = IOException.class)
    public void closeTerminatesStream() throws IOException {
        stream.close();
        stream.nextChar();
    }
}
