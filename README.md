# Hadoop-SortWordLength: A MapReduce project to sort words by length using Apache Hadoop.

This is a Hadoop project that sorts words in a given text file based on their length. The project is designed to work on Hadoop Distributed File System (HDFS) and uses MapReduce programming model to perform distributed sorting.

## Installation:
To use this project, you need to have Hadoop installed and configured on your system. The project is written in Java and requires Java Development Kit (JDK) to compile the source code. Once you have Hadoop and JDK installed, you can download or clone the project from GitHub and compile the code using the following command:

      javac -classpath ${HADOOP_HOME}/hadoop-core.jar -d build SortWordLength.java

## Usage:
To use this project, you need to first copy the input file to HDFS using the following command:

      hadoop fs -copyFromLocal input.txt input

Replace input.txt with the name of your input file.

Next, you can run the sorting job using the following command:

      hadoop jar SortWordLength.jar SortWordLength input output
      
Replace SortWordLength.jar with the name of your compiled jar file, input with the name of your input directory on HDFS and output with the name of your output directory on HDFS.

Once the sorting job is completed, you can view the sorted output using the following command:

      hadoop fs -cat output/part-r-00000
      
## Contributing:
Contributions are welcome to this project. If you find a bug or have a feature request, please create a new issue on GitHub. Pull requests are also welcome.

License:
This project is licensed under the GNU Affero General Public License v3.0. See the LICENSE file for more details.
