package zipzop.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class handles the I/O and conversion of files.
 */
public class FileManagement {
    
    /**
     * Reads file as bytes into a byte array.
     * @param pathAsString Path to the file to be read as a String.
     * @return Byte array created from file
     * @throws IOException 
     */
    public byte[] readFileToByteArray(String pathAsString) throws IOException {
        var path = Paths.get(pathAsString);
        var byteArray = Files.readAllBytes(path);
        return byteArray;
    }
}
