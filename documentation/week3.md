# Week 3

[Hours spent: ~6,5h](<https://github.com/Darake/zip-zop/blob/master/documentation/timesheet.md#week-3>)

### Progress

* Start decompression by building Huffman tree out of topology
* Refactor JUnit tests and migrate to JUnit 5
* Got rid of io library imports from Huffman class and abstracting them into own io classes.

This week I didn't spend as much time as I wanted to on the project. I started the decompression phase by researching about the Huffman decompression and implementing a method for building a Huffman's tree out of the compressed file's topology. After that I got sidetracked and didn't like how my test classes looked. I migrated to JUnit 5 for nested test support and also basically refactored all the tests. In addition, I removed the io error throwing from Huffman class by catching them in my io package.



### What's next

Next week i'm gonna finish the Huffman decompression and start working on data structures.