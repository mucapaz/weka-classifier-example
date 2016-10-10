import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class Main {
	public static void main(String[] args) throws Exception{
		
		
		PreProcessing.generateProcessedDataFromRawData("html_data_100","raw_data_100");

		Instances data = TextCategorization.directoryToInstances("raw_data_100");
	
		J48 classifier = new J48();
		Evaluation eval = TextCategorization.generateEvaluation(data,classifier, 0.7);
		TextCategorization.printEvaluation(eval);
		
	}
}
