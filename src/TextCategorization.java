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

	
  public static void generateModel() throws Exception{
	String processedData = "processed_data";
    Instances data = directoryToInstances(processedData);
    
    data = applyStringToWordVector(data);
    generateARFF(data, "arff_data");
    
    
    Evaluation eval = generateEvaluation(new J48(),data, 10);
    System.out.println(eval.toMatrixString());
    
    J48 classifier = new J48();
    classifier.buildClassifier(data);
    System.out.println("\n\nClassifier model:\n\n" + classifier);

    // serialize model
    ObjectOutputStream oos = new ObjectOutputStream(
                            new FileOutputStream("J48.model"));
    oos.writeObject(classifier);
    oos.flush();
    oos.close();
  }

  public static Instances directoryToInstances(String location) throws Exception{
    TextDirectoryLoader loader = new TextDirectoryLoader();
    loader.setDirectory(new File(location));
    Instances data = loader.getDataSet();
    return data;
    //System.out.println("\n\nImported data:\n\n" + dataRaw);
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

  public static void generateARFF(Instances data, String location)throws Exception{
    ArffSaver saver = new ArffSaver();
    saver.setInstances(data);
    saver.setFile(new File(location));
    System.out.println(location);
    //saver.setDestination(new File(location));   // **not** necessary in 3.5.4 and later
    
    saver.writeBatch();
  }

  //classifier.buildClassifier(data); build classifier
  public static Evaluation generateEvaluation(Classifier classifier, Instances data, int folds)throws Exception{
    Evaluation eval = new Evaluation(data);
    Random rand = new Random(1);  // using seed = 1
    eval.crossValidateModel(classifier, data, folds, rand);
    
    return eval;
    
  }
}
