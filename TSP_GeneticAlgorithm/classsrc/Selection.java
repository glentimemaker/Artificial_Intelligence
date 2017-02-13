/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classsrc;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Class used to select the parents path
 */
public class Selection{

    public Path performSelection(Population pop) {
        Path parent;
        
        parent = tournamentSelection(pop);
        
        return parent;
    }
    
    /**
     * Selects a tour from the population using tournament selection using 5
     * random paths from the current population
     */
    private Path tournamentSelection(Population pop) {
        int tournamentSize = 5;
        Population tournament = new Population(tournamentSize, true);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getPopulationSize());
            tournament.setPath(i, pop.getPath(randomId));
        }
        return tournament.getFittest();
    }

}
