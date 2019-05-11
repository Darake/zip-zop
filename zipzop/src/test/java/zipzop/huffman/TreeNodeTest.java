package zipzop.huffman;

import org.junit.Test;
import static org.junit.Assert.*;

public class TreeNodeTest {

    @Test
    public void compareToReturnsNegativeWhenWeightSmaller() {
        var node = new TreeNode(1, 'a');
        var other = new TreeNode(2, 'b');
        
        assertEquals(-1, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsPositiveWhenWeightBigger() {
        var node = new TreeNode(2, 'a');
        var other = new TreeNode(1, 'b');
        
        assertEquals(1, node.compareTo(other));
    }
    
    @Test
    public void compareToReturnsNegativeWhenWeightsEqualAndFirstNodeContainsData() {
        var node = new TreeNode(1, 'a');
        var other = new TreeNode(1, null, null);
        
        assertEquals(-1, node.compareTo(other));
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
    
    @Test
    public void hasLeftChildReturnsTrueWhenOneExists() {
        var child = new TreeNode(1, 'a');
        var node = new TreeNode(1, child, null);
        
        assertEquals(true, node.hasLeftChild());
    }
    
    @Test
    public void hasLeftChildReturnsFalseWhenOneDoesntExist() {
        var child = new TreeNode(1, 'a');
        var node = new TreeNode(1, null, null);
        
        assertEquals(false, node.hasLeftChild());
    }
    
    @Test
    public void hasRightChildReturnsTrueWhenOneExists() {
        var child = new TreeNode(1, 'a');
        var node = new TreeNode(1, null, child);
        
        assertEquals(true, node.hasRightChild());
    }
    
    @Test
    public void hasRightChildReturnsFalseWhenOneDoesntExist() {
        var child = new TreeNode(1, 'a');
        var node = new TreeNode(1, null, null);
        
        assertEquals(false, node.hasRightChild());
    }
}
