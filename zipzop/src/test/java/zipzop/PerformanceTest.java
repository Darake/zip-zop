package zipzop;

import java.io.File;
import java.nio.file.Path;
import java.text.DecimalFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import zipzop.huffman.Huffman;

@Tag("performance")
@DisplayName("Perofrmance tests")
public class PerformanceTest {

  private Huffman huffman;

  @TempDir
  static Path tempDir;

  @BeforeEach
  public void setUp() {
    huffman = new Huffman();
  }

  @Nested
  @DisplayName("The Canterbury Corpus tests")
  class Canterbury {
    
    private long compress(String input, String output) {
      long timeAtStart = System.currentTimeMillis();
      huffman.compress(input, output);
      long timeAtEnd = System.currentTimeMillis();
      return timeAtEnd - timeAtStart;
    }
    
    private long decompress(String input) {
      String output = tempDir.resolve("decompressed").toString();
      long timeAtStart = System.currentTimeMillis();
      huffman.decompress(input, output);
      long timeAtEnd = System.currentTimeMillis();
      return timeAtEnd - timeAtStart;
    }
    
    private void printResult(String fileName, long fileSize, long compressedFileSize,
            long compressionTime, long decompressionTime) {
      double percentage = (double) (1.0 * compressedFileSize / fileSize) * 100;
      DecimalFormat df = new DecimalFormat("#.##");

      System.out.println(fileName);
      System.out.println("File size: " + fileSize + " bytes");
      System.out.println("Compressed file size: " + compressedFileSize + " bytes");
      System.out.println("Compression percentage: " + df.format(percentage) + "%");
      System.out.println("Compression time: " + compressionTime + " ms");
      System.out.println("Decompression time: " + decompressionTime + " ms\n");
    }
    
    public void run(String fileName) {
      String file = getClass()
              .getClassLoader()
              .getResource("performance/canterbury/" + fileName)
              .getPath();

      long compressionTime = 0;
      long decompressionTime = 0;
      long compressedFileSize = 0;

      int n = 10;
      for (int i = 0; i < n; i++) {
        String compressedFilePath = tempDir.resolve("output" + fileName + i).toString();
        
        compressionTime += compress(file, compressedFilePath);

        decompressionTime += decompress(compressedFilePath);

        compressedFileSize += new File(compressedFilePath).length();
      }

      compressionTime /= n;
      decompressionTime /= n;
      compressedFileSize /= n;

      long fileSize = new File(file).length();
      
      printResult(fileName, fileSize, compressedFileSize, compressionTime, decompressionTime);
    }

    @Test
    public void testCanterburyCorpus() {
      run("alice29.txt");
      run("asyoulik.txt");
      run("cp.html");
      run("fields.c");
      run("grammar.lsp");
      run("kennedy.xls");
      run("lcet10.txt");
      run("plrabn12.txt");
      run("ptt5");
      run("sum");
      run("xargs.1");
    }
  }
}
