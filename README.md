# Project 1: Deterministic Finite Automata

* **Authors**: Dalton Bilau Goncalves & Thomas Pengelly
* **Class**: CS361 Section 001
* **Semester**: Fall 2025

## Overview

This program models a deterministic finite automata (DFA).
Through the use of different classes and packages it imitates
the entirety of a DFA by including states, possible transtions
and allows the user to create its own DFA and test it.

## Reflection

The project was well structured. We thought this project was a good way to add a more concrete idea on how a DFA is made and how its connections can be applied inside an actual code. The biggest struggle we had at first was figuring out how to use the Map and use inner maps to store all the transitions, we had to do some research to better understand it well and read the class well before being able to implement it. There were no concepts that were not clear per say.

A technique that was used to make the code easy to debug and modify was working on pieces of code at a time, writing it down on a piece of paper to make sure it'd make sense, and document everything properly. We did not think there was much on the design process that would have to be changed, however, we though it would be nice, if we could go back in time, to try and work the logic better as a team so as to understand any bugs or errors that were created. Perhaps starting earlier 
as well is always a beneficial strategy. This was a great project overall, it was challenging, and ties into the work 
we have been doing in class perfectly.

## Compiling and Using 

### Prerequisite:

Connect to Boise State Onyx Server.

### Compile Tests:
From the project root:
```bash
[you@onyx]$ javac -cp .:/usr/share/java/junit.jar ./test/dfa/DFATest.java
```

### Run Tests:
```bash
[you@onyx]$ java -cp .:/usr/share/java/junit.jar:/usr/share/java/hamcrest/hamcrest.jar org.junit.runner.JUnitCore test.dfa.DFATest
```

### Generate Javadoc
```bash
javadoc -d doc -cp . fa.dfa
```

## Sources used

The following reference was used to help on the understanding of
how to work on a nested map to create the sigma variable.
https://howtodoinjava.com/java/collections/hashmap/java-nested-map/#:~:text=Creating%20Nested%20Map%20using%20Map,()%3B%20addressMap