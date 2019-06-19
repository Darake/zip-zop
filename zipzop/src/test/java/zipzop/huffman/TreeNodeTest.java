package zipzop.huffman;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("TreeNode tests")
public class TreeNodeTest {

  private TreeNode node;

  @BeforeEach
  public void setUp() {
    node = new TreeNode(1, 'a');
  }

  @Test
  @DisplayName("compareTo returns a negative value when weight is smaller")
  public void compareToReturnsNegativeWhenWeightSmaller() {
    var other = new TreeNode(2, 'b');

    assertEquals(-1, node.compareTo(other));
  }

  @Test
  @DisplayName("compareTo returns a positive value when weight is bigger")
  public void compareToReturnsPositiveWhenWeightBigger() {
    var other = new TreeNode(0, 'b');

    assertEquals(1, node.compareTo(other));
  }

  @Test
  @DisplayName("compareTo returns a negative when weight equal and first node contains data")
  public void compareToReturnsNegativeWhenWeightsEqualAndFirstNodeContainsData() {
    var other = new TreeNode(1, null, null);

    assertEquals(-1, node.compareTo(other));
  }
  
  @Test
  @DisplayName("compareTo returns a positive when weight equal and only second node has data")
  public void compareToReturnsPositiveWhenWeightsEqualsAndSecondsNodeContainsData() {
    var firstNode = new TreeNode(1, null, null);
    
    assertEquals(1, firstNode.compareTo(node));
  }
  
  @Test
  @DisplayName("compareTo returns zero when weight is equal and both contain data")
  public void compareToReturnsZeroWhenWeightsEqualAndBothContainData() {
    var other = new TreeNode(1, 'b');

    assertEquals(0, node.compareTo(other));
  }

  @Test
  @DisplayName("compareTo returns zero when weight is equal and neither contain data")
  public void compareToReturnsZeroWhenWeightsEqualAndBothDataNull() {
    var node = new TreeNode(1, null, null);
    var other = new TreeNode(1, null, null);

    assertEquals(0, node.compareTo(other));
  }

  @Test
  @DisplayName("hasLeftChild returns true when one exists")
  public void hasLeftChildReturnsTrueWhenOneExists() {
    var parent = new TreeNode(1, node, null);

    assertEquals(true, parent.hasLeftChild());
  }

  @Test
  @DisplayName("hasLeftChild returns false when one doesn't exist")
  public void hasLeftChildReturnsFalseWhenOneDoesntExist() {
    var parent = new TreeNode(1, null, null);

    assertEquals(false, parent.hasLeftChild());
  }

  @Test
  @DisplayName("hasRightChild returns true when one exists")
  public void hasRightChildReturnsTrueWhenOneExists() {
    var parent = new TreeNode(1, null, node);

    assertEquals(true, parent.hasRightChild());
  }

  @Test
  @DisplayName("hasRightChild returns false when one doesn't exist")
  public void hasRightChildReturnsFalseWhenOneDoesntExist() {
    var parent = new TreeNode(1, null, null);

    assertEquals(false, parent.hasRightChild());
  }
}
