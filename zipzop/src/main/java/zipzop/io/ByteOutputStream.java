package zipzop.io;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Used to write bytes into a file.
 */
public class ByteOutputStream {
    
    private FileOutputStream stream;
    
    public ByteOutputStream(String path) {
        try {
            stream = new FileOutputStream(path);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Writes a byte into a file.
     * @param b Byte to be written,
     * @throws IOException 
     */
    public void writeByte(int b) {
        try {
            stream.write(b);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Write a byte array into a file.
     * @param bArray Byte array to be written into a file.
     * @throws IOException 
     */
    public void writeByteArray(byte[] bArray) {
        try {
            stream.write(bArray);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * Closes the FileOutputStream.
     * @throws IOException 
     */
    public void close() {
        try {
            stream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
