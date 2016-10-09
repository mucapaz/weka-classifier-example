
public class Main {
	public static void main(String[] args) throws Exception{
		PreProcessing.generateProcessedDataFromRawData();
		TextCategorization.generateModel();
	}
}
