# splitter

To split text files into separate files with integers, real numbers, and strings.<br/>

Valid arguments:
* `-s`  to print the number of elements in the output files; <br/>
* `-f`  to output the number of elements, as well as the sum, average, maximum, and minimum values ​​for numbers, and the lengths of the longest and shortest strings for text; <br/>
* `-a`  to write new results to existing data in files (otherwise overwriting will occur); <br/>
* `-o`  to specify the path for the output files; <br/>
* `-p`  to specify a prefix for the standard names of output files. <br/>
* Input file names are specified without arguments. <br/>

Example:  `java -jar splitter-1.0.jar ../../in2.txt in1.txt -f -o ../../ -p result-` <br/>
This allows (if possible) to find integers, floats and string lines from input files `in1.txt` and `in2.txt` located in differend folders, to write them in `../../result-(integers,floats,strings).txt` files and to print the full output statistics. <br/>

Java vesrion: 23 <br/>
Maven version: 3.4.1 <br/>

