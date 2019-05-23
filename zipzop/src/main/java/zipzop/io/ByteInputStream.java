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
     */
    public ByteInputStream(String path) {
        try {
            this.stream = new FileInputStream(path);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Reads next byte as int from file.
     * @return int Returns read byte as int.
     */
    public int nextByte() {
        try {
            return stream.read();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }
    
    /**
     * Closes the FileInputStream.
     */
    public void close() {
        try {
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
