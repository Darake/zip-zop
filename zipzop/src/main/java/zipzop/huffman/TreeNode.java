package zipzop.huffman;

/**
 * A node for the Huffman coding tree.
 */
public class TreeNode implements Comparable<TreeNode>{
    
    private int weight;
    private Character data;
    private TreeNode leftChild;
    private TreeNode rightChild;
    
    /**
     * Constructor for leaves
     * @param weight The amount of times a character occurs in a file
     * @param leftChild
     * @param rightChild
     * @param data The character itself
     */
    public TreeNode(int weight, Character data) {
        this.weight = weight;
        this.data = data;
    }
    
    /**
     * Constructor for non-leaves
     * @param weight Combined weight of left and right child
     * @param leftChild
     * @param rightChild 
     */
    public TreeNode(int weight, TreeNode leftChild, TreeNode rightChild) {
        this.weight = weight;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }
    
    public int getWeight() {
        return weight;
    }
    
    public Character getData() {
        return data;
    }

    @Override
    public int compareTo(TreeNode other) {
        if (this.weight < other.getWeight()) {
            return 1;
        } else if (this.weight > other.getWeight()) {
            return -1;
        }
        
        if (this.data != null && other.getData() == null) {
            return 1;
        } else {
            return 0;
        }
    }
}
