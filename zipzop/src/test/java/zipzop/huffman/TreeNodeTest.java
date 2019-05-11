package zipzop.huffman;

import org.junit.Test;
import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void compareToReturnsPositiveWhenWeightSmaller() {
        var node = new TreeNode(1, 'a');
        var other = new TreeNode(2, 'b');
        
        assertEquals(1, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsNegativeWhenWeightBigger() {
        var node = new TreeNode(2, 'a');
        var other = new TreeNode(1, 'b');
        
        assertEquals(-1, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsPositiveWhenWeightsEqualAndFirstNodeContainsData() {
        var node = new TreeNode(1, 'a');
        var other = new TreeNode(1, null, null);
        
        assertEquals(1, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsZeroWhenWeightsEqualAndBothContainData() {
        var node = new TreeNode(1, 'a');
        var other = new TreeNode(1, 'b');
        
        assertEquals(0, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsZeroWhenWeightsEqualAndBothDataNull() {
        var node = new TreeNode(1, null, null);
        var other = new TreeNode(1, null, null);
        
        assertEquals(0, node.compareTo(other));
    }
}
