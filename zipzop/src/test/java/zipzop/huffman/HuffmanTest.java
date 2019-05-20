package zipzop.huffman;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import zipzop.io.ByteInputStream;
import zipzop.io.ByteOutputStream;

public class HuffmanTest {
    
    private Huffman huffman;
    private String file;
    private String compressedFile;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
    @Before
    public void setUp() {
        huffman = new Huffman();
    }
    
    private PriorityQueue<TreeNode> getTreeForest() {
        var treeForest = new PriorityQueue<TreeNode>();
        treeForest.add(new TreeNode(2, 'h'));
        treeForest.add(new TreeNode(5, 'l'));
        treeForest.add(new TreeNode(1, 's'));
        treeForest.add(new TreeNode(9, 'k'));
        return treeForest;
    }
    
    private TreeNode getTreeRoot() {
        var leftGrandChild = new TreeNode(5, 'l');
        var rightGrandChild = new TreeNode(2, 'h');
        var leftChild = new TreeNode(9, 'k');
        var rightChild = new TreeNode(7, leftGrandChild, rightGrandChild);
        var root = new TreeNode(16, leftChild, rightChild);
        
        return root;
    }
    
    private void setUpFilePaths() throws IOException {
        var classloader = getClass().getClassLoader();
        file = classloader.getResource("compressionFile").getPath();  
        
        File tempFolder = testFolder.newFolder("folder");
        compressedFile = tempFolder.getAbsolutePath() + "comrpressed";
    }
    
    private byte[] getCompressedFile() throws IOException {
        setUpFilePaths();
        Path compressedPath = Paths.get(compressedFile);
        
        huffman.compress(file, compressedFile);
        
        var byteArray = Files.readAllBytes(compressedPath);
        return byteArray;
    }
    
    @Test
    public void getCharOccurrencesInByteArrayReturnsExpectedMap() {
        var byteArray = "hello".getBytes();
        Map<Character, Integer> occurrences = huffman.getCharOccurrencesInByteArray(byteArray);
        Map<Character, Integer> expected = Map.of('l', 2, 'h', 1, 'e', 1, 'o', 1);
        
        assertEquals(expected, occurrences);
    }
    
    @Test
    public void getCharOccurrencesFromStreamReturnsExpectedMap() throws FileNotFoundException, IOException {
        String path = getClass()
                .getClassLoader()
                .getResource("testfile")
                .getPath();
        var stream = new ByteInputStream(path);
        Map<Character, Integer> occurrences = huffman.getCharOccurrencesFromStream(stream);
        Map<Character, Integer> expected = Map.of('l', 2, 'h', 1, 'e', 1, 'o', 1);
        
        assertEquals(expected, occurrences);
    }
    
    @Test
    public void getHuffManTreeForestReturnsExpectedQueue() {
        Map<Character, Integer> occurrences = Map.of('h', 5, 'e', 2, 'l', 1, 'o', 3);
        PriorityQueue<TreeNode> treeForest = huffman.getHuffmanTreeForest(occurrences);
        String result = "";
        while(treeForest.peek() != null) {
            result += treeForest.poll().getData();
        }
        
        assertEquals("leoh", result);
    }
    
    @Test
    public void getHuffmanTreeRootTreeHasRightWeights() {
        TreeNode treeRoot = huffman.getHuffmanTreeRoot(getTreeForest());
        
        assertEquals(17, treeRoot.getWeight());
        assertEquals(9, treeRoot.getLeftChild().getWeight());
        var rightChild = treeRoot.getRightChild();
        assertEquals(8, rightChild.getWeight());
        assertEquals(5, rightChild.getLeftChild().getWeight());
        var rightGrandChild = rightChild.getRightChild();
        assertEquals(3, rightGrandChild.getWeight());
        assertEquals(2, rightGrandChild.getLeftChild().getWeight());
        assertEquals(1, rightGrandChild.getRightChild().getWeight());
    }
    
    @Test
    public void getHuffmanTreeRootTreeHasLeavesInRightPlaces() {
        TreeNode treeRoot = huffman.getHuffmanTreeRoot(getTreeForest());
        
        assertEquals('k', (char) treeRoot.getLeftChild().getData());
        assertEquals('l', (char) treeRoot.getRightChild().getLeftChild().getData());
        assertEquals('h', (char) treeRoot.getRightChild().getRightChild().getLeftChild().getData());
        assertEquals('s', (char) treeRoot.getRightChild().getRightChild().getRightChild().getData());        
    }
    
    @Test
    public void createBitEncodingTableReturnsTableWithRightCodes() {
        TreeNode root = getTreeRoot();
        var encodingTable = new HashMap<Character, String>();
        String code = "";
        
        huffman.createBitEncodingTable(encodingTable, code, root);
        Map<Character, String> expected = Map.of('k', "0", 'l', "10", 'h', "11");
        
        assertEquals(expected, encodingTable);
    }
    
    @Test
    public void createTopologyCreatesExpectedByteArray() {
        TreeNode root = getTreeRoot();
        byte[] topology = new byte[9];
        huffman.createTopology(topology, 0, root);
        byte[] expected =  {1, 'k', 1, 'l', 1, 'h', 0, 0, 0};
        
        assertArrayEquals(expected, topology);
    }
    
    @Test
    public void encodeDataWritesExpectedBytes() throws IOException {
        setUpFilePaths();
        var inputStream = new ByteInputStream(file);
        var outputStream = new ByteOutputStream(compressedFile);
        var encodingTable = Map.of('o', "0", 'b', "10", 'c', "11");
        
        huffman.encodeData(encodingTable, inputStream, outputStream);
        
        Path compressedPath = Paths.get(compressedFile);
        var byteArray = Files.readAllBytes(compressedPath);
        byte[] expected = {-46, 0};
        
        assertArrayEquals(expected, byteArray);
    }
    
    @Test
    public void compressedFileTopologySizeCorrectInHeader() throws IOException {
        byte[] topologySize = Arrays.copyOfRange(getCompressedFile(), 0, 4);
        byte[] expected = {0, 0, 0, 9};
        
        assertArrayEquals(expected, topologySize);
    }
    
    @Test
    public void compressedFileUncompressedByteSizeCorrectInHeader() throws IOException {
        byte[] byteSize = Arrays.copyOfRange(getCompressedFile(), 4, 8);
        byte[] expected = {0, 0, 0, 7};
        
        assertArrayEquals(expected, byteSize);
    }
    
    @Test
    public void compressedFileTopologyCorrect() throws IOException {
        byte[] topology = Arrays.copyOfRange(getCompressedFile(), 8, 17);
        byte[] expected = {1, 111, 1, 98, 1, 99, 0, 0, 0};
        
        assertArrayEquals(expected, topology);
    }
    
    @Test
    public void compressedFileDataCorrect() throws IOException {
        byte[] compressedFile = Arrays.copyOfRange(getCompressedFile(), 17, 19);
        byte[] expected = {-46, 0};
        
        assertArrayEquals(expected, compressedFile);
    }
    
    @Test
    public void buildTreeWorks() throws FileNotFoundException, IOException {
        String path = getClass()
                .getClassLoader()
                .getResource("treeBuildingTestFile")
                .getPath();
        var stream = new ByteInputStream(path);
        TreeNode root = huffman.buildTree(stream);
        
        assertEquals('o', (char) root.getLeftChild().getData());
        assertEquals('b', (char) root.getRightChild().getLeftChild().getData());
        assertEquals('c', (char) root.getRightChild().getRightChild().getData());
        
        stream.close();
    }
}
