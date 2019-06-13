# Implementation

## Structure

The project consists of four main packages, which are [huffman](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/huffman>), [io](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/io>), [util](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/util>) and [ui](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/ui>).  

<img src="https://raw.githubusercontent.com/Darake/zip-zop/master/documentation/images/i-1.png" width="450">  

### Huffman

The Huffman package contains the classes needed for Huffman coding. [The Huffman class](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/Huffman.java>) has all the logic behind the Huffman compression and decompression, while the [The TreeNode class](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/TreeNode.java>) is used for nodes in the Huffman tree.

### io

The io package contains the classes needed for input and output. All the applications input and output is abstracted into this package. [ByteInputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteInputStream.java>) handles the input by streaming the input file and [ByteOutputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteOutputStream.java>) handles the output.

### util

Util package is used for data structure implementations. At the moment it includes a [MinHeap](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/MinHeap.java>) implementation and a [Stack](https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/Stack.java) implementation.

### ui

The ui package contains all the ui related classes, which at the moment is limited to a simple [GUI](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/ui/GUI.java>) class.



## Algorithms

### Huffman coding

#### Compression

The Huffman coding implementation starts with going through the input file and counting all the occurrences. Occurrences are saved into an array, so no library imports are needed. This achieved with converting each read byte into an unsigned integer. Since a byte can only represent a static amount of integers, the static array size is not an issue. The time complexity for this operation is directly related to the file size in other words O(n).

After getting occurrences from a file a node for a tree is created for each character representation of the byte occurred. Nodes are added into a min heap. This doesn't take any time of significance. At worst the amount of characters to be added to the heap are 256 characters since an unsigned byte can only represent values from 0 to 255. Each insertion takes O(log m), where m is the amount of nodes in heap at insertion time. The nodes are ordered depending on the occurrences in the heap. Chars with less occurrences come first.

After creating a node representation of all the chars and their occurrences, a Huffman tree is built from the nodes. Two nodes are polled from the heap and a parent is made with those as children. The parent has a weight which equals the children's occurrences summed. After creation, the parent is added back into the heap. This keeps going until there is only one node left in heap. The one left is the tree's root. Like in the last paragraph, each insertion and deletion from heap has a O(log m) time complexity.

When a Huffman tree is created, the encoding table can be built with it. Encoding table is created by traversing the tree in postorder recursively. For each traverse to a left child a "0" is added into the characters encoding and a "1" is added when traversing right. When reaching a leaf, the encoding accumulated with recursion is the leaf's encoding. The encoding table is saved into an array in same fashion as was done with occurrences. Traversing the tree has a O(k) time complexity, where k is the amount of nodes in the tree.

When all the needed information has been collected and calculated, the encoding itself is done. The input file is gone through byte by byte, while also writing the bytes encoded data into the output file. For each byte the encoding of it found in the encoding table. Bytes encodings are collected into a binary string representations and when the length passes 8 the file is written into the output file. The time complexity of encoding itself is directly related to the file size i.e. O(n), where n is the size of the file.

Assuming the alphabet size to be a constant, the time complexity for the whole compression process is O(n). In addition, by the alphabet size being a constant and the file being streamed byte by byte, the space complexity is O(1). This was done so in theory big files can be compressed without the whole file being needed to load into the memory.

#### Decompression

When compressing the file, the Huffman tree is written into the header of the file. This enables the decoding of a compressed file. Each decompression starts with the construction of Huffman tree. Like with compression, the decompression also works by streaming the input file and writing the output file simultaneously. We start by reading the file bit by bit. For a 0 we traverse left in the tree and for a 1 we traverse right. This is done until a leaf is reached. When a leaf is reached, the byte it represents is written into the output file.  Again, we go to the start of the tree and start traversing. This is repeated until the end of the file. The whole process has a time complexity of O(c), where c is the size of the compressed file.



## Possible improvements

For a better compression another algorithm can be added. For example, the project could be expanded into a deflate implementation. In addition, some little tweaks could be done to the Huffman algorithm to save some space. Currently the 0s and 1s in the compressed file's Huffman tree representation are represented as characters. To save space, these could be represented as single bits.