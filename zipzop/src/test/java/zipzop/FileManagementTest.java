package zipzop;

import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class FileManagementTest { 
      
    @Before
    public void setUp() {
    }
    
    @Test
    public void readFileToByteArrayWorks() throws IOException {
        var fileManagement = new FileManagement();
        var classloader = getClass().getClassLoader();
        var path = classloader.getResource("testfile").getPath();
        var byteArray = fileManagement.readFileToByteArray(path);
        var content = "";
        for (byte b : byteArray) {
            content += (char) b;
        }
        assertEquals(true, content.contains("hello"));
    }
    
}
