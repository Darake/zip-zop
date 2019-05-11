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
     * Reads next byte as char from file.
     * @return byte Returns read byte.
     * @throws IOException 
     */
    public char nextChar() throws IOException {
        System.out.println((char) 65535);
        return (char) stream.read();
    }
    
    /**
     * Closes the FileInputStream.
     * @throws IOException 
     */
    public void close() throws IOException {
        stream.close();
    }
}
