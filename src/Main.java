import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

public class Main {
	public static void main(String[] args) throws Exception{
		
		
		PreProcessing.generateProcessedDataFromRawData("html_data_100","raw_data_100");

		
		Instances data = TextCategorization.directoryToInstances("raw_data_100");
		data = TextCategorization.applyStringToWordVector(data);
		
		Remove remove = new Remove();
		
		int[] ar = new int[2];
		ar[0] = 0;
		ar[1] = 1;
		remove.setAttributeIndicesArray(ar);
	    remove.setInvertSelection(true);
	    remove.setInputFormat(data);
		
	    Instances newData = Filter.useFilter(data, remove);
	    
	    System.out.println(newData.equalHeaders(data) + " loooooooooooooooooooooo l");
	    
	    TextCategorization.generateARFF(newData, "newdata");
	    TextCategorization.generateARFF(data, "olddata");
	    
		J48 j48 = new J48();
		Evaluation eval1 = TextCategorization.generateEvaluation(data,j48, 0.7);
		TextCategorization.printEvaluation(eval1);
		
		SMO smo = new SMO();
		Evaluation eval2 = TextCategorization.generateEvaluation(data,smo, 0.7);
		TextCategorization.printEvaluation(eval2);
		
	}
}
