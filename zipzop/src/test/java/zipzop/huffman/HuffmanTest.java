package zipzop.huffman;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.PriorityQueue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import zipzop.io.ByteInputStream;
import zipzop.io.ByteOutputStream;

@DisplayName("Huffman tests")
public class HuffmanTest {
    
    private Huffman huffman;
    
    @BeforeEach
    public void setUp() {
        huffman = new Huffman();
    }
       
    @Test
    @DisplayName("getOccurrencesFromStream returns a correct array")
    public void getOccurrencesFromStreamReturnsCorrectArray() {
        String path = getClass()
                .getClassLoader()
                .getResource("testfile")
                .getPath();
        var stream = new ByteInputStream(path);
        int[] expected = new int[256];
        expected['l' & 0xFF] = 2;
        expected['h' & 0xFF] = 1;
        expected['e' & 0xFF] = 1;
        expected['o' & 0xFF] = 1;
        int[] occurrences = huffman.getOccurrencesFromStream(stream);
            
        assertArrayEquals(expected, occurrences);
    }
    
    @Test
    @DisplayName("getHuffManTreeForest returns a queue with nodes in right order")
    public void getHuffManTreeForestReturnsExpectedQueue() {
        int[] occurrences = new int[256];
        occurrences['h' & 0xFF] = 5;
        occurrences['e' & 0xFF] = 2;
        occurrences['l' & 0xFF] = 1;
        occurrences['o' & 0xFF] = 3;
        PriorityQueue<TreeNode> treeForest = huffman.getHuffmanTreeForest(occurrences);
        String result = "";
        while(treeForest.peek() != null) {
            result += treeForest.poll().getData();
        }
        
        assertEquals("leoh", result);
    }
    
    @Test
    @DisplayName("getHuffmanTreeRoot creates a tree with leaves in the right positions")
    public void getHuffmanTreeRootLeavesInCorrectPositions() {
        var treeForest = new PriorityQueue<TreeNode>();
        treeForest.add(new TreeNode(2, 'h'));
        treeForest.add(new TreeNode(5, 'l'));
        treeForest.add(new TreeNode(1, 's'));
        treeForest.add(new TreeNode(9, 'k'));
        TreeNode treeRoot = huffman.getHuffmanTreeRoot(treeForest);
        String result = "" + treeRoot.getLeftChild().getData()
                + treeRoot.getRightChild().getLeftChild().getData()
                + treeRoot.getRightChild().getRightChild().getLeftChild().getData()
                + treeRoot.getRightChild().getRightChild().getRightChild().getData();
        
        assertEquals("klhs", result);
    }
    
    @Nested
    @DisplayName("Tests where Huffman's tree is needed")
    class HuffmanTreeNeeded {
        
        private TreeNode root;
        
        @BeforeEach
        public void setUp() {
            var leftGrandChild = new TreeNode(5, 'l');
            var rightGrandChild = new TreeNode(2, 'h');
            var leftChild = new TreeNode(9, 'k');
            var rightChild = new TreeNode(7, leftGrandChild, rightGrandChild);
            root = new TreeNode(16, leftChild, rightChild);
        }
        
        @Test
        @DisplayName("createBitEncodingTable inserts correct values to the map")
        public void createBitEncodingTableReturnsTableWithRightCodes() {
            String code = "";
            var encodingTable = new String[256];
            huffman.createBitEncodingTable(encodingTable, code, root);
            String[] expected = new String[256];
            expected['k' & 0xFF] = "0";
            expected['l' & 0xFF] = "10";
            expected['h' & 0xFF] = "11";
        
            assertArrayEquals(expected, encodingTable);
        }
        
        @Test
        @DisplayName("createTopology inserts correct bytes into byte array")
        public void createTopologyCreatesExpectedByteArray() {
            byte[] topology = new byte[9];
            huffman.createTopology(topology, 0, root);
            byte[] expected =  {1, 'k', 1, 'l', 1, 'h', 0, 0, 0};
        
            assertArrayEquals(expected, topology);
        }
        
        @Nested
        @DisplayName("decode tests")
        class DecodeTests {
            
            private StringBuilder encodedData;
        
            @BeforeEach
            public void setUp() {
                encodedData = new StringBuilder("101");
            }
        
            @Test
            @DisplayName("decode returns a correct decoded byte")
            public void decodeReturnsCorrectByte() {
                assertEquals('l', huffman.decode(encodedData, root));
            }
        
            @Test
            @DisplayName("decode changes encodedData String correctly")
            public void decodeChangesEncodedData() {
                huffman.decode(encodedData, root);
                
                assertEquals("1", encodedData.toString());
            }
        }
    }
    
    @Nested
    @DisplayName("Huffman compression tests with reading and writing")
    class ReadWriteTests {
        
        private String input;
        private String output;
        
        @BeforeEach
        public void setUp(@TempDir Path tempDir) {
            input = getClass().getClassLoader().getResource("compressionFile").getPath();
            output = tempDir.resolve("output").toString();
        }
        
        @Test
        @DisplayName("encodeData writes correct bytes into output file")
        public void encodeDataWritesExpectedBytes(@TempDir Path tempDir) throws IOException {
            var inputStream = new ByteInputStream(input);
            var outputStream = new ByteOutputStream(output);
            String[] encodingTable = new String[256];
            encodingTable['o' & 0xFF] = "0";
            encodingTable['b' & 0xFF] = "10";
            encodingTable['c' & 0xFF] = "11";
            huffman.encodeData(encodingTable, inputStream, outputStream);
            var byteArray = Files.readAllBytes(Paths.get(output));
            byte[] expected = {-46, 0};
        
            assertArrayEquals(expected, byteArray);
        }
        
        @Nested
        @DisplayName("compress")
        class CompressTests {
            
            private byte[] compressedFile;
            
            @BeforeEach
            public void setUp() throws IOException {
                Path outputPath = Paths.get(output);
                huffman.compress(input, output);
                compressedFile = Files.readAllBytes(outputPath);
            }
            
            @Test
            @DisplayName("creates a file where the 4 byte integer for topology size is correct")
            public void compressedFileTopologySizeCorrectInHeader() {
                byte[] topologySize = Arrays.copyOfRange(compressedFile, 0, 4);
                byte[] expected = {0, 0, 0, 9};
        
                assertArrayEquals(expected, topologySize);
            }
            
            @Test
            @DisplayName("creates a file where the 4 byte integer for uncompressed file size is correct")
             public void compressedFileUncompressedByteSizeCorrectInHeader() {
                byte[] byteSize = Arrays.copyOfRange(compressedFile, 4, 8);
                byte[] expected = {0, 0, 0, 7};
        
                assertArrayEquals(expected, byteSize);
            }
    
            @Test
            @DisplayName("creates a file where the topology is correct")
            public void compressedFileTopologyCorrect() {
                byte[] topology = Arrays.copyOfRange(compressedFile, 8, 17);
                byte[] expected = {1, 111, 1, 98, 1, 99, 0, 0, 0};
        
                assertArrayEquals(expected, topology);
            }
    
            @Test
            @DisplayName("creates a file where the encoded data is correct")
            public void compressedFileDataCorrect() {
                byte[] encodedData = Arrays.copyOfRange(compressedFile, 17, 19);
                byte[] expected = {-46, 0};
        
                assertArrayEquals(expected, encodedData);
            }
        }
    }
    
    @Test
    @DisplayName("buildTree builds a tree with leaves in right positions")
    public void buildTreeWorks() {
        String path = getClass()
                .getClassLoader()
                .getResource("treeBuildingTestFile")
                .getPath();
        var stream = new ByteInputStream(path);
        TreeNode root = huffman.buildTree(stream);
        String result = "" + root.getLeftChild().getData()
                + root.getRightChild().getLeftChild().getData()
                + root.getRightChild().getRightChild().getData();
        
        assertEquals("obc", result);
        
        stream.close();
    }
    
    @Test
    @DisplayName("intInFourBytes returns the correct 4 bytes")
    public void intInFourBytesReturnsCorrectBytes() {
        byte[] expected = {0, 0, 5, 57};
        
        assertArrayEquals(expected, huffman.intInFourBytes(1337));
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
