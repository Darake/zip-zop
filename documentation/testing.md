# Testing

## Huffman

### The Canterbury Corpus 

The first set of tests were done with the Canterbury Corpus collection, which is a popular collection of files for benchmarking compression methods. The test is implemented with JUnit and can be run anytime with the command ``` gradle performanceTests ``` . Each file was compressed and decompressed ten times.  Of those, the average was calculated. The results are following:

| File         | Size     | Compressed Size | % of original size | Compression time | Decompression time |
| ------------ | -------- | --------------- | ------------------ | ---------------- | ------------------ |
| alice29.txt  | 152089b  | 87918b          | 57,81%             | 330ms            | 4791ms             |
| asyoulik.txt | 125179b  | 76018b          | 60,73%             | 264ms            | 3041ms             |
| cp.html      | 24603b   | 16465b          | 66,92%             | 54ms             | 129ms              |
| fields.c     | 11150b   | 7304b           | 65,51%             | 24ms             | 36ms               |
| grammar.lsp  | 3721b    | 2406b           | 64,66%             | 7ms              | 9ms                |
| kennedy.xls  | 1029744b | 463308b         | 44,99%             | 2014ms           | 261456ms           |
| lcet10.txt   | 426754b  | 250825b         | 58,78%             | 953ms            | 41410ms            |
| plrabn12.txt | 481861b  | 275836b         | 57,24%             | 1073ms           | 52735ms            |
| ptt5         | 513216b  | 107036b         | 20,86%             | 892ms            | 40002ms            |
| sum          | 38240b   | 26418b          | 69,08%             | 89ms             | 308ms              |
| xargs.1      | 4227b    | 2832b           | 67,00%             | 9ms              | 11ms               |

From these different size and type of files the average compressed file size is around 63,36% of the original file.



## Sources

* <http://corpus.canterbury.ac.nz/descriptions/>