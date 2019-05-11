# Week 1

### Progress

* Started documentation by creating a software requirements specification, timesheet and a readme
* Started the project itself
* Started file management class and created a method for reading files into byte arrays. Later started a process of replacing this to an implementation that streams the input file. This was done because decompressing very large files would not work with loading the file into memory.
* Started working on the Huffman's algorithm. Created methods for counting char occurrences, creating tree nodes and placing them into priority queue, creating Huffman's tree, creating table for encoded binary values.

This week some time was spent on researching different project possibilities. I ended up choosing a compression project. I researched on Huffman coding, LZ77 and Deflate a bit. A lot of it is still unclear but the goal is to learn on the go. I've started coding the I/O and Huffman's algorithm part.



### Challenges

The project did seem a bit daunting at first but after understanding more and more about the algorithms, it's getting easier. Honestly, the most challenging part this week was updating to Java 11, Netbeans 11 and learning Gradle. (Mostly getting them all work together was the challenging part)

### What's next?

The next step is to finish implementation of file input stream class and to keep working on Huffman's algorithm compression.