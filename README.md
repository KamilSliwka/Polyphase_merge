# Polyphase merge

## Description
This project implements an external sorting algorithm designed to handle large datasets that do not fit entirely into RAM. It uses the **polyphase merge sort** method with three "tapes", represented as binary files, simulating realistic disk access patterns.

The program simulates memory access patterns as in real-world systems and accurately tracks the number of I/O operations during sorting. This allows for detailed analysis of I/O efficiency when working with data-intensive applications.

### Key Features
- Sorting of large data files using external memory simulation
- Implementation of the polyphase merge sort with three binary files (tapes)
- Simulation of realistic memory constraints and disk access
- Counting and comparison of theoretical vs. practical I/O operations




[Here](report.pdf) results are presented with detailed charts to visualize and compare theoretical expectations with actual performance.
## Table of contents 
- [How to use](#how-to-use)
    - [Arguments](#arguments)
    - [Example of usage](#example-of-usage)
- [Records](#records)
- [Experiment](#experiment)
## How to use
### Arguments
![menu](https://github.com/user-attachments/assets/3687e131-dc29-405a-b372-afb25c618daf)


| Argument | Description                                                                                  |
| ------ | -------------------------------------------------------------------------------------------- |
| `1`    | Sorts the records using the external sorting algorithm without displaying content of all tapes after every pahse. |
| `2`    | Sorts the records using the external sorting algorithm with displaying content of all tapes after every pahse.                            |
| `3`    | Allows the user to manually input records via keyboard.                                      |
| `4`    | Generates a set of random records and stores them for sorting.                               |
| `5`    | Exits the program.                                                                           |
### Example of usage

https://github.com/user-attachments/assets/989bce74-9a8e-4410-ae61-506e3d079b13




## Records

Each record consists of a pair of real numbers, interpreted as a point in 2D space.  
Records are sorted based on their Euclidean distance from the origin (0, 0).  
A point farther from the origin is considered greater.

The distance \( d \) from the origin for a point \( (x, y) \) is calculated as:

d = √(x² + y²)

**Example record:**
```md
4.1 7.3
```

Records are stored in binary files. The comparison rule is not stored, but dynamically calculated during sorting.

## Experiment
The project includes experiments designed to evaluate how changes in key parameters affect I/O performance. Tests were conducted on datasets of various sizes to analyze:

- The number of I/O operations required in theory vs. practice

- How polyphase merging behaves under different load conditions

- The efficiency and limitations of the algorithm in disk-bound environments
