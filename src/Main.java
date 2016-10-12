
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class Main {
	public static void main(String[] args) throws Exception{
				
		PreProcessing.generateProcessedDataFromRawData("html_data_100","raw_data_100");

		Instances data = TextCategorization.directoryToInstances("raw_data_100");
		data = TextCategorization.applyStringToWordVector(data);
		
	    TextCategorization.generateARFF(data, "data.arff");
	    
	    System.out.println("J48 model");
		J48 j48 = new J48();
		Evaluation eval1 = TextCategorization.generateEvaluation(data,j48, 0.7);
		TextCategorization.printEvaluation(eval1);
		
		System.out.println("SMO model");
		SMO smo = new SMO();
		Evaluation eval2 = TextCategorization.generateEvaluation(data,smo, 0.7);
		TextCategorization.printEvaluation(eval2);
		
		System.out.println("NaiveBayes model");
		NaiveBayes naiveBayes = new NaiveBayes();
		Evaluation eval3 = TextCategorization.generateEvaluation(data,naiveBayes, 0.7);
		TextCategorization.printEvaluation(eval3);
		
	}
	
	
	
	
}
