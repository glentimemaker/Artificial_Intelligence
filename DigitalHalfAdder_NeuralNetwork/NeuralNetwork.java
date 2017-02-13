import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NeuralNetwork {
	private List<Neuron> inputNeurons;
	private List<Neuron> hiddenLayerNeurons;
	private List<Neuron> outputNeurons;
	
	public int inputCount;
	public int hiddenCount;
	public int outputCount;
	
	private List<List<Double>> weightInputHidden;	// weight from input node to hidden node
	private List<List<Double>> weightHiddenOutput;	// weight from hidden node to output node
	
	private List<List<Double>> trainData;
	private List<List<Double>> inputData;
	private List<List<Double>> outputData;
	
	//private double eta;	// learning rate
	private double totalErr;	// total error
	private double firstBatchError;
	private int numBatch = 0;	// number of epoch
	
	private double beta = 0.95;
	
	public NeuralNetwork(int inputCount, int hiddenCount, int outputCount) {
		this.inputCount = inputCount;
		this.hiddenCount = hiddenCount;
		this.outputCount = outputCount;
		//this.eta = eta;
		initWeight();
		initNeuron();
	}
	
	// initialize weight
	private void initWeight() {
		Random rnd = new Random();
		weightInputHidden = new ArrayList<List<Double>>();
		weightHiddenOutput = new ArrayList<List<Double>>();
		for(int i = 0; i < inputCount; i++) {
			List<Double> col = new ArrayList<>();
			for(int j = 0; j < hiddenCount; j++) {
				double w = rnd.nextDouble() * 2 - 1;	// weight: random value from -1 to 1
				col.add(w);
			}
			weightInputHidden.add(col);
		}
		for(int i = 0; i < hiddenCount; i++) {
			List<Double> col = new ArrayList<>();
			for(int j = 0; j < outputCount; j++) {
				double w = rnd.nextDouble() * 2 - 1;
				col.add(w);
			}
			weightHiddenOutput.add(col);
		}
	}
	
	// initialize neuron
	private void initNeuron() {
		inputNeurons = new ArrayList<Neuron>(inputCount);
		hiddenLayerNeurons = new ArrayList<Neuron>(hiddenCount);
		outputNeurons = new ArrayList<Neuron>(outputCount);
		
		for(int i = 0; i < inputCount; i++) {
			inputNeurons.add(new Neuron(Neuron.TYPE_INPUT));
		}
		for(int i = 0; i < hiddenCount; i++) {
			hiddenLayerNeurons.add(new Neuron(Neuron.TYPE_HIDDEN));
		}
		for(int i = 0; i < outputCount; i++) {
			outputNeurons.add(new Neuron(Neuron.TYPE_OUTPUT));
		}
	}
	
	public void setTrainData(List<List<Double>> data) {
		trainData = data;
		setInputData();
		setOutputData();
	}
	
	private void setInputData() {
		inputData = new ArrayList<>();
		for(int i = 0; i < trainData.size(); i++) {
			List<Double> temp = new ArrayList<>();
			for(int j = 0; j < inputCount; j++) {
				temp.add(trainData.get(i).get(j));
			}
			inputData.add(temp);
		}
	}
	
	private void setOutputData() {
		outputData = new ArrayList<>();
		for(int i = 0; i < trainData.size(); i++) {
			List<Double> temp = new ArrayList<>();
			for(int j = inputCount; j < trainData.get(0).size(); j++) {
				temp.add(trainData.get(i).get(j));
			}
			outputData.add(temp);
		}
	}
	
	private void forward(int num) {
		// input neurons
		for(int i = 0; i < inputCount; i++) {
			inputNeurons.get(i).input(inputData.get(num).get(i));
		}
		
		// hidden layer neurons
		for(int i = 0; i < hiddenCount; i++) {
			double val = 0.0;
			for(int j = 0; j < inputCount; j++) {
				val += inputNeurons.get(j).getValue() * weightInputHidden.get(j).get(i);
			}
			
			hiddenLayerNeurons.get(i).updateValue(val);
		}
		
		// output neurons
		for(int i = 0; i < outputCount; i++) {
			double val = 0.0;
			for(int j = 0; j < hiddenCount; j++) {
				val += hiddenLayerNeurons.get(j).getValue() * weightHiddenOutput.get(j).get(i);
			}
			outputNeurons.get(i).updateValue(val);
		}
	}
	
	private void backPropagation(int num, double eta) {
		for(int i = 0; i < hiddenCount; i++) {
			for(int j = 0; j < outputCount; j++) {
				double oldWeight = weightHiddenOutput.get(i).get(j);
			}
		}
		// output neurons error gradient
		double[] deltaOutput = new double[outputCount];
		double[] deltaHidden = new double[hiddenCount];
		for(int i = 0; i < outputCount; i++) {
			double outputValue = outputNeurons.get(i).getValue();
			deltaOutput[i] = outputValue * (1 - outputValue) * (outputData.get(num).get(i) - outputValue);
		}
		// hidden layer error gradient
		for(int i = 0; i < hiddenCount; i++) {
			double hiddenValue = hiddenLayerNeurons.get(i).getValue();
			for(int j = 0; j < outputCount; j++) {
				deltaHidden[i] += hiddenValue * (1- hiddenValue) * deltaOutput[j] * weightHiddenOutput.get(i).get(j);
			}
		}
		
		// update weight between hidden layer and output neurons
		double[][] deltaWeight1;
		deltaWeight1 = new double[hiddenCount][outputCount];
		for(int i = 0; i < hiddenCount; i++) {
			for(int j = 0; j < outputCount; j++) {
				
				deltaWeight1[i][j] = beta*deltaWeight1[i][j] + eta * deltaOutput[j] * hiddenLayerNeurons.get(i).getValue();
				
				double newWeight = weightHiddenOutput.get(i).get(j) + deltaWeight1[i][j];
				weightHiddenOutput.get(i).set(j, newWeight);
			}
		}
		
		// update weight between input layer and hidden layer
		double[][] deltaWeight2;
		deltaWeight2 = new double[inputCount][hiddenCount];
		for(int i = 0; i < inputCount; i++) {
			for(int j = 0; j < hiddenCount; j++) {
				
				deltaWeight2[i][j] = beta*deltaWeight2[i][j] + eta * deltaHidden[j] * inputNeurons.get(i).getValue();
				double newWeight = weightInputHidden.get(i).get(j) + deltaWeight2[i][j];
				weightInputHidden.get(i).set(j, newWeight);
			}
		}
		
	}
	
	public void train(double eta, double error) throws IOException {
		
		totalErr = Double.MAX_VALUE;
		PrintWriter out = new PrintWriter(new FileWriter("ErrorEpoch.txt"), true);
		while(totalErr >= error) {
			numBatch++;
			totalErr = 0;
			for(int i = 0; i < trainData.size(); i++) {
				forward(i);
				backPropagation(i, eta*100);
				// computer total error of the output neurons
				for(int j = 0; j < outputCount; j++) {
					totalErr += Math.pow(outputNeurons.get(j).getValue() - outputData.get(i).get(j), 2) ;
				}
				totalErr = totalErr/outputCount;
				if(numBatch > 10000 && totalErr < error) {
					break;
				}
				
			}
			
			if(numBatch == 1) {
				firstBatchError = totalErr;
			}
			
			double s=totalErr;
			out.println(s);
			
			//System.out.println("set error: " + error);
			//System.out.println("total error: " + totalErr);
		}
	}
	
	public void printWeights(boolean isInitial) {
		if(isInitial) {
			System.out.println("Initial weights:");
			System.out.println("(1) Between input and hidden layer:");
		} else {
			System.out.println("Final weights:");
			System.out.println("(1) Between input and hidden layer:");
		}
		for(int i = 0; i < inputCount; i++) {
			for(int j = 0; j < hiddenCount; j++) {
				System.out.print("" + weightInputHidden.get(i).get(j) + " ");
				if((j + 1) % hiddenCount == 0) {
					System.out.println();
				}
			}
		}
		
		if(isInitial) {
			System.out.println("(2) Between hidden layer and output layer:");
		} else {
			System.out.println("(2) Between hidden layer and output layer:");
		}
		for(int i = 0; i < hiddenCount; i++) {
			for(int j = 0; j < outputCount; j++) {
				System.out.print("" + weightHiddenOutput.get(i).get(j) + " ");
				if((j + 1) % outputCount == 0) {
					System.out.println();
				}
			}
		}
	}
	
	public double getError() {
		return totalErr;
	}
	
	public double getFirstBatchError() {
		return firstBatchError;
	}
	
	public int getTotalBatches() {
		return numBatch;
	}
	
	public void reset() {
		initWeight();
		initNeuron();
	}
	
	public void test() {
		System.out.printf("****Testing the final Neural Network**** \n");
		for(int i = 0; i < outputData.size(); i++) {
		forward(i);
		double[] data1;
		data1 = new double[inputCount];
		for(int j = 0; j < inputCount; j++) {
			data1[j]=trainData.get(i).get(j);
		}
		try{
		System.out.println("input = " + data1[0]+"  "+data1[1]);
		}catch(Exception e){
			System.err.println("ERROR:"+e.getMessage());
		}
		for(int j = 0; j < outputCount; j++) {
			System.out.println("output = " + Math.round(outputNeurons.get(j).getValue()));
			
		}
		System.out.println();
		}
		
//		for(int i = 0; i < outputData.size(); i++) {
//			forward(i);
//			for(int j = 0; j < outputCount; j++) {
//				System.out.println("output = " + outputNeurons.get(j).getValue());
//			}
//		}
	}
	
}
