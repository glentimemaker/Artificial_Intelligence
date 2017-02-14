## Traveling Salesperson Problem ##
Develop a program to solve the Traveling Salesperson Problem using genetic algorithms.

Here is a proposed solution:

1.	Let a chromosome represent the sequence of cities visited, i.e.  , with a gene representing a city, and n is the number of cities.  Assume that the start and end city is always C1.
2.	 Randomly generate N (= 8 to 12, your choice) initial chromosomes.
3.	 Choose either crossover or mutation genetic operator with a probability of p for crossover and (1-p) for mutation, where p is user defined.
4.	 Apply the genetic operators until the population size is 2N.
5.	 The distance between the city  and   is obtained from the formula  ,  .
6.	 The fitness proportion selection is used to keep the size of population equal to N.  That is after generating N new chromosomes, out of a total of 2N chromosomes N of them are selected for the next generation.
7.	 Continue with the evolution until fitness converges (i.e.  no appreciable change in the fitness is observed), or a preset number (say 400) generations are performed.
