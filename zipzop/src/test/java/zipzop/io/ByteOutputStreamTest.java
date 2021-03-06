package zipzop.io;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("ByteOutputStream tests")
public class ByteOutputStreamTest {

  private ByteOutputStream stream;
  private String file;

  @BeforeEach
  public void setUp(@TempDir Path tempDir) {
    file = tempDir.resolve("output").toString();
    stream = new ByteOutputStream(file);
  }

  @Test
  @DisplayName("writeByte writes a correct byte to file")
  public void writeByteWorks() throws IOException {
    stream.writeByte('k');
    var inputStream = new FileInputStream(file);

    assertEquals('k', inputStream.read());

    inputStream.close();
  }

  @Test
  @DisplayName("writeByteArray writes correct bytes to file")
  public void writeByteArrayWorks() throws IOException {
    byte[] byteArray = "hello".getBytes();

    stream.writeByteArray(byteArray);

    var inputStream = new FileInputStream(file);
    var string = new String(inputStream.readAllBytes());

    assertEquals("hello", string);

    inputStream.close();
  }

  @Test
  @DisplayName("close terminates stream")
  public void closeTerminatesStream() throws IOException {
    stream.close();
    stream.writeByte(0);
    var inputStream = new FileInputStream(file);

    assertEquals(-1, inputStream.read());
  }
}
