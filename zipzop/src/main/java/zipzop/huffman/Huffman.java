package zipzop.huffman;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import zipzop.io.ByteInputStream;
import zipzop.io.ByteOutputStream;

/**
 * Class for Huffman's algorithm
 */
public class Huffman {
    
    /**
     * Counts all char occurrences in parameter's byte array by casting byte into
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
     * Counts all char occurrences from the parameter's input stream. File is
     * read byte by byte and each byte is cast into char. Chars and their 
     * occurrences are stored in a HashMap.
     * @param stream A input stream of a file
     * @return HashMap of occurrences
     * @throws IOException 
     */
    public HashMap<Character, Integer> getCharOccurrencesFromStream(ByteInputStream stream) throws IOException {
        var charOccurrences = new HashMap<Character, Integer>();
        int data;
        while ((data = stream.nextByte()) != -1) {
            charOccurrences.merge((char) data, 1, (x, y) -> x + y);
        }
        stream.close();
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
        
        if (node.getData() != null) {
            table.put(node.getData(), code);
        }

        return;
    }
    
    /**
     * Creates a topology of the Huffman coding tree for compressed file's
     * header. Creation is done with recursion.
     * @param node The TreeNode to be handled in the next step of recursion.
     *             Use the tree's root when first calling the method.
     * @param topology A byte array that the topology is filled into.
     * @param i Index of the next empty spot in byte array.
     * @return Updated index of the next empty spot in byte array.
     */
    public int createTopology(byte[] topology, int i, TreeNode node) {
        if (node == null) {
            return i;
        }
        
        i = createTopology(topology, i, node.getLeftChild());
        i = createTopology(topology, i, node.getRightChild());
        
        if (node.getData() == null) {
            topology[i] = (byte) 0;
            i++;
        } else {
            topology[i] = (byte) 1;
            i++;
            topology[i] = (byte) (char) node.getData();
            i++;
        }
        return i;
    }
    
    /**
     * Compresses a file using Huffman's coding.
     * @param filePath Path to the file to be compressed as String.
     * @param compressedFilePath Wanted to path to the compressed file as String.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void compress(String filePath, String compressedFilePath) throws FileNotFoundException, IOException {
        var occurrenceStream = new ByteInputStream(filePath);
        HashMap<Character, Integer> occurrences = getCharOccurrencesFromStream(occurrenceStream);
        PriorityQueue<TreeNode> treeForest = getHuffmanTreeForest(occurrences);
        TreeNode root = getHuffmanTreeRoot(treeForest);
        
        var encodingTable = new HashMap<Character, String>();
        createBitEncodingTable(encodingTable, "", root);
        
        int topologySize = encodingTable.size() * 3;
        var topology = new byte[topologySize];
        createTopology(topology, 0, root);
        
        var inputStream = new ByteInputStream(filePath);
        var outputStream = new ByteOutputStream(compressedFilePath);
        
        int charsInFile = occurrences.values().stream().mapToInt(i->i).sum();

        byte[] topologySizeBytes = ByteBuffer.allocate(4).putInt(topologySize).array();
        byte[] charsInFileBytes = ByteBuffer.allocate(4).putInt(charsInFile).array();
        outputStream.writeByteArray(topologySizeBytes);
        outputStream.writeByteArray(charsInFileBytes);
        outputStream.writeByteArray(topology);
        
        int b;
        String encodedBits = "";
        while ((b = inputStream.nextByte()) != -1) {
            String encodedValue = encodingTable.get((char) b);
            encodedBits += encodedValue;
            if (encodedBits.length() >= 8) {
                byte nextByteWritten = (byte) Integer.parseInt(encodedBits.substring(0, 8), 2);
                encodedBits = encodedBits.substring(8);
                outputStream.writeByte(nextByteWritten);
            }
        }
        if (!encodedBits.isEmpty()) {
            int zeros = 8 - encodedBits.length();
            String padding = "";
            for (int i = 0; i < zeros; i++) {
                padding += "0";
            }
            encodedBits = padding + encodedBits;
            outputStream.writeByte(Byte.parseByte(encodedBits, 2));
        }
        
        inputStream.close();
        outputStream.close();
    }
}
    
