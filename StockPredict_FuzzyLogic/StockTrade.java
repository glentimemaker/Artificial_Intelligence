import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
//Main function
public class StockTrade{
	static double basicMoney = 12000;

	public static void main(String[] args) throws IOException{ 
		
		
		double totalMoney = basicMoney;
		int tradeStockNum;
		double tradeMoney;
		int totalStockNum = 0;
		double previousPrice;
		double totalStockMoney = 0;
		double totalActiveMoney = basicMoney;
		double totalProfit;
		double previousProfit = 0;
		double dailyProfit = 0;
		
		PrintWriter out = new PrintWriter(new FileWriter("output.txt"), true);
		PrintWriter out2 = new PrintWriter(new FileWriter("output2.txt"), true);
		PrintWriter out3 = new PrintWriter(new FileWriter("output3.txt"), true);
		for (int i=1; i<=150;i++)
		{
			//Set the fuzzy set of Price
			Fuzzy priceVL = new Fuzzy(0.0,0.0,0.2,0.3);
			Fuzzy priceLO = new Fuzzy(0.2,0.35,0.35,0.5);
			Fuzzy priceMD = new Fuzzy(0.4,0.5,0.5,0.6);
			Fuzzy priceHI = new Fuzzy(0.5,0.65,0.65,0.8);
			Fuzzy priceVH = new Fuzzy(0.7,0.8,1,1);
			//Set the fuzzy set of MAD
			Fuzzy madN = new Fuzzy(0.0,0.0,0.3,0.4);
			Fuzzy madZ = new Fuzzy(0.3,0.5,0.5,0.7);
			Fuzzy madP = new Fuzzy(0.6,0.7,1,1);
			//Set the fuzzy set of Trade
			Fuzzy tradeSM = new Fuzzy(0.0,0.0,0.2,0.3);
			Fuzzy tradeSF = new Fuzzy(0.2,0.35,0.35,0.5);
			Fuzzy tradeDT = new Fuzzy(0.4,0.5,0.5,0.6);
			Fuzzy tradeBF = new Fuzzy(0.5,0.65,0.65,0.8);
			Fuzzy tradeBM = new Fuzzy(0.7,0.8,1,1);
			
			//Get today's price and MAD
			double todayPrice = 10+2.5*(Math.sin(2*(Math.PI)*i/19)) + 0.8*(Math.cos(2*3.1415*i/5)) + 12* (Math.round(Math.random())*2-1)*(i%2) ;
			if(todayPrice<2)
			{
				todayPrice = 2;
			}
			if(i==1) //Set the initial previousPrice
			{
				previousPrice = todayPrice;
			}
			double todayMad = 0.5*(Math.cos(0.3*i)) - (Math.sin(0.3*i)) + 0.4*(Math.round(Math.random())*2-1)*(i%3) ;
			System.out.printf("Day %d \n",i);
			System.out.printf("Current Price Of Each Stock:   %.3f\n",todayPrice);
			System.out.printf("Today's MAD:                   %.3f\n\n",todayMad);
			
			//Normalize today's price and MAD
			double maxPrice = 25, minPrice = 2;
			double maxMad = 1, minMad = -1;
			double normTodayPrice = Normalize(minPrice, maxPrice, todayPrice);
			double normTodayMad = Normalize(minMad, maxMad, todayMad);
			
			//Clip the Output fuzzy set by using the min value between price and mad.
			//And the rule of firing the trade method is on the base of common sense.
			tradeBM.Clip(Math.min(madP.FuzzyValue(normTodayMad), priceVL.FuzzyValue(normTodayPrice)));
			//System.out.printf("fuzzyvalue %f \n",tradeBM.FuzzyValue(normTodayMad));
			tradeBM.Clip(Math.min(madZ.FuzzyValue(normTodayMad), priceVL.FuzzyValue(normTodayPrice)));
			tradeBM.Clip(Math.min(madP.FuzzyValue(normTodayMad), priceLO.FuzzyValue(normTodayPrice)));
			tradeBF.Clip(Math.min(madZ.FuzzyValue(normTodayMad), priceLO.FuzzyValue(normTodayPrice)));
			tradeBF.Clip(Math.min(madP.FuzzyValue(normTodayMad), priceMD.FuzzyValue(normTodayPrice)));
			//System.out.printf("fuzzyvalue %f \n",tradeBF.c1);
			tradeBF.Clip(Math.min(madP.FuzzyValue(normTodayMad), priceHI.FuzzyValue(normTodayPrice)));
			tradeDT.Clip(Math.min(madN.FuzzyValue(normTodayMad), priceVL.FuzzyValue(normTodayPrice)));
			tradeDT.Clip(Math.min(madZ.FuzzyValue(normTodayMad), priceMD.FuzzyValue(normTodayPrice)));
			tradeDT.Clip(Math.min(madZ.FuzzyValue(normTodayMad), priceHI.FuzzyValue(normTodayPrice)));
			tradeSF.Clip(Math.min(madN.FuzzyValue(normTodayMad), priceLO.FuzzyValue(normTodayPrice)));
			tradeSF.Clip(Math.min(madN.FuzzyValue(normTodayMad), priceMD.FuzzyValue(normTodayPrice)));
			tradeSF.Clip(Math.min(madP.FuzzyValue(normTodayMad), priceVH.FuzzyValue(normTodayPrice)));
			tradeSM.Clip(Math.min(madN.FuzzyValue(normTodayMad), priceHI.FuzzyValue(normTodayPrice)));
			tradeSM.Clip(Math.min(madN.FuzzyValue(normTodayMad), priceVH.FuzzyValue(normTodayPrice)));
			tradeSM.Clip(Math.min(madZ.FuzzyValue(normTodayMad), priceVH.FuzzyValue(normTodayPrice)));
			
			
			//Defuzzify the Output
			double weight1 = 0+0.1+0.2;
			double weight2 = 0.35+0.45;
			double weight3 = 0.5+0.6;
			double weight4 = 0.65+0.75;
			double weight5 = 0.8+0.9+1;
			
			double COGUp = weight1*tradeSM.topFuzzyValue + weight2*tradeSF.topFuzzyValue + weight3*tradeDT.topFuzzyValue + weight4*tradeBF.topFuzzyValue + weight5*tradeBM.topFuzzyValue;
			double COGDown = 3*tradeSM.topFuzzyValue+2*tradeSF.topFuzzyValue+2*tradeDT.topFuzzyValue+2*tradeBF.topFuzzyValue+3*tradeBM.topFuzzyValue;
			double COG = COGUp/COGDown;
			
			//Calculate the trade money and total money
			tradeStockNum = (int) Math.floor(1600*COG-800);
			
			if(tradeStockNum < -totalStockNum) //If the trade number of stocks is beyond the total stock number we have 
			{
				tradeStockNum = -totalStockNum;
				
			}
			
			tradeMoney = todayPrice * tradeStockNum;
			totalActiveMoney = totalActiveMoney - tradeMoney; //The total money excluding the money in stocks
			totalStockMoney = todayPrice*(totalStockNum +tradeStockNum);
			totalMoney = totalActiveMoney + totalStockMoney;
			totalProfit = totalMoney - basicMoney;
			
			totalStockNum = totalStockNum + tradeStockNum;
			previousPrice = todayPrice;
			dailyProfit = totalProfit - previousProfit;
			previousProfit = totalProfit;
			
			System.out.printf("Trade Stock Number Suggestion: %d\n", tradeStockNum);
			System.out.printf("Trade Stock Money:             %.3f\n\n", (float)tradeMoney);
			
			System.out.printf("Current Stock Number:          %d\n", totalStockNum);
			System.out.printf("Total Stock Money:             %.3f\n", (float)totalStockMoney);
			System.out.printf("Total Active Money:            %.3f\n", (float)totalActiveMoney);
			System.out.printf("Daily Profit:                  %.3f\n", (float)dailyProfit);
			System.out.printf("Total Profit:                  %.3f\n", (float)totalProfit);
			System.out.printf("Total Money:                   %.3f\n", (float)totalMoney);
			
			
			System.out.println("----------------------------------------------");
			
			double s=dailyProfit;
		    out.println(s);
			double s2= totalProfit;
			out2.println(s2);
			double s3 = tradeStockNum;
			out3.println(s3);
		}

	}
	
	
	//Normalize function
	static double Normalize(double min, double max, double x)
	{
		if(x > max)
		{
			return 1;
		}
		else if(x < min)
		{
			return 0;
		}
		else
		{
			return ((x - min)/(max-min));
		}
	}
	
	//static double Defuzzy()
	//{
		
		
		//return tradeMoney;
		
	//}
	
	
	
}