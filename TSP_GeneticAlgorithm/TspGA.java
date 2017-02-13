/* main function */
import classsrc.City;
import classsrc.GA;
import classsrc.TSPGenerator;
import gui.TSPFrame;
import java.util.ArrayList;

/**
 * Starts the application by creating and showing the GUI frame.
 */
public class TspGA{

    /**
     * Run the program by GUI or command line
     */
    public static void main(String[] args) {
        // If user is using application via console
        if(args.length == 6){
            GA TSPSolver = new GA();
            TSPGenerator TSPReader = new TSPGenerator();
            ArrayList<City> cities = TSPReader.readCitiesCsv(args[0]);
            System.out.println(cities.get(10));
            // Send arguments
            TSPSolver.calculatePath(cities, Double.parseDouble(args[1]),
                Integer.parseInt(args[2]), Boolean.parseBoolean(args[3]));
        }
        // Else, run the GUI and allow entry there
        else{
            TSPFrame frame = new TSPFrame();
            frame.showIt();
        }
    }
}
