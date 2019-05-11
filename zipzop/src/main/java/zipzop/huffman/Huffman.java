package zipzop.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

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
    
    public TreeNode getHuffmanTreeRoot(HashMap<Character, Integer> occurrences) {
        return null;
    }
}
