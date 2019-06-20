package zipzop.huffman;

import zipzop.io.ByteInputStream;
import zipzop.io.ByteOutputStream;
import zipzop.util.ByteConversion;

/**
 * Class for Huffman compression and decompression.
 */
public class Huffman {

  private ByteConversion converter;
  private HuffmanTree tree;

  public Huffman() {
    this.converter = new ByteConversion();
    this.tree = new HuffmanTree(converter);
  }

  /**
   * Creates a bit encoding table out of a Huffman tree recursively.
   *
   * @param table A String array where the table is created to.
   * @param code The character encoded Binary value so far into the recursion. Use an empty String
   *        when first calling the method.
   * @param node The TreeNode being handled in the next step of recursion. Use the tree's root when
   *        first calling the method.
   */
  private void createBitEncodingTable(String[] table, String code, TreeNode node) {
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
   * Recursively creates a Huffman tree topology as binary string.
   *
   * @param topology The StringBuilder in which the binary string is created into
   * @param node The current TreeNode in Huffman tree
   * @return Huffman tree topology as binary string
   */
  private String createTopologyBinaryString(StringBuilder topology, TreeNode node) {
    if (node == null) {
      return topology.toString();
    }

    createTopologyBinaryString(topology, node.getLeftChild());
    createTopologyBinaryString(topology, node.getRightChild());

    if (node.getData() == null) {
      topology.append("0");
    } else {
      topology.append("1");
      topology.append(converter.byteAsString(node.getData()));
    }
    return topology.toString();
  }
  
  /**
   * Creates a topology from a binary string created from a Huffman tree.
   * 
   * @param root Huffman tree's root node
   * @return Returns the topology as an byte array
   */
  private byte[] createTopology(TreeNode root) {
    String topologyString = createTopologyBinaryString(new StringBuilder(), root);
    for (int i = 0; i < topologyString.length() % 8; i++) {
      topologyString += "0";
    }
    byte[] topologyArray = new byte[topologyString.length() / 8];
    for (int i = 0; i < topologyArray.length; i++) {
      topologyArray[i] = converter.stringAsByte(topologyString.substring(0, 8));
      topologyString = topologyString.substring(8);
    }
    return topologyArray;
  }

  /**
   * Encodes the file's data one byte at a time using the encoding table.
   *
   * @param encodingTable String[] encoding table for encoding the data.
   * @param inputStream ByteInputStream for the file to be encoded.
   * @param outputStream ByteOutputStream for the target output file.
   */
  private void encodeData(String[] encodingTable, ByteInputStream inputStream,
          ByteOutputStream outputStream) {

    String encodedBits = "";
    
    for (int b = inputStream.nextByte(); b != -1; b = inputStream.nextByte()) {
      encodedBits += encodingTable[b & 0xFF];
      while (encodedBits.length() >= 8) {
        outputStream.writeByte(converter.stringAsByte(encodedBits.substring(0, 8)));
        encodedBits = encodedBits.substring(8);
      }
    }
    
    if (!encodedBits.isEmpty()) {
      for (int i = encodedBits.length(); i < 8; i++) {
        encodedBits += "0";
      }
      outputStream.writeByte(converter.stringAsByte(encodedBits));
    }
  }

  /**
   * Compresses a file using Huffman's coding.
   *
   * @param filePath Path to the file to be compressed as String.
   * @param compressedFilePath Wanted to path to the compressed file as String.
   */
  public void compress(String filePath, String compressedFilePath) {
    var frequencyStream = new ByteInputStream(filePath);
    TreeNode root = tree.buildTreeFromUncompressedFile(frequencyStream);
    frequencyStream.close();

    var encodingTable = new String[256];
    createBitEncodingTable(encodingTable, "", root);

    byte[] topology = createTopology(root);

    var inputStream = new ByteInputStream(filePath);
    var outputStream = new ByteOutputStream(compressedFilePath);

    byte[] fileSizeBytes = converter.intInFourBytes(inputStream.length());
    outputStream.writeByteArray(fileSizeBytes);
    outputStream.writeByteArray(topology);

    encodeData(encodingTable, inputStream, outputStream);

    inputStream.close();
    outputStream.close();
  }

  /**
   * Finds the next decoded character in the encoded data by traversing the Huffman tree.
   *
   * @param encodedData StringBuilder object containing the encoded data in bit representation
   * @param node TreeNode of the next node to be traversed in Huffman tree
   * @param stream ByteInputStream of the file being decompressed
   * @return Returns the first decoded byte got from encoded data
   */
  private byte decode(StringBuilder encodedData, TreeNode node, ByteInputStream stream) {
    if (node.getData() != null) {
      return (byte) (char) node.getData();
    } else if (encodedData.length() == 0) {
      encodedData.append(converter.byteAsString(stream.nextByte()));
    }

    if (encodedData.charAt(0) == '0') {
      encodedData.deleteCharAt(0);
      return decode(encodedData, node.getLeftChild(), stream);
    } else {
      encodedData.deleteCharAt(0);
      return decode(encodedData, node.getRightChild(), stream);
    }
  }

  /**
   * Decompresses a file compressed by the same Huffman compression.
   *
   * @param inputPath Path to the compressed file as String
   * @param outputPath Path to the generated decompressed file as String
   */
  public void decompress(String inputPath, String outputPath) {
    var inputStream = new ByteInputStream(inputPath);
    var outputStream = new ByteOutputStream(outputPath);

    int uncompressedFileSize = inputStream.nextDoubleWord();
    TreeNode root = tree.buildTreeFromCompressedFile(inputStream);

    int outputSize = 0;
    StringBuilder encodedDataBuffer = new StringBuilder();
    while (outputSize < uncompressedFileSize) {
      byte nextDecodedByte = decode(encodedDataBuffer, root, inputStream);
      outputStream.writeByte(nextDecodedByte);
      outputSize++;
    }
  }
}