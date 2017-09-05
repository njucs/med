import java.io.*;
import java.util.regex.*;

public class DataTransform {
	private static void transfer(String in_file, String out_file) throws IOException {
		File file = new File(in_file);
		FileOutputStream fos = new FileOutputStream(out_file);

		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineText = null;
			while ((lineText = bufferedReader.readLine()) != null) {
				// 2419200000
			}
			read.close();
		}
		fos.close();
	}

	public static void main(String[] args) {
		try {
			transfer("./DB.xml", "./DB-transferred.xml");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
