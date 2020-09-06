# Introduction

Quality evaluation process behind this tool consists of the following steps:
1. Several dependency networks are generated based on the source code
2. Properties of these networks are calculated
3. Code metrics are calculated using only the network properties
4. With the user-defined reference values, and the actual metric values, each metric provides a metric grade from 0 to 1. These are quality grades based on each individual metric
5. User-defined quality property grades are calculated, as a weighted sum of metric grades
6. The final quality grade is calculated as the average value of quality property grades

## Dependency networks
Three types of networks are generated in the analysis process:

### Call graph
Represents the method calls in the entire system.  
Links are directed from the caller to the called method.

### Class structure graph
Collection of multiple disconnected components.  
Each component represents a single class, where the central node is the class and the peripheral nodes are class members.

### Type dependency graph
Represents the relationships between each class in the system.


## Metrics used

### Decoupling Impact (DI)
Measure of participation in decoupling two or more component groups.
Based on betweenness centrality in network theory, it represents the number of shortest dependency chain paths that pass through the observed class.
Decoupling impact is high for classes that represent a bridge between different software components.

### Interface Size (IS)  
Number of publicly available fields and methods.
This metric provides an overview of interface complexity.
Calculated as a degree of interface nodes in the type dependency network.

### Weighted Method Count (WMC)
Sum of cyclomatic complexities of all methods in the class.
Cyclomatic complexities is the total number of execution paths in a method.
Calculated as a weighted degree of a method node in the class structure network.

### Number Of Methods (NOM)
Number of all methods defined within the class.
Similar to interface size, but only methods count, no matter the access modifier.
Calculated as a degree of a class node in the type dependency network, by observing only the method definition branches.

### Response For Class (RFC)
Number of public methods in the class + number of methods called from the class.
This is the total number of methods that can be called using this class.
Calculated by using the call network and class structure network.

### Depth Of Inheritance Tree (DIT)
Measures the maximal inheritance depth in the system.
Calculated by finding the longest path in the inheritance tree,
which is a subgraph of the type dependency network.

### Number Of Implemented Interfaces (NII)
Number of interfaces that current class implements.
This is an adaptation of "Number of superclasses" metric, but since Java doesn't support multiple inheritance,
here only implementation is observed. Calculated by counting the implementation dependencies of the class node in the type dependency graph.

### Number Of Circularly Dependent Classes (NCDC)
Total number of classes involved in a circular dependency.
Calculated as the number of cycles in the type dependency network.

### Number Of Disconnected Groups (NDG)
Number of connected groups of classes that are disconnected form the rest of the system.
Calculated finding the set of disconnected groups in the type dependency network,
and counting the number of elements of that set.

### Coupling Between Objects (CBO)
Number of classes to which the observed class is coupled.
Classes are coupled if they interact by using each others variables, methods, etc.
Calculated by filtering out the inheritance and implementation dependencies
in the type dependency graph and counting the class node degree.

### Maximal Call Indirection (MCI)
Longest call path detected from static analysis.
Calculated as the longest path of the call network.

### Number Of Variable Fields (NOVF)
Number of all member variables in the class.
Calculated as the degree of the class node in the class
structure network, by observing only the field nodes.

### Tight Class Cohesion (TCC)
Number of method pairs that commonly use at least one filed of the class.
This is a measure of re-use of class fields, so high cohesion means
that class members are properly re-used by methods.
Calculated using the class structure network.

### Number Of Subclasses (NSUB)
Number of classes that inherit the current class.
Calculated by finding the number of incoming edges of the class note
containing the inheritance dependency in the type dependency network.

### Degree Of Class Interdepencency (DOI)
Measures the density of connections between all classes in the system.
This is the overall density of the type dependency network.
