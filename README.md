# MOWNIT
Numerical Analysis :


lab1 : 

- -sinus and cosinus series expansion by comparison with java.lang.Math.sin() and java.lang.Math.cos()
-Partial Sums method ( comparing double and float precision , summing forward and backward )
-Logistic Map , Bifurcation Diagram ( JavaPlot for results presentation), also comparison between double and float precision

 

lab2 :

- Gaussian-Jordan ellimination for solving linear equotatiions with complete pivoting compared to library solution in LibSolution.java
- LU factorization with partial pivoting
- Electric cuircuit analysis based on Kirchhoff's Rules. Given a graph representing an electrical cuircuit, solving a linear equation set for current on each branch in a cuircuit (represented as an edge in a graph). Implemented mainly in Java but using Python ( with matplotlib and networkx ) for visualization of the resolved graph representing cuircuit with color map to show particular current values.



lab4 :   
Simulated annealing algorithm in solving several practical problems : 

- Traveling Salesman Problem - drawing randomly distributed points ( normal distribution / uniform distribution / groups of seperated points) and then finding the optimal path through all the points with simulated annealing algorithm.
- Binary Image - drawing random black points and minimization of the energy with simulated annealing ( black points affect each other so minimizations of the energy leads them closer to each other in a specific way based on the neighbourhood type).
- Sudoku - solving Sudoku puzzle with simulated annealing ( minimization of digits repetitions in each row, column, 3x3 squares).


lab5 :
SVD (Python implementation )
- transformation of a sphere to an elipsoid with visalizations


lab6 :
Search 
- Web Crawler downloading some part of en.wikipedia.org/wiki (over 1000 txt files)
- Building a set of words appearing in all files (dictionary)
- Building bag-of-words vectors for every single file ( word occurences in the file)
- Building a term-by-document matrix of frequencies of each word from dictionary in each file 
- Calculating a corelation of the query vector to each file's bag-of-words ( implemented in python)
- Returning best fitting files to the query provided by user as argument 


lab8 :
Page Rank  (Python implementation )
- Page Rank calculation of large network graph ( example with one from Stanford Large Network Database)
- Solution based on eigenvectors and eigenvalues calculation

lab9 & lab10 :
DFT, FFT ( Fourier Transform application )
