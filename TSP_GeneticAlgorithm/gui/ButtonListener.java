package gui;

import classsrc.TSPGenerator;
import classsrc.GA;
import classsrc.Path;
import classsrc.PathSolution;

import java.awt.event.*;
import java.io.IOException;


/**
 * A listener for buttons on the button panel in the GUI
 */
public class ButtonListener implements ActionListener {

    private CanvasPanel canvasPane;
    private ButtonPanel buttonPane;

    /**
     * Initializes each pane to allow for methods such as showing solution stats
     * to be used
     */
    public ButtonListener(ButtonPanel bp, CanvasPanel cP) {
        canvasPane = cP;
        buttonPane = bp;
    }

    @Override
    public void actionPerformed(ActionEvent evt){
        String actionCommand = evt.getActionCommand();

        switch (actionCommand) {
            case "Generate Points":
                PathSolution.clearAll();
                int generateAmount = 30;
                TSPGenerator TSPGenerator = new TSPGenerator();
                TSPGenerator.randomlyGenerateCities(generateAmount, 
                        canvasPane.getWidth(), canvasPane.getHeight());
                PathSolution.setSolved(false);
                buttonPane.hideStats();
                canvasPane.repaint();
                break;
            case "Solve TSP":
                double mutationRate = (1-Double.valueOf(ButtonPanel.crossoverInput.getText()));
                int populationEvolution = Integer.parseInt(ButtonPanel.evolutionInput.getText());
                
                
                boolean elitism = false;
                GA TSPGeneticAlgorithm = new GA();
                Path solution = null;
                // get the input value and all the generated paths in case "Generate points"
                int i = 10;
                for(i=0; i < 10;i++){
                	System.out.printf("\nThe %d time simulation:\n", i+1);
                solution = TSPGeneticAlgorithm.calculatePath(PathSolution.getAll(), 
	                        mutationRate, populationEvolution, elitism);
                PathSolution.setAll(solution.getAllInPath()); // Set current path to solution for repaint
                PathSolution.setSolved(true);
                
                buttonPane.showStats(solution);
                canvasPane.repaint();
                }
				
                break;
            case "Clear Points":
                PathSolution.clearAll();
                PathSolution.setSolved(false);
                buttonPane.hideStats();
                canvasPane.repaint();
                break;
            case "Exit Program":
                canvasPane.exitProgram();
                break;
        }

    }

}
