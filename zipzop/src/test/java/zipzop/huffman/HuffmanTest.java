package zipzop.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTest {
    
    private Huffman huffman;
    
    public PriorityQueue<TreeNode> getTreeForest() {
        var treeForest = new PriorityQueue<TreeNode>();
        treeForest.add(new TreeNode(2, 'h'));
        treeForest.add(new TreeNode(5, 'l'));
        treeForest.add(new TreeNode(1, 's'));
        treeForest.add(new TreeNode(9, 'k'));
        return treeForest;
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
        var leftGrandChild = new TreeNode(5, 'l');
        var rightGrandChild = new TreeNode(2, 'h');
        var leftChild = new TreeNode(9, 'k');
        var rightChild = new TreeNode(7, leftGrandChild, rightGrandChild);
        var root = new TreeNode(16, leftChild, rightChild);
        var encodingTable = new HashMap<Character, String>();
        String code = "";
        
        huffman.createBitEncodingTable(encodingTable, code, root);
        
        assertEquals("0", encodingTable.get('k'));
        assertEquals("10", encodingTable.get('l'));
        assertEquals("11", encodingTable.get('h'));
    }
}
