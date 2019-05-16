# Software requirements specification  

Zip-Zop is a compression software using lossless compression algorithms. In addition to compression, Zip-Zop can decompress the files back to their normal form.



## Algorithms and data structures  

The initial version will start with Huffman coding and will be expanded into using Deflate format. In addition to Huffman coding, Deflate utilizes the LZ77 algorithm. In my implementation of Huffman's algorithm a HashMap is used for storing character occurrences and for storing each characters encoded binary value. In addition a tree structure is used for finding out the encoded binary values, which in turn is made with a help of a priority queue. The rest of the needed data structures will be found out working on the project. The reason for choosing Deflate is easy expandability from Huffman.



## Input & output

The application will take a file as an input stream, while also outputting a file as output stream.



## Time & space complexity

The time needed for the algorithm should be linear to the input file size. Because of this, the target for application's time complexity is O(n). As the input file will be streamed, the file itself is not loaded into memory, so the only thing taking memory space is the character mapping and the Huffman tree.  Assuming the alphabet size to be a constant, the target space complexity is O(1).



## Sources

* <https://zlib.net/feldspar.html>
* <https://en.m.wikipedia.org/wiki/LZ77_and_LZ78#LZ77>
* <https://engineering.purdue.edu/ece264/17au/hw/HW13?alt=huffman> 

