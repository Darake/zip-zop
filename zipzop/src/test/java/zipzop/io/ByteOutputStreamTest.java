package zipzop.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

public class ByteOutputStreamTest {

    private ByteOutputStream stream;
    private File tempFolder;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void setUp() throws IOException {
        tempFolder = testFolder.newFolder("folder");
        stream = new ByteOutputStream(tempFolder.getAbsolutePath() + "/testoutput");
    }
    
    @Test
    public void writeByteWorks() throws IOException {
        stream.writeByte((byte) 'k');
        var inputStream = new FileInputStream(tempFolder.getAbsolutePath() + "/testoutput");
        
        assertEquals('k', (char) inputStream.read());
        
        inputStream.close();
    }
    
    @Test
    public void writeByteArrayWorks() throws IOException {
        byte[] bArray = "hello".getBytes();
        
        stream.writeByteArray(bArray);
        
        var inputStream = new FileInputStream(tempFolder.getAbsolutePath() + "/testoutput");
        var string = new String(inputStream.readAllBytes());
        
        assertEquals("hello", string);
        
        inputStream.close();
    }
    
    @Test(expected = IOException.class)
    public void closeTerminatesStream() throws IOException {
        stream.close();
        stream.writeByte(0);
    }
}
