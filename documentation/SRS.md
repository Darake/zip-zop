# Software requirements specification  

Zip-Zop is a compression software using lossless compression algorithms. In addition to compression, Zip-Zop can return the files to their normal form.



## Algorithms and data structures  

The initial version will start with Huffman coding and will be expanded into using Deflate format. In addition to Huffman coding, Deflate utilizes the LZ77 algorithm. In Huffman coding at least some type of a tree structure is used. The rest of the needed data structures will be found out working on the project. The reason for choosing Deflate is easy expandability from Huffman.



## Input & output

The application will take a file as an input and after compressing it, will return a compressed file as output.



## Time & space complexity

As the input file is gone through as an uncompressed data stream, the time needed for the algorithm should be linear to the input file size. Because of this, the target for application's time complexity is O(n). This also means that there should not be any growth in memory during streaming, which sets the target for space complexity O(1).



## Sources

* <https://zlib.net/feldspar.html>
* <https://en.m.wikipedia.org/wiki/LZ77_and_LZ78#LZ77>

