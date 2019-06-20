# Testing

## Performance testing

### Huffman

The tests were done with the Canterbury Corpus collection, which is a popular collection of files for benchmarking compression methods. The test is implemented with JUnit and can be run anytime with the command ``` gradle performanceTests ``` . Each file was compressed and decompressed ten times.  Of those, the average was calculated. The results are following:

| File         |       Size | Size after | % of original size | Compression | Decompression |
| ------------ | ---------: | ---------: | ------------------ | ----------: | ------------: |
| alice29.txt  |   152 089b |    87 785b | 57,72%             |       328ms |         261ms |
| asyoulik.txt |   125 179b |    75 895b | 60,63%             |       263ms |         215ms |
| cp.html      |    24 603b |    16 311b | 66,30%             |        53ms |          43ms |
| fields.c     |    11 150b |     7 143b | 64,06%             |        23ms |          19ms |
| grammar.lsp  |     3 721b |     2 269b | 60,98%             |         7ms |           6ms |
| kennedy.xls  | 1 029 744b |   462 856b | 44,95%             |     1 977ms |       1 641ms |
| lcet10.txt   |   426 754b |   250 674b | 58,74%             |       888ms |         732ms |
| plrabn12.txt |   481 861b |   275 691b | 57,21%             |       994ms |         821ms |
| ptt5         |   513 216b |   106 754b | 20,80%             |       827ms |         715ms |
| sum          |     38240b |    25 968b | 67,91%             |        84ms |          70ms |
| xargs.1      |      4227b |     2 699b | 63,86%             |         9ms |           7ms |

Judging from these tests, our Huffman implementation's compression can reach an average 56,65% of the original file size or a median of 60,63%.



By inserting the compression times and file sizes into a graph a comparison can be made.

<img src="https://raw.githubusercontent.com/Darake/zip-zop/master/documentation/images/t-1.png">Other than in extreme cases, the compression time appears to be growing linearly with the file size. This affirms the assumptions made [here](https://github.com/Darake/zip-zop/blob/master/documentation/implementation.md#compression).



## Unit testing

The framework used for testing was JUnit 5. All but the the classes in the ui package have tests written for them. Coverage for those included in testing can be seen here:

<img src="https://raw.githubusercontent.com/Darake/zip-zop/master/documentation/images/t-2v2.png">



## Sources

* <http://corpus.canterbury.ac.nz/descriptions/>