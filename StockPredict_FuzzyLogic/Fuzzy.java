//Fuzzy set
public class Fuzzy{
	double slop1;
	double slop2;
	double c1;
	double c2;
	double topValueX;
	double a;
	double b;
	double c;
	double d;
	double topFuzzyValue = 1.0;
	boolean slicing = false;
	double fuzzyValue = 0;
	
	public Fuzzy(double a, double b, double c, double d){

		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		//Left fuzzy area
		if(a==b){
			slop1 = 0;
			c1 = 1;
			slop2 = 1/(c-d);
			c2 = 1-c*slop2;
			topValueX = c;
			
		}
		//Right fuzzy area
		else if (c==d){
			slop1 = 1/(b-a);
			c1 = 1-b*slop1;
			slop2 = 0;
			c2 = 1;
			topValueX = b;
		}
		//Triangle fuzzy area
		else {
			slop1 = 1/(b-a);
			c1 = -a*slop1;
			slop2 = 1/(c-d);
			c2 = -d*slop2;
			topValueX = b;
		}
		
	}
	
	//Return the proportion of x in each fuzzy area
	double FuzzyValue(double x)
	{
		double result;
		if( x<this.a || x>this.d)
		{
			return 0.0;
		}
		else
		{
			if(x>=(this.a + 0.1) && (x <= (this.d - 0.1)))
			{
//				result = (slope2*x) + c2;
				return topFuzzyValue;
			}
			else if(x>=(this.a + 0.1))
			{
				result = (slop2 * x) + c2;
				return Math.min(result, topFuzzyValue);
			}
			else
			{
				result = (slop1 * x) + c1;
				return Math.min(result,topFuzzyValue);
			}
		
		
	}
		
		
	}
	
	double Clip(double x)
	{
		
		if(slicing == false)
		{
			topFuzzyValue = x;
			slicing = true;
		}
		else
		{
			if(topFuzzyValue > x)
			{
				
			}
			else
			{
				topFuzzyValue = x;
			}
		}
		return topFuzzyValue;
	}
		
}
	