package zipzop.huffman;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import zipzop.io.ByteInputStream;
import zipzop.util.ByteConversion;

@DisplayName("HuffmanTree tests")
public class HuffmanTreeTest {

  private HuffmanTree tree;
  private TreeNode root;
  private ByteInputStream stream;

  @BeforeEach
  public void setUp() {
    tree = new HuffmanTree(new ByteConversion());
  }

  @Nested
  @DisplayName("buildTreeFromUncompressedFile tests")
  class BuildTreeFromUncompressedFileTest {

    @Nested
    @DisplayName("first file")
    class BuildTreeFromUncompressedFileFirstFileTests {

      @BeforeEach
      public void setUp() {
        String path = getClass().getClassLoader().getResource("compressionFile").getPath();
        stream = new ByteInputStream(path);
        root = tree.buildTreeFromUncompressedFile(stream);
      }

      @Test
      @DisplayName("tree leaves in right position")
      public void leavesInRightPosition() {
        String result = "" + root.getLeftChild().getData()
                + root.getRightChild().getLeftChild().getData()
                + root.getRightChild().getRightChild().getData();

        assertEquals("obc", result);
      }

      @Test
      @DisplayName("tree nodes have excpected weights")
      public void nodesHaveExcpectedWeights() {
        int[] result = {root.getWeight(), root.getLeftChild().getWeight(),
          root.getRightChild().getWeight(), root.getRightChild().getLeftChild().getWeight(),
          root.getRightChild().getRightChild().getWeight()};
        int[] expected = {7, 4, 3, 2, 1};

        assertArrayEquals(expected, result);
      }
    }
    
    @Nested
    @DisplayName("second file")
    class BuildTreeFromUncompressedFileSecondFileTests {
      
      @BeforeEach
      public void setUp() {
        String path = getClass().getClassLoader().getResource("testfile").getPath();
        stream = new ByteInputStream(path);
        root = tree.buildTreeFromUncompressedFile(stream);
      }
      
      @Test
      @DisplayName("tree leaves in right position")
      public void leavesInRightPosition() {
        String result = "" + root.getLeftChild().getLeftChild().getData()
                + root.getLeftChild().getRightChild().getData()
                + root.getRightChild().getLeftChild().getData()
                + root.getRightChild().getRightChild().getData();

        assertEquals("lhoe", result);
      }

      @Test
      @DisplayName("tree nodes have excpected weights")
      public void nodesHaveExcpectedWeights() {
        int[] result = {root.getWeight(), root.getLeftChild().getWeight(),
          root.getRightChild().getWeight(), root.getRightChild().getLeftChild().getWeight(),
          root.getRightChild().getRightChild().getWeight(),
          root.getLeftChild().getLeftChild().getWeight(),
          root.getLeftChild().getRightChild().getWeight()};
        int[] expected = {5, 3, 2, 1, 1, 2, 1};

        assertArrayEquals(expected, result);
      }
    }

  }

  @Test
  @DisplayName("buildTreeFromCompressedFile leaves are in right position with first file")
  public void buildTreeFromCompressedFileTreeLeavesInRightPositionFirstFile() {
    String path = getClass().getClassLoader().getResource("compressedFile").getPath();
    stream = new ByteInputStream(path);
    for (int i = 0; i < 4; i++) {
      stream.nextByte();
    }
    root = tree.buildTreeFromCompressedFile(stream);
    String result = "" + root.getLeftChild().getData()
            + root.getRightChild().getLeftChild().getData()
            + root.getRightChild().getRightChild().getData();

    assertEquals("obc", result);
  }
  
  @Test
  @DisplayName("buildTreeFromCompressedFile leaves are in right position with second file")
  public void buildTreeFromCompressedFileTreeLeavesInRightPositionSecondFile() {
    String path = getClass().getClassLoader().getResource("compressedTestFile").getPath();
    stream = new ByteInputStream(path);
    for (int i = 0; i < 4; i++) {
      stream.nextByte();
    }
    root = tree.buildTreeFromCompressedFile(stream);
    String result = "" + root.getLeftChild().getLeftChild().getData()
            + root.getLeftChild().getRightChild().getData()
            + root.getRightChild().getLeftChild().getData()
            + root.getRightChild().getRightChild().getData();

    assertEquals("lhoe", result);
  }

  @AfterEach
  public void clean() {
    stream.close();
  }
}
