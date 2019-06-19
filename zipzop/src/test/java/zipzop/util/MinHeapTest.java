package zipzop.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import zipzop.huffman.TreeNode;

@DisplayName("MinHeap tests")
public class MinHeapTest {

  private MinHeap<TreeNode> minHeap;
  private TreeNode firstNode;
  private TreeNode secondNode;
  private TreeNode thirdNode;
  private TreeNode fourthNode;

  @BeforeEach
  public void setUp() {
    minHeap = new MinHeap<>(256);
    fourthNode = new TreeNode(5, 'd');
    firstNode = new TreeNode(1, 'a');
    thirdNode = new TreeNode(3, 'c');
    secondNode = new TreeNode(4, 'b');

    minHeap.add(fourthNode);
    minHeap.add(firstNode);
    minHeap.add(thirdNode);
    minHeap.add(secondNode);
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
    Comparable[] expected = new Comparable[256];
    expected[1] = firstNode;
    expected[2] = secondNode;
    expected[3] = thirdNode;
    expected[4] = fourthNode;

    assertArrayEquals(expected, minHeap.getHeap());
  }

  @Test
  @DisplayName("peek returns min value of heap")
  public void peekReturnsRightValue() {
    assertEquals(firstNode, minHeap.peek());
  }

  @Test
  @DisplayName("poll returns min value of heap")
  public void pollReturnsRightValue() {
    assertEquals(firstNode, minHeap.poll());
  }

  @Test
  @DisplayName("poll removes min value from heap")
  public void pollRemovesFirstValue() {
    minHeap.poll();

    assertNotEquals(firstNode, minHeap.poll());
  }

  @Test
  @DisplayName("polling orders the heap correctly")
  public void pollOrdersHeapCorrectly() {
    minHeap.poll();
    Comparable[] expected = new Comparable[256];
    expected[1] = thirdNode;
    expected[2] = secondNode;
    expected[3] = fourthNode;

    assertArrayEquals(expected, minHeap.getHeap());
  }
}
