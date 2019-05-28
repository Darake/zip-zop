package zipzop.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zipzop.huffman.TreeNode;

@DisplayName("MinHeap tests")
public class MinHeapTest {
    
    private MinHeap<TreeNode> minHeap;
    
    @BeforeEach
    public void setUp() {
        this.minHeap = new MinHeap<TreeNode>(256);
    }
    
    @Test
    @DisplayName("add adds right object to right index")
    public void addAddsObjectToRightIndex() {
        minHeap.add(new TreeNode('a'));
        
        assertEquals('a', minHeap.getHeap()[1].getData());
    }
}
