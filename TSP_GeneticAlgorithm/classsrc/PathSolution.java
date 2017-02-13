package classsrc;

import java.util.ArrayList;
import java.util.Random;

/**
 * A management towards city and path set
 * Manage the cities in the current solution which is a path, making it available
 * to various classes for statistics collection
 */
public class PathSolution extends ArrayList<City> {

    private static ArrayList cities = new ArrayList<>();
    private static boolean isSolved = false;
    private static long runTime = 0;

    /**
     * Adds a given city to the solution path
     */
    public static void addCity(City city) {
        cities.add(city);
    }

    /**
     * Gets all the cities in the current solution
     */
    public static ArrayList getAll() {
        return cities;
    }

    /**
     * Sets all the cities in the current solution
     */
    public static void setAll(ArrayList<City> cityList) {
        cities = cityList;
    }

    /**
     * Gets a city in the path solution by index
     */
    public static City getCity(int index) {
        return (City) cities.get(index);
    }

    /**
     * Gets the size of the path
     */
    public static int getSize() {
        return cities.size();
    }

    /**
     * Clears the current path solution
     */
    public static void clearAll() {
        getAll().clear();
    }

    /**
     * Returns the flag signifying if the path is declared as solved
     */
    public static boolean isSolved() {
        return isSolved;
    }

    /**
     * Declares the path to be solved or not
     */
    public static void setSolved(boolean solved) {
        isSolved = solved;
    }
    
    /*
     * Method sets the completed run time for the solved solution
     */
    public static void setRunTime(long RT) {
        runTime = RT;
    }
    
    /*
     * Method gets the completed run time for the solved solution
     */
    public static long getRunTime() {
        return runTime;
    }
}
