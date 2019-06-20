package zipzop.huffman;

import zipzop.io.ByteInputStream;
import zipzop.util.ByteConversion;
import zipzop.util.MinHeap;
import zipzop.util.Stack;

/**
 * Class for Huffman tree related utility.
 */
public class HuffmanTree {
  
  private ByteConversion converter;
  
  public HuffmanTree(ByteConversion converter) {
    this.converter = converter;
  }

  /**
   * Goes through the file calculating the frequency of each byte in the file. Then creates TreeNode
   * objects for each unique byte in the file, where the byte's frequency is the data of the
   * TreeNode. Creates a Huffman tree out of those leaves with the help of MinHeap.
   *
   * @param stream The uncompressed file as ByteInputStream
   * @return Returns a TreeNode, which is the root of the Huffman tree
   */
  public TreeNode buildTreeFromUncompressedFile(ByteInputStream stream) {
    int[] occurrences = getByteFrequenciesInFile(stream);
    MinHeap<TreeNode> leaves = createLeaves(occurrences);
    return buildTreeFromLeaves(leaves);
  }
  
  /**
   * Builds tree out of compressed file's topology in header.
   * @param stream The compressed file as ByteOutputStream
   * @return Returns a TreeNode, which is the root of the Huffman tree
   */
  public TreeNode buildTreeFromCompressedFile(ByteInputStream stream) {
    var stack = new Stack<TreeNode>();
    String binaryString = converter.byteAsString(stream.nextByte());
    while (true) {
      if (binaryString.isEmpty()) {
        binaryString += converter.byteAsString(stream.nextByte());
      }

      if (binaryString.charAt(0) == '1') {
        if (binaryString.length() < 9) {
          binaryString += converter.byteAsString(stream.nextByte());
        }
        stack.push(new TreeNode((char) converter.stringAsByte(binaryString.substring(1, 9))));
        binaryString = binaryString.substring(9);
      } else {
        if (stack.size() == 1) {
          break;
        }

        var rightChild = stack.pop();
        var leftChild = stack.pop();
        stack.push(new TreeNode(leftChild, rightChild));
        binaryString = binaryString.substring(1);
      }
    }
    return stack.pop();
  }

  /**
   * Counts all byte occurrences from the parameter's input stream.
   *
   * @param stream ByteInputStream of a file being read
   * @return Returns an array of occurrences for each byte/char
   */
  private int[] getByteFrequenciesInFile(ByteInputStream stream) {
    var occurrences = new int[256];
    int nextByte;
    while ((nextByte = stream.nextByte()) != -1) {
      //Turns signed int into an unsigned one
      occurrences[nextByte & 0xFF]++;
    }
    return occurrences;
  }

  /**
   * Creates a min heap made out of Huffman tree nodes. The nodes are ordered primary by their
   * weight and secondary if they are a leaf or not.
   *
   * @param occurrences An array of character occurrences.
   * @return MinHeap of TreeNodes
   */
  private MinHeap<TreeNode> createLeaves(int[] occurrences) {
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
   * Creates a Huffman tree out of TreeNode leaves.
   *
   * @param treeForest Nodes to be added to the tree contained in a MinHeap
   * @return Returns the Huffman tree's root node
   */
  private TreeNode buildTreeFromLeaves(MinHeap<TreeNode> treeForest) {
    while (treeForest.size() > 1) {
      TreeNode rightChild = treeForest.poll();
      TreeNode leftChild = treeForest.poll();
      int weight = rightChild.getWeight() + leftChild.getWeight();
      var newNode = new TreeNode(weight, leftChild, rightChild);
      treeForest.add(newNode);
    }
    return treeForest.peek();
  }
}