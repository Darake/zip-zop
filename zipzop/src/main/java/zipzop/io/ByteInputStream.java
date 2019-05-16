package zipzop.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Used to read bytes from a file one byte at a time.
 */
public class ByteInputStream {
    
    private FileInputStream stream;
    
    /**
     * Creates a new FileInputStream.
     * @param path A path to the file wanted to be read.
     * @throws FileNotFoundException 
     */
    public ByteInputStream(String path) throws FileNotFoundException {
        this.stream = new FileInputStream(path);
    }
    
    /**
     * Reads next byte as int from file.
     * @return int Returns read byte as int.
     * @throws IOException 
     */
    public int nextByte() throws IOException {
        return stream.read();
    }
    
    /**
     * Closes the FileInputStream.
     * @throws IOException 
     */
    public void close() throws IOException {
        stream.close();
    }
}
