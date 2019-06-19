package zipzop.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Used to read bytes from a file one byte at a time.
 */
public class ByteInputStream {

  private String path;
  private FileInputStream stream;

  /**
   * Creates a new FileInputStream.
   *
   * @param path A path to the file wanted to be read.
   */
  public ByteInputStream(String path) {
    try {
      this.path = path;
      this.stream = new FileInputStream(path);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Reads next byte as int from file.
   *
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
   * Reads the next four bytes and converts them into an int.
   *
   * @return Returns an int representation of four bytes read
   */
  public int nextDoubleWord() {
    try {
      var dword = new byte[4];
      stream.read(dword);
      return ByteBuffer.wrap(dword).getInt();
    } catch (IOException ex) {
      ex.printStackTrace();
      return -1;
    }
  }

  /**
   * File's length.
   *
   * @return Size of the file being streamed in bytes as int
   */
  public int length() {
    return (int) new File(path).length();
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
