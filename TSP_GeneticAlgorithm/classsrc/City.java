package classsrc;
/**
 * 
 * A class set
 * A node on the traveling salesman path. Holds the x and y coordinates of
 * the city, and the ID for identifying the position in a solution.
 */
public class City {

    private int x;
    private int y;
    private int id;

    /**
     * Constructs a new city with location x and y
     */
    public City(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = PathSolution.getSize()+1;
    }
    
    /**
     * Constructs a new city with location x and y
     */
    public City(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Returns the x coordinate
     */
    public int getX() {
        return this.x;
    }

    /**
     * Returns the y coordinate
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Returns the ID of the coordinate
     */
    public int getID() {
        return this.id;
    }

    /**
     * Calculates the distance between cities
     */
    public double getDistanceBetween(City city) {
    	int cityDistance = (this.getID()-city.getID())*(this.getID()-city.getID())+(int)Math.sqrt(this.getID()*city.getID());
        //int xDistance = Math.abs(this.x-city.getX());
        //int yDistance = Math.abs(this.y-city.getY());
    	//Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        return cityDistance;
        
    }
}
