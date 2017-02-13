package classsrc;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * The Genetic algorithm for solving the Traveling salesman problem. Selects
 * chromosomes, evolves and mutates them to find the best possible solution
 */
public class GA {

    /**
     * Takes an array of cities to be visited and generates a solution to the
     * traveling salesman problem
     */
    public Path calculatePath(ArrayList<City> cities, double mutation,
            int populationEvolution, boolean elitsim) {

        Population pop = new Population(cities.size(), false);
        int initialDistance = pop.getFittest().getDistance();
        System.out.println("Initial distance: " + initialDistance);
        ArrayList<Path> bestEvolvedPaths = new ArrayList<>();
        ArrayList<Path> averageEvolvedPaths = new ArrayList<>();
        bestEvolvedPaths.add(pop.getFittest());
        averageEvolvedPaths.add(pop.getAverage());

        long start = System.nanoTime();
        
        // Start the evolutions, using parameters selected
        pop = evolvePopulation(pop, elitsim, mutation);
        for (int i = 0; i < populationEvolution; i++) {
            pop = evolvePopulation(pop, elitsim, mutation);
            bestEvolvedPaths.add(pop.getFittest());
            averageEvolvedPaths.add(pop.getAverage());
            //System.out.printf("fittest:%s" , bestEvolvedPaths.get());
        }
        /*
        try {
            FileOutputStream fos = new FileOutputStream("output");
            ObjectOutputStream oos = new ObjectOutputStream(fos);   
            oos.writeObject(bestEvolvedPaths); // write MenuArray to ObjectOutputStream
            oos.close(); 
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        */
        
        Path solution = pop.getFittest();

        // Print final results
        long end = System.nanoTime();
        System.out.println("Took: " + ((end - start) / 1000000) + "ms");
        System.out.println("Finished");
        System.out.println("Final distance: " + solution.getDistance());
       
        // Export solution to csv
        TSPGenerator TSPGenerator = new TSPGenerator();
        TSPGenerator.generateCitiesCsv("output.csv", solution.getAllInPath());
        
        solution.setInitialDistance(initialDistance);
        PathSolution.setRunTime((end - start) / 1000000);
        return solution;
    }

    /**
     * Evolves a population by selecting two parent chromosomes, creating a
     * child and then mutating it based on options passed
     */
    private Population evolvePopulation(Population pop, boolean elitism,
            double mutationRate) {
        Population newPopulation = new Population(pop.getPopulationSize(), true);

        Selection selectionTool = new Selection();
        Crossover crossoverTool = new Crossover();
        
        int offset = (elitism) ? 1 : 0;
        if (elitism) {
            newPopulation.setPath(0, pop.getFittest());
        }

        Path parent1, parent2;
        for (int i = offset; i < newPopulation.getPopulationSize();) {
            // perform selection
            parent1 = selectionTool.performSelection(pop);
            parent2 = selectionTool.performSelection(pop);
            
            if (parent1 != null && parent2 != null) {
                // perform crossover
                Path[] children = crossoverTool.performCrossover(parent1, parent2);
                
                newPopulation.setPath(i++, children[0]);
                if (i < newPopulation.getPopulationSize()) {
                    newPopulation.setPath(i++, children[1]);
                }
            }
        }
        // mutate population
        for (int i = offset; i < newPopulation.getPopulationSize(); i++) {
            mutate(newPopulation.getPath(i), mutationRate);
        }
        return newPopulation;
    }
    
    /*
     * Performs swap mutation on a path
    */
    private void mutate(Path path, double mutationRate) {
        for (int pathPos1 = 0; pathPos1 < path.pathSize(); pathPos1++) {
            // if mutation probability is higher than a random number, then mutate
            if (Math.random() < mutationRate) {
            	// Get a second random position in the tour
                int pathPos2 = (int) (path.pathSize() * Math.random());
                // Get the cities at target position in tour
                City city1 = path.getCity(pathPos1);
                City city2 = path.getCity(pathPos2);
                // Swap them around
                path.setCity(pathPos2, city1);
                path.setCity(pathPos1, city2);
            }
        }
    }
}
