package zipzop.huffman;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import zipzop.io.ByteInputStream;

public class HuffmanTest {
    
    private Huffman huffman;
    
    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
    
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
    
    private byte[] getCompressedFile() throws IOException {
        var classloader = getClass().getClassLoader();
        String file = classloader.getResource("compressionFile").getPath();  
        
        File tempFolder = testFolder.newFolder("folder");
        String compressedFile = tempFolder.getAbsolutePath() + "comrpressed";
        Path compressedPath = Paths.get(compressedFile);
        
        huffman.compress(file, compressedFile);
        
        var byteArray = Files.readAllBytes(compressedPath);
        return byteArray;
    }
    
    @Before
    public void setUp() {
        huffman = new Huffman();
    }

    @Test
    public void getCharOccurrencesInByteArrayWorks() {
        var byteArray = "hello".getBytes();
        var occurrences = huffman.getCharOccurrencesInByteArray(byteArray);
        
        assertEquals((Integer) 2, occurrences.get('l'));
        assertEquals((Integer) 1, occurrences.get('h'));
        assertEquals((Integer) 1, occurrences.get('e'));
        assertEquals((Integer) 1, occurrences.get('o'));
    }
    
    @Test
    public void getCharOccurrencesFromStreamWorks() throws FileNotFoundException, IOException {
        var classloader = getClass().getClassLoader();
        String path = classloader.getResource("testfile").getPath();
        var stream = new ByteInputStream(path);
        
        var occurrences = huffman.getCharOccurrencesFromStream(stream);
        
        assertEquals((Integer) 2, occurrences.get('l'));
        assertEquals((Integer) 1, occurrences.get('h'));
        assertEquals((Integer) 1, occurrences.get('e'));
        assertEquals((Integer) 1, occurrences.get('o'));
    }
    
    @Test
    public void getHuffManTreeForestWorks() {
        var occurrences = new HashMap<Character, Integer> (
                Map.of('h', 5, 'e', 2, 'l', 1, 'o', 3));
        PriorityQueue<TreeNode> treeForest = huffman.getHuffmanTreeForest(occurrences);
        
        assertEquals('l', (char) treeForest.poll().getData());
        assertEquals('e', (char) treeForest.poll().getData());
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
    public void createBitEncodingTableWorks() {
        TreeNode root = getTreeRoot();
        var encodingTable = new HashMap<Character, String>();
        String code = "";
        
        huffman.createBitEncodingTable(encodingTable, code, root);
        
        assertEquals("0", encodingTable.get('k'));
        assertEquals("10", encodingTable.get('l'));
        assertEquals("11", encodingTable.get('h'));
        assertEquals(3, encodingTable.size());
    }
    
    @Test
    public void createTopologyWorks() {
        TreeNode root = getTreeRoot();
        byte[] topology = new byte[9];
        huffman.createTopology(topology, 0, root);
        
        assertEquals((byte) 1, topology[0]);
        assertEquals((byte) 'k', topology[1]);
        assertEquals((byte) 1, topology[2]);
        assertEquals((byte) 'l', topology[3]);
        assertEquals((byte) 1, topology[4]);
        assertEquals((byte) 'h', topology[5]);
        assertEquals((byte) 0, topology[6]);
        assertEquals((byte) 0, topology[7]);
        assertEquals((byte) 0, topology[8]);
    }
    
    @Test
    public void compressedFileTopologySizeCorrectInHeader() throws IOException {
        byte[] compressedFile = getCompressedFile();
        
        assertEquals(0, compressedFile[0]);
        assertEquals(0, compressedFile[1]);
        assertEquals(0, compressedFile[2]);
        assertEquals(9, compressedFile[3]);
    }
    
    @Test
    public void compressedFileUncompressedCharacterAmountCorrectInHeader() throws IOException {
        byte[] compressedFile = getCompressedFile();
        
        assertEquals(0, compressedFile[4]);
        assertEquals(0, compressedFile[5]);
        assertEquals(0, compressedFile[6]);
        assertEquals(7, compressedFile[7]);
    }
    
    @Test
    public void compressedFileTopologyCorrect() throws IOException {
        byte[] compressedFile = getCompressedFile();
        
        assertEquals(1, compressedFile[8]);
        assertEquals(111, compressedFile[9]);
        assertEquals(1, compressedFile[10]);
        assertEquals(98, compressedFile[11]);
        assertEquals(1, compressedFile[12]);
        assertEquals(99, compressedFile[13]);
        assertEquals(0, compressedFile[14]);
        assertEquals(0, compressedFile[15]);
        assertEquals(0, compressedFile[16]);
    }
    
    @Test
    public void compressedFileDataCorrect() throws IOException {
        byte[] compressedFile = getCompressedFile();
        
        assertEquals(-46, compressedFile[17]);
        assertEquals(0, compressedFile[18]);
    }
}
