package zipzop.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import zipzop.io.ByteInputStream;

/**
 * Class for Huffman's algorithm
 */
public class Huffman {
    
    /**
     * Counts all char occurrences in parameters byte array by casting byte into
     * char and adding it to HashMap.
     * @param byteArray
     * @return HashMap where the key is a char and the value is occurrence of
     * that char in byte array.
     */
    public HashMap<Character, Integer> getCharOccurrencesInByteArray(byte[] byteArray) {
        var charOccurrences = new HashMap<Character, Integer>();
        for (byte b : byteArray) {
            charOccurrences.merge((char) b, 1, (x, y) -> x + y);
        }
        return charOccurrences;
    }
    
    public HashMap<Character, Integer> getCharOccurrencesFromStream(ByteInputStream stream) {
        throw new java.lang.UnsupportedOperationException("Not supported yet.");
    }
    
    /**
     * Creates a priority queue made out of Huffman tree nodes. The nodes are
     * ordered primary by their weight and secondary if they are a leaf or not.
     * @param occurrences A HashMap of character occurrences.
     * @return PriorityQueue of TreeNodes
     */
    public PriorityQueue<TreeNode> getHuffmanTreeForest(HashMap<Character, Integer> occurrences) {
        var treeForest = new PriorityQueue<TreeNode>();
        for (Map.Entry<Character, Integer> occurrence : occurrences.entrySet()) {
            var node = new TreeNode(occurrence.getValue(), occurrence.getKey());
            treeForest.add(node);
        }
        return treeForest;
    }
    
    /**
     * Creates a Huffman tree.
     * @param treeForest Nodes to be added to the tree contained in a PriorityQueue
     * @return Returns the Huffman tree's root node
     */
    public TreeNode getHuffmanTreeRoot(PriorityQueue<TreeNode> treeForest) {
        while (treeForest.size() > 1) {
            TreeNode rightChild = treeForest.poll();
            TreeNode leftChild = treeForest.poll();
            int weight = rightChild.getWeight() + leftChild.getWeight();
            var newNode = new TreeNode(weight, leftChild, rightChild);
            treeForest.add(newNode);
        }
        return treeForest.peek();
    }
    
    /**
     * Creates a bit encoding table out of a Huffman tree recursively.
     * @param table A Character/String HashMap where the table is added to.
     * @param code The character encoded Binary value so far into the recursion.
     *             Use an empty String when first calling the method.
     * @param node The TreeNode being handled in the next step of recursion.
     *             Use the tree's root when first calling the method.
     */
    public void createBitEncodingTable(HashMap<Character, String> table, String code, TreeNode node) {
        if (node.hasLeftChild()) {
            String newCode = code + "0";
            createBitEncodingTable(table, newCode, node.getLeftChild());
        } 
        
        if (node.hasRightChild()) {
            String newCode = code + "1";
            createBitEncodingTable(table, newCode, node.getRightChild());
        }
        
        table.put(node.getData(), code);
        return;
    }
}
