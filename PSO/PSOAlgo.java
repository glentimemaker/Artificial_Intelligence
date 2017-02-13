/*
 * Pso iteration process
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Vector;

public class PSOAlgo{
	// Set the constant values
	int SWARM_SIZE = 30;
	int MAX_ITERATION = 1000;
	int PROBLEM_DIMENSION = 2;
	double C1 = 2.0;
	double C2 = 2.0;
	double W_UPPERBOUND = 1.0;
	double W_LOWERBOUND = 0.0;
	double ERR_TOLERANCE = 1E-20; 
	
	private Vector<Particle> swarm = new Vector<Particle>();
	private double[] pBest = new double[SWARM_SIZE];
	private Vector<Location> pBestLocation = new Vector<Location>();
	private double gBest;
	private Location gBestLocation;
	private double[] fitnessValueList = new double[SWARM_SIZE];
	
	Random generator = new Random();
	long startTime = System.currentTimeMillis();
	//The execute part
	public void execute() throws IOException {
		//Initialize
		initializeSwarm();
		updateFitnessList();
		
		for(int i=0; i<SWARM_SIZE; i++) {
			pBest[i] = fitnessValueList[i];
			pBestLocation.add(swarm.get(i).getLocation());
		}
		

		PrintWriter out1 = new PrintWriter(new FileWriter("Epoch.txt"),true);
		PrintWriter out2 = new PrintWriter(new FileWriter("Error.txt"),true);
		int epoch = 0;
		double error = 0;
		
		int t = 0;
		double w;
		double err = 10000;
		
		while(t < MAX_ITERATION && err > ERR_TOLERANCE) {
			// step 1: update personal best(fitness decides)
			for(int i=0; i<SWARM_SIZE; i++) {
				if(fitnessValueList[i] < pBest[i]) {
					pBest[i] = fitnessValueList[i];
					pBestLocation.set(i, swarm.get(i).getLocation());
				}
			}
				
			// step 2: update global best
			int bestParticleIndex = getMinPos(fitnessValueList);
			if(t == 0 || fitnessValueList[bestParticleIndex] < gBest) {
				gBest = fitnessValueList[bestParticleIndex];
				gBestLocation = swarm.get(bestParticleIndex).getLocation();
			}
			
			w = W_UPPERBOUND - (((double) t) / MAX_ITERATION) * (W_UPPERBOUND - W_LOWERBOUND);
			
			for(int i=0; i<SWARM_SIZE; i++) {
				double r1 = generator.nextDouble();
				double r2 = generator.nextDouble();
				
				Particle p = swarm.get(i);
				
				// step 3: update velocity
				double[] newVel = new double[PROBLEM_DIMENSION];
				newVel[0] = (w * p.getVelocity().getPos()[0]) + 
							(r1 * C1) * (pBestLocation.get(i).getLoc()[0] - p.getLocation().getLoc()[0]) +
							(r2 * C2) * (gBestLocation.getLoc()[0] - p.getLocation().getLoc()[0]);
				newVel[1] = (w * p.getVelocity().getPos()[1]) + 
							(r1 * C1) * (pBestLocation.get(i).getLoc()[1] - p.getLocation().getLoc()[1]) +
							(r2 * C2) * (gBestLocation.getLoc()[1] - p.getLocation().getLoc()[1]);
				Velocity vel = new Velocity(newVel);
				p.setVelocity(vel);
				
				// step 4: update location
				double[] newLoc = new double[PROBLEM_DIMENSION];
				newLoc[0] = p.getLocation().getLoc()[0] + newVel[0];
				newLoc[1] = p.getLocation().getLoc()[1] + newVel[1];
				Location loc = new Location(newLoc);
				p.setLocation(loc);
			}
			
			err = ProblemSet.evaluate(gBestLocation) - 0; 
			
			// system print out
			System.out.println("Epoch " + t + ": ");
			System.out.println("gBest x1: " + gBestLocation.getLoc()[0]);
			System.out.println("gBest x2: " + gBestLocation.getLoc()[1]);
			System.out.println("Error: " + ProblemSet.evaluate(gBestLocation));
			System.out.println("------------------------------------------");
			epoch = t;
			error = ProblemSet.evaluate(gBestLocation);
			out1.println(epoch);
			out2.println(error);
			
			t++;
			updateFitnessList();
		}
		long endTime   = System.currentTimeMillis();
		double totalTime = endTime - startTime;
		
		System.out.println("Function: \nf(x1,x2)=(1-x1)^2 + 100*(x2-x1^2)^2\nwhere -1.5 <= x1 <= 2, and -5.5 <= x2 <= 3");		
		System.out.println("\nSolution: \nEpoch " + (t - 1));
		System.out.printf("Running Time:   %f s\n",totalTime/1000);
		System.out.println("Global Best x1: " + gBestLocation.getLoc()[0]);
		System.out.println("Global Best x2: " + gBestLocation.getLoc()[1]);
		
	}
	
	public void initializeSwarm() {
		Particle p;
		for(int i=0; i<SWARM_SIZE; i++) {
			p = new Particle();
			
			// randomly set the location inside the defined space
			double[] loc = new double[PROBLEM_DIMENSION];
			loc[0] = ProblemSet.LOC_X1_LOW + generator.nextDouble() * (ProblemSet.LOC_X1_HIGH - ProblemSet.LOC_X1_LOW);
			loc[1] = ProblemSet.LOC_X2_LOW + generator.nextDouble() * (ProblemSet.LOC_X2_HIGH - ProblemSet.LOC_X2_LOW);
			Location location = new Location(loc);
			
			// randomly set the velocity in the defined space
			double[] vel = new double[PROBLEM_DIMENSION];
			vel[0] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
			vel[1] = ProblemSet.VEL_LOW + generator.nextDouble() * (ProblemSet.VEL_HIGH - ProblemSet.VEL_LOW);
			Velocity velocity = new Velocity(vel);
			
			p.setLocation(location);
			p.setVelocity(velocity);
			swarm.add(p);
		}
	}
	
	public void updateFitnessList() {
		for(int i=0; i<SWARM_SIZE; i++) {
			fitnessValueList[i] = swarm.get(i).getFitnessValue();
		}
	}
	
	public int getMinPos(double[] list) {
		int pos = 0;
		double minValue = list[0];
		
		for(int i=0; i<list.length; i++) {
			if(list[i] < minValue) {
				pos = i;
				minValue = list[i];
			}
		}
		
		return pos;
	}
}
