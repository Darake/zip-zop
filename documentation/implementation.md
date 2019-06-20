# Implementation

## Structure

The project consists of four main packages, which are [huffman](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/huffman>), [io](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/io>), [util](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/util>) and [ui](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/ui>).  

<img src="https://raw.githubusercontent.com/Darake/zip-zop/master/documentation/images/i-1v3.png" width="450">  

### Huffman

The Huffman package contains the classes needed for Huffman coding. The [Huffman](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/Huffman.java>)  class handles the compression and decompression itself. This is the main logic class for the application. The Huffman tree's building happens in [HuffmanTree](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/HuffmanTree.java). Both Huffman and HuffmanTree use the [TreeNode](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/TreeNode.java>) class, which represents the nodes in Huffman tree.

### io

The io package contains the classes needed for input and output. All the applications input and output is abstracted into this package. [ByteInputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteInputStream.java>) handles the input by streaming the input file and [ByteOutputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteOutputStream.java>) handles the output.

### util

Util package is used for data structure implementations and other useful utility. At the moment it includes a [MinHeap](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/MinHeap.java>) implementation, a [Stack](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/Stack.java) implementation and a helper class [ByteConversion](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/ByteConversion.java).

### ui

The ui package contains all the ui related classes, which at the moment is limited to a simple [UserInterface](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/ui/UserInterface.java) class. The [Main](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/ui/Main.java) class is needed to create a jar file with all the dependencies. It simply calls the UserInterface's main method.



## Algorithms

### Huffman coding

#### Compression

The Huffman coding implementation starts with going through the input file and counting all the occurrences. Occurrences are saved into an array, so no library imports are needed. This achieved with converting each read byte into an unsigned integer. Since a byte can only represent a static amount of integers, the static array size is not an issue. The time complexity for this operation is directly related to the file size in other words O(n).

After getting occurrences from a file a tree node is created for each character representation of the byte occurred. Nodes are added into a min heap. At worst the amount of characters to be added to the heap is 256, since an unsigned byte can only represent values from 0 to 255. Each insertion takes O(log m), where m is the amount of nodes in heap at insertion time. The nodes are ordered depending on the occurrences in the heap. Chars with less occurrences come first.

After creating a node representation of all the chars and their occurrences, a Huffman tree is built from the nodes. Two nodes are polled from the heap and a parent is made with those as children. The parent has a weight which equals the children's occurrences summed. After creation, the parent is added back into the heap. This keeps going until there is only one node left in heap. The one left is the tree's root. Like in the last paragraph, each insertion and deletion from heap has a O(log m) time complexity.

When a Huffman tree is created, the encoding table can be built with it. Encoding table is created by traversing the tree in postorder. For each traverse to a left child a "0" is added into the characters encoding and a "1" is added when traversing right. When reaching a leaf, the encoding accumulated with recursion is the leaf character's encoding. The encoding table is saved into an array in same fashion as was done with occurrences. Traversing the tree has a O(k) time complexity, where k is the amount of nodes in the tree.

When all the needed information has been collected and calculated, the encoding itself is done. The input file is gone through byte by byte, while also writing the bytes encoded data into the output file. For each byte the encoding of it is found in the encoding table. Bytes encodings are collected into a binary string representations and when the length passes 8, the file is written into the output file. The time complexity of encoding itself is directly related to the file size i.e. O(n), where n is the size of the file.

Assuming the alphabet size to be a constant, the time complexity for the whole compression process is O(n). As seen [here](https://github.com/Darake/zip-zop/blob/master/documentation/testing.md#performance-testing), this does indeed actualize when real world testing was done. In addition, by the alphabet size being a constant and the file being streamed byte by byte, the space complexity is O(1). This was done so in theory big files can be compressed without the whole file being needed to load into the memory.

#### Decompression

When compressing the file, the Huffman tree is written into the header of the file. This enables the decoding of a compressed file. Each decompression starts with the construction of Huffman tree. Like with compression, the decompression also works by streaming the input file and writing the output file simultaneously. We start by reading the file bit by bit. For a 0 we traverse left in the tree and for a 1 we traverse right. This is done until a leaf is reached. When a leaf is reached, the byte it represents is written into the output file.  Again, we go to the start of the tree and start traversing. This is repeated until the end of the file. The whole process has a time complexity of O(c), where c is the size of the compressed file.



## Possible improvements

For a better compression another algorithm can be added. For example, the project could be expanded into a deflate implementation.