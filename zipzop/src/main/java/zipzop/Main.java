package zipzop;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Main class of the Zip-Zop application, which is a used for compressing and
 * decompressing files.
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        byte[] compressedFile = {0, 0, 0, 9, 0, 0, 0, 7, 1, 111, 1, 98, 1, 99, 0, 0, 0, -46, 0};
        var outputStream = new FileOutputStream("compressedFile");
        
        outputStream.write(compressedFile);
    }

}
