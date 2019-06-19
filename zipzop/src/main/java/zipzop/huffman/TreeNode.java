package zipzop.huffman;

/**
 * A node for the Huffman coding tree.
 */
public class TreeNode implements Comparable<TreeNode> {

  private int weight;
  private Character data;
  private TreeNode leftChild;
  private TreeNode rightChild;

  /**
   * Constructor for leaves.
   *
   * @param weight The amount of times a character occurs in a file
   * @param data The character itself
   */
  public TreeNode(int weight, Character data) {
    this.weight = weight;
    this.data = data;
  }

  /**
   * Constructor for non-leaves.
   *
   * @param weight Combined weight of left and right child
   * @param leftChild Node's left TreeNode child
   * @param rightChild Node's right TreeNode child
   */
  public TreeNode(int weight, TreeNode leftChild, TreeNode rightChild) {
    this.weight = weight;
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  /**
   * Constructor for leaves when building tree for decompression.
   *
   * @param data The character itself
   */
  public TreeNode(Character data) {
    this.data = data;
  }

  /**
   * Constructor for non-leaves when building tree for decompression.
   *
   * @param leftChild Node's left TreeNode child
   * @param rightChild Node's right TreeNode child
   */
  public TreeNode(TreeNode leftChild, TreeNode rightChild) {
    this.leftChild = leftChild;
    this.rightChild = rightChild;
  }

  public int getWeight() {
    return this.weight;
  }

  public Character getData() {
    return data;
  }

  public TreeNode getLeftChild() {
    return leftChild;
  }

  public TreeNode getRightChild() {
    return rightChild;
  }

  /**
   * Checks if TreeNode has a left child node.
   *
   * @return Returns a true if child exists and false otherwise
   */
  public boolean hasLeftChild() {
    return leftChild != null;
  }

  /**
   * Checks if TreeNode has a right child node.
   *
   * @return Returns a true if child exists and false otherwise
   */
  public boolean hasRightChild() {
    return rightChild != null;
  }

  @Override
  public int compareTo(TreeNode other) {
    if (this.weight < other.getWeight()) {
      return -1;
    } else if (this.weight > other.getWeight()) {
      return 1;
    } else if (this.data != null && other.getData() == null) {
      return -1;
    } else if (this.data == null && other.getData() != null) {
      return 1;
    } else {
      return 0;
    }
  }
}
