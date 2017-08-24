import java.io.*;
import java.util.regex.*;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class MedQAParser {
	public static void parser(String inFileName, String outFileName, String savePath) throws IOException {
		
		// create output file
		File saveDir = new File(savePath);
		if (!saveDir.exists()) {
			saveDir.mkdir();
		}
		String filename = savePath + File.separator + outFileName;
		FileOutputStream fos = new FileOutputStream(filename);
		
		// parse the html file using jsoup
		File file = new File(inFileName);
		Document doc = Jsoup.parse(file, "UTF-8", "http://www.120ask.com/");
		Elements quenames = doc.select("a.q-quename");
		Pattern p = Pattern.compile("\\s*|\t|\r|\n");
		for (Element quename : quenames) {
		      String question = quename.attr("title");
		      Matcher m = p.matcher(question);
		      question = m.replaceAll("");
		      question += "\n";
		      fos.write(question.getBytes());
		}
		
		if (fos != null) {
			fos.close();
		}
	}

	public static void main(String[] args) {
		try {
			parser("./med-qa.txt", "med-qa-clean.txt", "./");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
