package zipzop.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Used to write bytes into a file.
 */
public class ByteOutputStream {
    
    private FileOutputStream stream;
    
    public ByteOutputStream(String path) throws FileNotFoundException {
        stream = new FileOutputStream(path);
    }
    
    /**
     * Writes a byte into a file.
     * @param b Byte to be written,
     * @throws IOException 
     */
    public void writeByte(int b) throws IOException {
        stream.write(b);
    }
    
    /**
     * Write a byte array into a file.
     * @param bArray Byte array to be written into a file.
     * @throws IOException 
     */
    public void writeByteArray(byte[] bArray) throws IOException {
        stream.write(bArray);
    }
    
    /**
     * Closes the FileOutputStream.
     * @throws IOException 
     */
    public void close() throws IOException {
        stream.close();
    }
}
