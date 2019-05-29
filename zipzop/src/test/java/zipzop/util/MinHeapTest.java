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
        this.minHeap = new MinHeap<>(256);
    }
    
    @Test
    @DisplayName("objects start at index 1")
    public void addAddsObjectToRightIndex() {
        var node = new TreeNode('a');
        minHeap.add(node);
        
        assertEquals(node, minHeap.getHeap()[1]);
    }
    
    @Test
    @DisplayName("objects are in right order")
    public void objectsAreInRightOrder() {
        
    }
}
