package gui;

import java.awt.BorderLayout;
import java.awt.*;
import javax.swing.JFrame;


/**
 * Creates the main GUI frame.
 * Creates the frame and adds each of the different panels to it
 */
public class TSPFrame extends JFrame {

    private ButtonPanel btPanel;
    private StatusPanel stPanel;
    private CanvasPanel cvPanel;

    /**
     * Creates a new window, and initializes each panel before adding them to it.
     * Sets the size of the window, and the location.
     */
    public TSPFrame() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("TSP Genetic Algorithm");

        stPanel = new StatusPanel();
        stPanel.setPreferredSize(new Dimension(this.getWidth(), 2));

        cvPanel = new CanvasPanel(stPanel);

        btPanel = new ButtonPanel(cvPanel);
        btPanel.setPreferredSize(new Dimension(175, this.getHeight()));

        add(btPanel, BorderLayout.EAST);
        add(cvPanel, BorderLayout.CENTER);
        add(stPanel, BorderLayout.SOUTH);

        pack();

        
    }

    /**
     * Opens the generated frame in a new window
     */
    public void showIt() {
        this.setVisible(true);
    }

}
