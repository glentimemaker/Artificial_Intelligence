package gui;

import javax.swing.*;
import classsrc.Path;
import classsrc.PathSolution;
import java.awt.Color;
import java.awt.event.ActionEvent;


/**
 * Provides the buttons and options for the GA
 */

public class ButtonPanel extends JPanel {

    private JLabel genLabel = new JLabel("---- GA Options ----");
    private JLabel crossoverLabel = new JLabel("Crossover Rate");
    private JLabel generateAmountLabel = new JLabel("City Number: 30");
    private JLabel simulationTimeLabel = new JLabel("Simulation Times: 10");
    private JLabel evolutionLabel = new JLabel("Generation Number:");
    private JButton generateButton, clearButton, solveButton;
    

    public static JTextField crossoverInput = new JTextField("0.9");
    public static JTextField evolutionInput = new JTextField("1000");
    
    
    private JSeparator titleSep = new JSeparator(JSeparator.HORIZONTAL);
    private JSeparator topSep = new JSeparator(JSeparator.HORIZONTAL);
    private JSeparator bottom = new JSeparator(JSeparator.HORIZONTAL);
    
    //output
    private JLabel totalLabel = new JLabel("");
    private JLabel initialLabel = new JLabel("");
    private JLabel runTimeLabel = new JLabel("");

    /**
     * Creates a new Button panel to hold user options for the GA. Adds a listener
     * to each button or option on the panel
     */
    public ButtonPanel(CanvasPanel cP) {

        //Initialises action listener for button components on panel
        ButtonListener buttonList = new ButtonListener(this, cP);

        //Set new layout for  panel
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        //Add visual components to panel
        this.add(genLabel);
        this.add(titleSep);
        this.add(topSep);
        this.add(bottom);

        //Set EAST border of panel 
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.black));

        this.add(generateAmountLabel);
        this.add(simulationTimeLabel);
        
        generateButton = new JButton("Generate Points");
        this.add(generateButton);
        generateButton.addActionListener(buttonList);

        clearButton = new JButton("Clear Points");
        this.add(clearButton);
        clearButton.addActionListener(buttonList);
        
        this.add(crossoverLabel);
        this.add(crossoverInput);
        this.add(evolutionLabel);
        this.add(evolutionInput);
        
        solveButton = new JButton("Solve TSP");
        this.add(solveButton);
        solveButton.addActionListener(buttonList);
        
        this.add(runTimeLabel);
        
        this.add(totalLabel);
        
        this.add(initialLabel);
        
        

        layout.putConstraint(SpringLayout.NORTH, topSep, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST, topSep, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, topSep, -5, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, genLabel, 10, SpringLayout.NORTH, topSep);
        layout.putConstraint(SpringLayout.WEST, genLabel, 30, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, genLabel, -30, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, titleSep, 30, SpringLayout.NORTH, genLabel);
        layout.putConstraint(SpringLayout.WEST, titleSep, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, titleSep, -5, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, generateAmountLabel, 15, SpringLayout.NORTH, titleSep);
        layout.putConstraint(SpringLayout.WEST, generateAmountLabel, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, generateAmountLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, simulationTimeLabel, 15, SpringLayout.NORTH, generateAmountLabel);
        layout.putConstraint(SpringLayout.WEST, simulationTimeLabel, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, simulationTimeLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, generateButton, 30, SpringLayout.NORTH, simulationTimeLabel);
        layout.putConstraint(SpringLayout.WEST, generateButton, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, generateButton, -12, SpringLayout.EAST, this);

        layout.putConstraint(SpringLayout.NORTH, clearButton, 40, SpringLayout.NORTH, generateButton);
        layout.putConstraint(SpringLayout.WEST, clearButton, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, clearButton, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, bottom, 40, SpringLayout.NORTH, clearButton);
        layout.putConstraint(SpringLayout.WEST, bottom, 5, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, bottom, -5, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, crossoverLabel, 40, SpringLayout.NORTH, bottom);
        layout.putConstraint(SpringLayout.WEST, crossoverLabel, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, crossoverLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, crossoverInput, 20, SpringLayout.NORTH, crossoverLabel);
        layout.putConstraint(SpringLayout.WEST, crossoverInput, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, crossoverInput, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, evolutionLabel, 40, SpringLayout.NORTH, crossoverInput);
        layout.putConstraint(SpringLayout.WEST, evolutionLabel, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, evolutionLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, evolutionInput, 20, SpringLayout.NORTH, evolutionLabel);
        layout.putConstraint(SpringLayout.WEST, evolutionInput, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, evolutionInput, -12, SpringLayout.EAST, this);
        
        
        layout.putConstraint(SpringLayout.NORTH, solveButton, 40, SpringLayout.NORTH, evolutionInput);
        layout.putConstraint(SpringLayout.WEST, solveButton, 12, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, solveButton, -12, SpringLayout.EAST, this);
     
        
        layout.putConstraint(SpringLayout.NORTH, runTimeLabel, 80, SpringLayout.NORTH, solveButton);
        layout.putConstraint(SpringLayout.WEST, runTimeLabel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, runTimeLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, initialLabel, 40, SpringLayout.NORTH, runTimeLabel);
        layout.putConstraint(SpringLayout.WEST, initialLabel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, initialLabel, -12, SpringLayout.EAST, this);
        
        layout.putConstraint(SpringLayout.NORTH, totalLabel, 40, SpringLayout.NORTH, initialLabel);
        layout.putConstraint(SpringLayout.WEST, totalLabel, 15, SpringLayout.WEST, this);
        layout.putConstraint(SpringLayout.EAST, totalLabel, -12, SpringLayout.EAST, this);

    }
   
    /**
     * Shows the text on the canvas panel.
     */
    public void showStats(Path path) {
        runTimeLabel.setText("Run Time: " + PathSolution.getRunTime() + "ms");
        totalLabel.setText("Solution Distance: " + path.getDistance());
        initialLabel.setText("Initial Distance: " + path.getInitialDistance());
    }
    
    /**
     * Removes the text on the canvas panel.
     */
    public void hideStats(){
        runTimeLabel.setText("");
        totalLabel.setText("");
        initialLabel.setText("");
     }
}
