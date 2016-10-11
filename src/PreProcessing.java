import java.util.*;
import java.io.*;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;



public class PreProcessing {
	
	
	
	public static void generateProcessedDataFromRawData(String base, String destination) throws Exception{
		
		String[] classes = {"pos","neg"};
		
		File f1 = new File(destination);
		f1.mkdir();
		
		File f2 = new File(destination + "/" + classes[0]);
		f2.mkdir();
		
		File f3 = new File(destination + "/" + classes[1]);
		f3.mkdir();

		generateBaseClass(base + "/" + classes[0], destination + "/" + classes[0] +"/");
		generateBaseClass(base + "/" + classes[1], destination + "/" + classes[1] + "/");
		
	}
		
	
	public static void generateBaseClass(String wekaClass, String destination) throws Exception{
		File[] htmlFiles = readFiles(wekaClass);

		for(int i=0;i<htmlFiles.length;i++){
			String htmlContent = fileToString(htmlFiles[i]);
			String textContent = discardHTMLTags(htmlContent);
			textContent = textContent.replaceAll("[^a-zA-Z]", " ");
			
			
			textContent = textContent.toLowerCase();
			
			stringToFile(textContent, destination + (i));
		}
	}

	public static void stringToFile(String content, String destination) throws Exception{
		File file = new File(destination);
		System.out.println(destination);
		file.createNewFile();

		PrintWriter writer = new PrintWriter(destination, "UTF-8");
		writer.println(content);
		writer.close();
	}

	public static String discardHTMLTags(String html){
		Document doc = Jsoup.parse(html);
		return doc.body().text();
	}

	public static File[] readFiles(String location) throws Exception{
		File folder = new File(location);
		File[] listOfFiles = folder.listFiles();

		return listOfFiles;
	}

	public static String fileToString(File file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();

		String str = new String(data, "UTF-8");
		return str;
	}


}
