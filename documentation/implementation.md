# Implementation

## Structure

The project consists of four main packages, which are [huffman](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/huffman>), [io](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/io>), [util](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/util>) and [ui](<https://github.com/Darake/zip-zop/tree/master/zipzop/src/main/java/zipzop/ui>).  

<img src="https://raw.githubusercontent.com/Darake/zip-zop/master/documentation/images/i-1.png" width="450">  

### Huffman

The Huffman package contains the classes needed for Huffman coding. [The Huffman class](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/Huffman.java>) has all the logic behind the Huffman compression and decompression, while the [The TreeNode class](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/huffman/TreeNode.java>) is used for nodes in the Huffman tree.

### io

The io package contains the classes needed for input and output. All the applications input and output is abstracted into this package. [ByteInputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteInputStream.java>) handles the input by streaming the input file and [ByteOutputStream](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/io/ByteOutputStream.java>) handles the output.

### util

Util package is used for data structure implementations. At this moment, only a single class resides here, which is the [MinHeap](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/util/MinHeap.java>) class.

### ui

The ui package contains all the ui related classes, which at the moment is limited to a simple [GUI](<https://github.com/Darake/zip-zop/blob/master/zipzop/src/main/java/zipzop/ui/GUI.java>) class.
