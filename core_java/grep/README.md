# Introduction
This application implements a basic file search functionality based on a regular expression pattern, similar to the Linux grep command. It allows users to search for specific text patterns within files located in a given directory. The app uses core Java, including streams, file handling, regular expressions, and file I/O. The project also employs Docker to package and distribute the app for easier deployment. The development environment includes IntelliJ IDEA and Docker for containerization

# Quick Start
```bash
#Compile the mvn project and package the the program into a JAR file
mvn clean compile package

#Run the package program with three arguments [regex], [rootDir], [outFile]
java -cp target/grep-1.0-SNAPSHOT.jar [regex], [rootDir], [outFile]
#Example: java -cp target/grep-1.0-SNAPSHOT.jar .*Romeo.*Juliet.* ./data ./out/grep.txt

#View the output of the grep application from the arguments passed
cat [outFile]
```

#Implemenation
## Pseudocode
Below is the process methodthat highlights a high level structure for the entire application.
```java
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

## Performance Issue
The original implementation of the application relied on storing files and lines in an ArrayList during intermediate operations, which resulted in higher memory consumption due to the accumulation of all matched lines in memory. To optimize memory usage and improve efficiency, the app was refactored to leverage Java Streams. By utilizing Streams lazy evaluation, the program processes data on-demand, meaning intermediate results are not stored in memory, but rather computed and consumed as needed. This refactor significantly reduces memory overhead, making the application more lightweight and scalable.

# Test
Testing was conducted using the Java Unit Testing framework, with each method tested in isolation to verify the correct functionality of individual components and ensure the overall integrity of the application.

# Deployment
```bash
docker pull shahzabe/grep
docker run --rm -v `pwd`/data:/data -v `pwd`/log:/log shahzabe/grep .*Romeo.*Juliet.* /data /log/grep.out
```

# Improvement
List three things you can improve in this project.
