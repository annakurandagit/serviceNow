#####Author: Anna Kuranda
#####ServiceNow home assignment

Groups together similar sentences (sentences where only a single word has
changed) and extracts the changes, then outputs them to a file in the following format:

01-01-2012 19:45:00 Naomi is getting into the car
02-01-2012 09:13:15 George is getting into the car
The changing word was: Naomi, George

#### How to run
Program Open console where user asked for input 
Console:
Please enter your input and output file : [input file full path,output file full path]
To stop the program print : [QUIT]

Example for user input:
d:/in.txt,d:/out.txt
##### By command line
java -jar annak-0.0.1-SNAPSHOT.jar
##### From void main
com.interview.annak.AnnakApplication
#### From Tests
com.interview.annak.AnnakApplicationTest

#### Running time O(n);
Analyzer read line by line K lines from input file .
For each word in line we create pattern ,used to group lines .File has N words
The running time is n = maximum between N,K

#### Possible improvements
Add more tests 
Improve code design to be more generic .
Support different io handlers.


