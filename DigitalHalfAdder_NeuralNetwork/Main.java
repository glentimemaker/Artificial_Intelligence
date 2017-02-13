import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * Main function
 */

public class Main {

	public static void main(String[] args) throws IOException {
		
		NeuralNetwork myNetwork = new NeuralNetwork(2, 6, 2);
		
		// Initialize data
		List<List<Double>> data = new ArrayList<List<Double>>();
		List<Double> input0 = new ArrayList<Double>();
		input0.add(0.1); input0.add(0.2); input0.add(0.0);input0.add(0.0);
		List<Double> input1 = new ArrayList<>();
		input1.add(0.0); input1.add(1.1); input1.add(1.0);input1.add(0.0);
		List<Double> input2 = new ArrayList<>();
		input2.add(1.2); input2.add(0.1); input2.add(1.0);input2.add(0.0);
		List<Double> input3 = new ArrayList<>();
		input3.add(1.1); input3.add(1.0); input3.add(0.0);input3.add(1.0);
		data.add(input0);
		data.add(input1);
		data.add(input2);
		data.add(input3);
		
		myNetwork.setTrainData(data);
		
		// Print out background conditions
		System.out.println("---------------------------------");
		System.out.println("   Nerual Network Structure");
		System.out.println(myNetwork.inputCount+" inputs"+"\n"+myNetwork.hiddenCount+" neurons in 1 hidden layer"+"\n"+myNetwork.outputCount+" outputs");
		System.out.println("---------------------------------");
		
		myNetwork.printWeights(true);
		System.out.println();
		
		// Train the NN, use activation function and back propagation algorithm
		myNetwork.train(0.5, 0.001);
		
		// Print out the result
		System.out.println("First Epoch error: " + myNetwork.getFirstBatchError());
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		myNetwork.printWeights(false);
		System.out.println();
		System.out.println("Final error: " + myNetwork.getError());
		System.out.println();
		System.out.println("Total Epoch: " + myNetwork.getTotalBatches());
		System.out.println("-------------------------------------------------------------------------------------------------------------------------");
		myNetwork.test();
		
	}

}
