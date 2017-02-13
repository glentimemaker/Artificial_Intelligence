/*
 *  Find the minimum of
 *  Function f(x1,x2)=(1-x1)^2 + 100*(x2-x1^2)^2  
 *  where -1.5 <= x1 <= 2, and -5.5 <= x2 <= 3
 */

public class ProblemSet {
	public static final double LOC_X1_LOW = -1.5;
	public static final double LOC_X1_HIGH = 2;
	public static final double LOC_X2_LOW = -5.5;
	public static final double LOC_X2_HIGH = 3;
	public static final double VEL_LOW = -3; //range of velocity 
	public static final double VEL_HIGH =3;
	
	                                                  
	public static double evaluate(Location location) {
		double result = 0;
		double x1 = location.getLoc()[0]; 
		double x2 = location.getLoc()[1]; 
		
		result = Math.pow(1-x1, 2) + 100* Math.pow(x2-Math.pow(x1,2), 2);
		
		return result;
	}
}
