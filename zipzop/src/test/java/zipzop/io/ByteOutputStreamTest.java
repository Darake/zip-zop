package zipzop.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("ByteOutputStream tests")
public class ByteOutputStreamTest {

    private ByteOutputStream stream;
    private String file;
    
    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws IOException {
        file = tempDir.resolve("output").toString();
        stream = new ByteOutputStream(file);
    }
    
    @Test
    @DisplayName("writeByte writes a correct byte to file")
    public void writeByteWorks() throws IOException {
        stream.writeByte('k');
        var inputStream = new FileInputStream(file);
        
        assertEquals('k', inputStream.read());
        
        inputStream.close();
    }
    
    @Test
    @DisplayName("writeByteArray writes correct bytes to file")
    public void writeByteArrayWorks() throws IOException {
        byte[] bArray = "hello".getBytes();
        
        stream.writeByteArray(bArray);
        
        var inputStream = new FileInputStream(file);
        var string = new String(inputStream.readAllBytes());
        
        assertEquals("hello", string);
        
        inputStream.close();
    }
    
    @Test
    @DisplayName("close terminates stream")
    public void closeTerminatesStream() throws IOException {
        stream.close();
        assertThrows(IOException.class, () -> {
            stream.writeByte(0);
        });
    }
}
