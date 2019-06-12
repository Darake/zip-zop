package zipzop.huffman;

import java.util.ArrayDeque;
import zipzop.io.ByteInputStream;
import zipzop.io.ByteOutputStream;
import zipzop.util.MinHeap;
import zipzop.util.Stack;

/**
 * Class for Huffman's algorithm
 */
public class Huffman {
    
    
    /**
     * Counts all byte occurrences from the parameter's input stream.
     * @param stream ByteInputStream of a file being read
     * @return Returns an array of occurrences for each byte/char
     */
    public int[] getOccurrencesFromStream(ByteInputStream stream) {
        var occurrences = new int[256];
        int nextByte;
        while ((nextByte = stream.nextByte()) != -1) {
            //Turns signed int into an unsigned one
            occurrences[nextByte & 0xFF]++;
        }
        return occurrences;
    }
    
    /**
     * Creates a min heap made out of Huffman tree nodes. The nodes are
     * ordered primary by their weight and secondary if they are a leaf or not.
     * @param occurrences A HashMap of character occurrences.
     * @return MinHeap of TreeNodes
     */
    public MinHeap<TreeNode> getHuffmanTreeForest(int[] occurrences) {
        var treeForest = new MinHeap<TreeNode>(256);
        for (int i = 0; i < occurrences.length; i++) {
            if (occurrences[i] != 0) {
                /*Turns unsigned int back into signed by casting it to a byte
                and then casts that into the wanted char*/
                var node = new TreeNode(occurrences[i], (char) (byte) i);
                treeForest.add(node);
            }
        }
        return treeForest;
    }
    
    /**
     * Creates a Huffman tree.
     * @param treeForest Nodes to be added to the tree contained in a MinHeap
     * @return Returns the Huffman tree's root node
     */
    public TreeNode getHuffmanTreeRoot(MinHeap<TreeNode> treeForest) {
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
     * @param table A String array where the table is created to.
     * @param code The character encoded Binary value so far into the recursion.
     *             Use an empty String when first calling the method.
     * @param node The TreeNode being handled in the next step of recursion.
     *             Use the tree's root when first calling the method.
     */
    public void createBitEncodingTable(String[] table, String code, TreeNode node) {
        if (node.hasLeftChild()) {
            String newCode = code + "0";
            createBitEncodingTable(table, newCode, node.getLeftChild());
        } 
        
        if (node.hasRightChild()) {
            String newCode = code + "1";
            createBitEncodingTable(table, newCode, node.getRightChild());
        }
        
        if (node.getData() != null) {
            table[(char) node.getData() & 0xFF] = code;
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
     * Encodes the file's data one byte at a time using the encoding table.
     * @param encodingTable Map<Character, String> encoding table for encoding
     *                      the data.
     * @param inputStream   ByteInputStream for the file to be encoded.
     * @param outputStream  ByteOutputStream for the target output file.
     */
    public void encodeData(String[] encodingTable,
            ByteInputStream inputStream, ByteOutputStream outputStream) {
        
        int b;
        String encodedBits = "";
        while ((b = inputStream.nextByte()) != -1) {
            String encodedValue = encodingTable[b & 0xFF];
            encodedBits += encodedValue;
            if (encodedBits.length() >= 8) {
                byte nextByteWritten = (byte) Integer.parseInt(encodedBits.substring(0, 8), 2);
                encodedBits = encodedBits.substring(8);
                outputStream.writeByte(nextByteWritten);
            }
        }
        while (encodedBits.length() >= 8) {
            byte nextByteWritten = (byte) Integer.parseInt(encodedBits.substring(0, 8), 2);
            encodedBits = encodedBits.substring(8);
            outputStream.writeByte(nextByteWritten);
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
    }
    
    /**
     * Converts an int into a byte array with a size of 4 using bit shifting.
     * @param number int to be converted into 4 byte array
     * @return byte array of the int with a size of 4
     */
    public byte[] intInFourBytes(int number) {
        return new byte[] {
            (byte) (number >>> 24),
            (byte) (number >>> 16),
            (byte) (number >>> 8),
            (byte) (number)
        };
    }
    
    /**
     * Compresses a file using Huffman's coding.
     * @param filePath Path to the file to be compressed as String.
     * @param compressedFilePath Wanted to path to the compressed file as String.
     */
    public void compress(String filePath, String compressedFilePath) {
        var occurrenceStream = new ByteInputStream(filePath);
        int[] occurrences = getOccurrencesFromStream(occurrenceStream);
        MinHeap<TreeNode> treeForest = getHuffmanTreeForest(occurrences);
        TreeNode root = getHuffmanTreeRoot(treeForest);
        
        var encodingTable = new String[256];
        createBitEncodingTable(encodingTable, "", root);
        
        int topologySize = 0;
        for (String string : encodingTable) {
            if (string != null) {
                topologySize++;
            }
        }
        topologySize *= 3;
        
        var topology = new byte[topologySize];
        createTopology(topology, 0, root);
        
        var inputStream = new ByteInputStream(filePath);
        var outputStream = new ByteOutputStream(compressedFilePath);
        
        int fileSize = 0;
        for (int occurrence : occurrences) {
            fileSize += occurrence;
        }

        byte[] topologySizeBytes = intInFourBytes(topologySize);
        byte[] fileSizeBytes = intInFourBytes(fileSize);
        outputStream.writeByteArray(topologySizeBytes);
        outputStream.writeByteArray(fileSizeBytes);
        outputStream.writeByteArray(topology);
        
        encodeData(encodingTable, inputStream, outputStream);
        
        inputStream.close();
        outputStream.close();
    }
    
    /**
     * Builds a Huffman's tree for file's decompression.
     * @param stream The ByteInputStream of the file being decompressed. The
     *               stream must be at the start of tree's topology.
     * @return Returns the tree's root node.
     */
    public TreeNode buildTree(ByteInputStream stream) {
        var stack = new Stack<TreeNode>();
        int b;
        while (true) {
            if ((b = stream.nextByte()) == 0) {
                if (stack.size() == 1) break;
                var rightChild = stack.pop();
                var leftChild = stack.pop();
                stack.push(new TreeNode(leftChild, rightChild));
            } else {
                b = stream.nextByte();
                stack.push(new TreeNode((char) b));
            }
        }
        return stack.pop();
    }
    
    /**
     * Finds the next decoded character in the encoded data by traversing the
     * Huffman tree.
     * @param encodedData StringBuilder object containing the encoded data in
     *                    bit representation
     * @param node TreeNode of the next node to be traversed in Huffman tree
     * @return Returns the first decoded byte got from encoded data
     */
    public byte decode(StringBuilder encodedData, TreeNode node) {
        if (node.getData() != null) {
            return (byte) (char) node.getData();
        }
        
        if (encodedData.charAt(0) == '0') {
            encodedData.deleteCharAt(0);
            return decode(encodedData, node.getLeftChild());
        } else {
            encodedData.deleteCharAt(0);
            return decode(encodedData, node.getRightChild());
        }
    }
    
    /**
     * Decompresses a file compressed by the same Huffman compression.
     * @param inputPath Path to the compressed file as String
     * @param outputPath Path to the generated decompressed file as String
     */
    public void decompress(String inputPath, String outputPath) {
        var inputStream = new ByteInputStream(inputPath);
        var outputStream = new ByteOutputStream(outputPath);
        
        int topologySize = inputStream.nextDoubleWord();
        int uncompressedFileSize = inputStream.nextDoubleWord();
        TreeNode root = buildTree(inputStream);
        
        int outputSize = 0;
        int firstDataByte = inputStream.nextByte();
        /* "+ 0x100" Ensures that the binary string is padded with 0s. The added 1 infront of the binary
        string is removed with ".substring(1). An extra byte is read before decoding because a encoded
        data can be spanned from between two bytes.*/
        StringBuilder encodedDataBuffer = new StringBuilder(Integer.toBinaryString((firstDataByte & 0xFF) + 0x100).substring(1));
        while (outputSize < uncompressedFileSize) {
            encodedDataBuffer.append(Integer.toBinaryString((inputStream.nextByte() & 0xFF) + 0x100).substring(1));
            byte nextDecodedByte = decode(encodedDataBuffer, root);
            outputStream.writeByte(nextDecodedByte);
            outputSize++;
        }
    }
}
    
