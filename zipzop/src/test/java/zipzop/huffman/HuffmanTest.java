package zipzop.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanTest {
    
    private Huffman huffman;
    
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
                Map.of('h', 1, 'e', 2, 'l', 3, 'o', 1));
        PriorityQueue<TreeNode> treeForest = huffman.getHuffmanTreeForest(occurrences);
        
        assertEquals('l', (char) treeForest.poll().getData());
        assertEquals('e', (char) treeForest.poll().getData());
    }
}
