package gui;

import classsrc.City;
import classsrc.PathSolution;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Shows the map of cities.
 * Draws all the points in a path, and shows path solution
 */
public class CanvasPanel extends JPanel implements ActionListener {

    private StatusPanel statusPane;
    
    SpringLayout layout = new SpringLayout();
    private JSeparator statusSep = new JSeparator(JSeparator.VERTICAL);

    public CanvasPanel(StatusPanel sp) {
        SpringLayout layout = new SpringLayout();
        
        this.setBackground(Color.white);
        this.setPreferredSize(new Dimension(600, 600)); //Set size of panel
        this.statusPane = sp;
        
    }

    /**
     * Exits the program.
     */
    public void exitProgram() {
        int exit = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
        if (exit == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    
    public void displayCoords(int x, int y) {
        statusPane.setCoords(x, y);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    /**
     * Print the graphic
     */
    @Override
    public void paint(Graphics g) {
        if (g != null) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            ArrayList<City> cities = PathSolution.getAll();
            for (int i = 0; i < cities.size(); i++) {
                City city = cities.get(i);
                g2.setPaint(Color.black);
                g2.drawString(String.valueOf(city.getID()), city.getX(), city.getY());
                g2.drawString("[" +city.getX() + "," + city.getY() + "]", city.getX(), city.getY()+30);
                g2.setPaint(Color.blue);
                g2.fillOval(city.getX(), city.getY(), 15, 15);
                
                if (PathSolution.isSolved() && cities.size() > i + 1) {
                    City nextCity = cities.get(i + 1);
                    Line2D lin = new Line2D.Float(city.getX() + 7, city.getY() + 7, nextCity.getX() + 7, nextCity.getY() + 7);
                    g2.draw(lin);
                }
            }
            // then draw line back to starting point
            if(cities.size() > 1 && PathSolution.isSolved()){
                City lastCity = cities.get(cities.size()-1);
                City firstCity = cities.get(0);
                Line2D lin = new Line2D.Float(lastCity.getX() + 7, lastCity.getY() + 7, firstCity.getX() + 7, firstCity.getY() + 7);
                g2.draw(lin);
            }
        }    
    }

}
