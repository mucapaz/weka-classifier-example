import weka.core.*;
import weka.core.converters.*;
import weka.classifiers.trees.*;
import weka.filters.*;
import weka.filters.unsupervised.attribute.*;
import weka.classifiers.*;
import weka.core.stemmers.*;

import java.util.Random;
import java.io.*;


public class TextCategorization {

	public static Evaluation generateEvaluation(Instances data, Classifier classifier, double trainPercent) throws Exception{

		data = applyStringToWordVector(data);

		data.randomize(new Random(0));

		int trainSize = (int) Math.round(data.numInstances() * trainPercent);
		int testSize = data.numInstances() - trainSize;

		Instances train = new Instances(data, 0, trainSize);
		Instances test = new Instances(data, trainSize, testSize);
		
		System.out.println("train instances");
		System.out.println(train.size());
		
		System.out.println("test instances");
		System.out.println(test.size());
		
		
		
		classifier.buildClassifier(train);	

		return generateEvaluation(classifier,test);
	}

	public static void printEvaluation(Evaluation eval) throws Exception{
		System.out.println(eval.toMatrixString());
		System.out.println(eval.toClassDetailsString());	
	}


	public static void generateARFF(Instances data, String destination) throws Exception{
		data = applyStringToWordVector(data);
		generateARFF(data, destination);
		
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(destination));

		saver.writeBatch();
	}

	public static Classifier generateModel(Instances data, Classifier classifier) throws Exception{
		data = applyStringToWordVector(data);

		classifier.buildClassifier(data);
		
		return classifier;
	}

	public static void serializeClassifier(String destination, Classifier classifier) throws FileNotFoundException, IOException{
		ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream(destination));
		oos.writeObject(classifier);
		oos.flush();
		oos.close();
	}

	public static Instances directoryToInstances(String location) throws Exception{
		TextDirectoryLoader loader = new TextDirectoryLoader();
		loader.setDirectory(new File(location));
		Instances data = loader.getDataSet();
		return data;
	}

	public static Instances applyStringToWordVector(Instances data, Stemmer stem) throws Exception{
		StringToWordVector filter = new StringToWordVector();
		filter.setInputFormat(data);
		filter.setStemmer(stem);

		Instances dataFiltered = Filter.useFilter(data, filter);
		return dataFiltered;
	}

	public static Instances applyStringToWordVector(Instances data)throws Exception{
		StringToWordVector filter = new StringToWordVector();
		filter.setInputFormat(data);

		Instances dataFiltered = Filter.useFilter(data, filter);
		return dataFiltered;
	}

	public static Evaluation generateEvaluation(Classifier classifier, Instances data)throws Exception{
		Evaluation eval = new Evaluation(data);
		eval.evaluateModel(classifier, data);
		return eval;
	}
}
