package classsrc;

import java.util.ArrayList;
import java.util.Collections;

/**
 * The crossover method for the GA to get the offspring by two parents
 */
public class Crossover {

    /**
     * Returns the two children created from the two parents
     */
    public Path[] performCrossover(Path parent1, Path parent2) {
        Path[] children = new Path[2];
        
        children = orderedCrossover(parent1, parent2);
        
        return children;
    }
    
    /*
     * Performs ordered crossover on two parent chromosomes to create children.
     * Performs a two point crossover, and then checks if there are missing points
     * in the new children to avoid invalid paths. Rotates the child paths to ensure
     * the swapped cities remain in the same indexed position as when they started
    */
    private Path[] orderedCrossover(Path parent1, Path parent2) {
        int size = parent1.pathSize();
        int number1 = (int) (Math.random() * (size-1));
        int number2 = (int) (Math.random() * size);
        int start, end;
        // ensure random start is smaller than end
        if(number1 > number2){
            start = number2;
            end = number1;
        }
        else{
            start = number1;
            end = number2;
        }
        // get raw city list
        ArrayList<City> parent1Rep = parent1.getAllInPath();
        ArrayList<City> parent2Rep = parent2.getAllInPath();
        // copy parent paths into children paths, using start and end points only
        ArrayList<City> child1Rep = new ArrayList<>(size);
        ArrayList<City> child2Rep = new ArrayList<>(size);
        child1Rep.addAll(parent1Rep.subList(start, end));
        child2Rep.addAll(parent2Rep.subList(start, end));

        int currentCityIndex;
        City current1;
        City current2;
        for (int i = 0; i < size; i++) {
            // get the remainder from mutliples of the end point plus the nth run from the total path size
            currentCityIndex = (end + i) % size;
            // get the city at the current index in each of the two parent tours
            current1 = parent1.getCity(currentCityIndex);
            current2 = parent2.getCity(currentCityIndex);
            // if child 1 does not already contain the current city in tour 2, add it
            if (!child1Rep.contains(current2)) {
                child1Rep.add(current2);
            }
            // if child 2 does not already contain the current city in tour 1, add it
            if (!child2Rep.contains(current1)) {
                child2Rep.add(current1);
            }
        }
        Collections.rotate(child1Rep, start);
        Collections.rotate(child2Rep, start);
        Path child1 = new Path(child1Rep);
        Path child2 = new Path(child2Rep);
        return new Path[]{child1, child2};
    }
    
}
