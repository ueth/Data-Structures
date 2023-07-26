# Data Structures and Algorithms Repository

This repository is dedicated to the exploration and implementation of various data structures and algorithms, providing a practical understanding of fundamental concepts in computer science. The projects contained within are written in Java, demonstrating the application of these concepts in a widely-used, object-oriented programming language.

## Project 1: PLH_201_PROJECT_1

This project is a comprehensive study of various data structures and their associated algorithms. It includes implementations of core data structures, utility classes for node generation and sorting, and file management tools.

### Key Components

- `BinarySearch.java`: An efficient algorithm for finding an item from a sorted list of items.
- `Node.java`: The basic building block for many other data structures.
- `RandomNodeGenerator.java` and `SortedNodeGenerator.java`: Utilities for generating nodes in random or sorted order.
- `SortNodes.java`: A utility for sorting nodes.
- `FileManager.java`, `Operate.java`, `Tool.java`, `FMType.java`: Classes for file operations and other utilities.

## Project 2: PLH_201_PROJECT_2

This project delves into the world of binary trees, including standard binary trees and threaded binary trees. It also includes an interface for binary trees and various utility tools.

### Key Components

- `BinaryTree.java` and `ThreadedBinaryTree.java`: Implementations of binary tree data structures.
- `IBinaryTree.java`: An interface defining the contract for binary trees.
- `BinarySearch.java`: An implementation of the binary search algorithm.
- `Counter.java`, `Tool.java`, `UniqueRandomGenerator.java`: Utility classes for various operations.

## Getting Started

To run these projects, you need to have Java installed on your machine. Navigate to the directory of the project you want to run, compile the Java files using `javac`, and then run the main class using `java`.

## How to Run
Based on the code provided, here's how you can run the projects:

### Project 1: PLH_201_PROJECT_1

This project is a console-based application. The main class is `Operate.java` and the main menu is handled by the `Tool.java` class. 

To run this project:

1. Navigate to the `PLH_201_PROJECT_1` directory.
2. Compile the Java files using the command `javac -d bin src/**/*.java`.
3. Run the main class using the command `java -cp bin org.tuc.plh201.Operate`.

Once the application is running, you will see a menu with the following options:

1. Create and fill 4 new files TypeA TypeB TypeC TypeD
2. Open existing files
3. Print File names
4. Print number of pages
5. Print position
6. Search for 20 keys in random files
7. Search for 20 keys with binary search in sorted files
8. Close all files
9. Exit

You can select an option by entering the corresponding number.

### Project 2: PLH_201_PROJECT_2

This project is a library for binary trees. The main class is `BinaryTree.java`. 

To use this library:

1. Navigate to the `PLH_201_PROJECT_2` directory.
2. Compile the Java files using the command `javac -d bin com/tuc/bst/*.java`.
3. Create a new Java file that uses the `BinaryTree` class, compile it, and run it.
