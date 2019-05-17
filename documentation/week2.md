# Week 2

[Hours spent: ~6](<https://github.com/Darake/zip-zop/blob/master/documentation/timesheet.md#week-2>)

### Progress

* Created classes for input and output streams
* Created topology of Huffman's tree
* Finished the initial version of compression

I started this week with input/output and a way of creating the topology and other header information for compressed file. I finished the week with a working initial version of compression.



### Challenges & Questions

Getting everything to work together was a bit challenging this week. The challenges mostly arose on how to handle the header and data writing to the compressed file. What mostly bothered me this week had nothing to do with the algorithm itself. I wrote most of the methods in Huffman class as functional style. What i'm mostly wondering is, what's the best way to put it all together. At the moment I just kind go through the steps (methods) one by one in the main compress method:

``` var occurrenceStream = new ByteInputStream(filePath); ```

``` HashMap<Character, Integer> occurrences = getCharOccurrencesFromStream(occurrenceStream); ```

```PriorityQueue<TreeNode> treeForest = getHuffmanTreeForest(occurrences);```

```TreeNode root = getHuffmanTreeRoot(treeForest);```

 Would a better way be calling the functions inside the functions itself or something else?



Also i'll be changing the methods to private, since there isn't a reason for them to be public. Should private methods have a JavaDoc? If I remember right, in 'Ohjelmistotekniikka' -course only JavaDoc comments on public methods were required.



### What's next?

Next i'm gonna start working on decompression.



