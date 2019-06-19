package zipzop.huffman;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

@DisplayName("Huffman tests")
public class HuffmanTest {

  private Huffman huffman;

  @BeforeEach
  public void setUp() {
    huffman = new Huffman();
  }

  @Nested
  @DisplayName("compress tests")
  class CompressTests {

    private String input;
    private String output;
    private byte[] compressedFile;

    @BeforeEach
    public void setUp(@TempDir Path tempDir) throws IOException {
      output = tempDir.resolve("output").toString();
    }

    @Nested
    @DisplayName("compress tests first file")
    class CompressTestsFirstFile {

      @BeforeEach
      public void setUp() throws IOException {
        input = getClass().getClassLoader().getResource("compressionFile").getPath();
        huffman.compress(input, output);
        compressedFile = Files.readAllBytes(Paths.get(output));
      }

      @Test
      @DisplayName("4 byte integer for uncompressed file size is correct")
      public void compressedFileUncompressedByteSizeCorrectInHeader() {
        byte[] byteSize = Arrays.copyOfRange(compressedFile, 0, 4);
        byte[] expected = {0, 0, 0, 7};

        assertArrayEquals(expected, byteSize);
      }

      @Test
      @DisplayName("the header's topology is correct")
      public void compressedFileTopologyCorrect() {
        byte[] topology = Arrays.copyOfRange(compressedFile, 4, 8);
        byte[] expected = {-73, -40, -84, 96};

        assertArrayEquals(expected, topology);
      }

      @Test
      @DisplayName("the encoded data is correct")
      public void compressedFileDataCorrect() {
        byte[] encodedData = Arrays.copyOfRange(compressedFile, 8, 10);
        byte[] expected = {-46, 0};

        assertArrayEquals(expected, encodedData);
      }
    }
    
    @Nested
    @DisplayName("compress tests second file")
    class CompressTestsSecondFile {

      @BeforeEach
      public void setUp() throws IOException {
        input = getClass().getClassLoader().getResource("testfile").getPath();
        huffman.compress(input, output);
        compressedFile = Files.readAllBytes(Paths.get(output));
      }

      @Test
      @DisplayName("4 byte integer for uncompressed file size is correct")
      public void compressedFileUncompressedByteSizeCorrectInHeader() {
        byte[] byteSize = Arrays.copyOfRange(compressedFile, 0, 4);
        byte[] expected = {0, 0, 0, 5};

        assertArrayEquals(expected, byteSize);
      }

      @Test
      @DisplayName("the header's topology is correct")
      public void compressedFileTopologyCorrect() {
        byte[] topology = Arrays.copyOfRange(compressedFile, 4, 9);
        byte[] expected = {-74, 90, 22, -5, 40};

        assertArrayEquals(expected, topology);
      }

      @Test
      @DisplayName("the encoded data is correct")
      public void compressedFileDataCorrect() {
        byte[] encodedData = Arrays.copyOfRange(compressedFile, 9, 11);
        byte[] expected = {112, -128};

        assertArrayEquals(expected, encodedData);
      }
    }

  }

  @Test
  @DisplayName("decompress works as expected")
  public void decompressWorks(@TempDir Path tempDir) throws FileNotFoundException, IOException {
    String input = getClass().getClassLoader().getResource("compressedFile").getPath();
    String output = tempDir.resolve("output").toString();
    huffman.decompress(input, output);
    var stream = new FileInputStream(output);
    byte[] data = stream.readAllBytes();

    assertEquals("coboboo", new String(data));
  }
}
