package zipzop.io;

import zipzop.io.FileManagement;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("FileManagement tests")
public class FileManagementTest { 
    
    @Test
    @DisplayName("readFileToByteArray reads the expected bytes to array")
    public void readFileToByteArrayWorks() throws IOException {
        var fileManagement = new FileManagement();
        String path = getClass().getClassLoader().getResource("testfile").getPath();
        var byteArray = fileManagement.readFileToByteArray(path);
        
        assertEquals("hello", new String(byteArray));
    }
    
}
