import java.io.*;
import java.util.regex.*;

public class DataTransform {
	private static void transfer(String in_file, String out_file) throws IOException {
		File file = new File(in_file);
		
		if (file.isFile() && file.exists()) {
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(read);
			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(out_file), "UTF-8");
			BufferedWriter bufferedWriter = new BufferedWriter(write);
			
			String lineText = null;
			
			/*
			   current_place_log
			   <row>
                <value column="0">90</value>
                <value column="1">NEAR_WORK</value>
                <value column="2">ANALYZED_PLACE</value>
                <value column="3">7</value>
                <value column="4">1501589683000</value>
                <value column="5">2017/08/01 21:14:43</value>
                <value column="6">Asia/Seoul</value>
                <value column="7">1</value>
                <value column="8">1502203501000</value>
               </row>
               
               location_log
               <row>
                <value column="0">1</value>
                <value column="1">37.257353</value>
                <value column="2">127.053084</value>
                <value column="3">0</value>
                <value column="4">passive GPS</value>
                <value column="5">0</value>
                <value column="6">0</value>
                <value column="7">0</value>
                <value column="8">1501589683806</value>
                <value column="9">2017/08/01 21:14:43</value>
                <value column="10">Asia/Seoul</value>
                <value column="11">1501589683000</value>
               </row>
               
               sleep_time_stats
               <row>
                <value column="0">1</value>
                <value column="1">1501602239000</value>
                <value column="2">2017/08/02 00:43:59</value>
                <value column="3">1501625522000</value>
                <value column="4">2017/08/02 07:12:02</value>
                <value column="5">Asia/Seoul</value>
                <value column="6">0</value>
                <value column="7">1501629122000</value>
                <value column="8">1501629122000</value>
               </row>
			 */
//			Pattern timestamp = Pattern.compile("[0-9]{13}");
//			Pattern date = Pattern.compile("2017/[0-9]{2}/[0-9]{2}\\s[0-9]{2}:[0-9]{2}:[0-9]{2}");
//			Pattern latlon = Pattern.compile("[0-9]+.[0-9]+");
			
			while ((lineText = bufferedReader.readLine()) != null) {
				bufferedWriter.write(lineText + "\n");
				if(lineText.contains("<name>sleep_time_stats</name>"))
				{
					while ((lineText = bufferedReader.readLine()) != null)
					{
						if(lineText.contains("</rows>"))
						{
							bufferedWriter.write(lineText + "\n");
							break;
						}
						else if(lineText.contains("<value column=\"1\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"1\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"3\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"3\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"7\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"7\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"8\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"8\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"2\">") || lineText.contains("<value column=\"4\">"))
						{
							// just for Aug -> Sep
							int idx = lineText.indexOf("/");
							String day = lineText.substring(idx + 4, idx + 6);
							int c_day = Integer.parseInt(day) + 28;
							String month = c_day > 31 ? "09" : "08";
							day = String.format("%02", ((c_day - 1) % 31) + 1); 
							bufferedWriter.write(lineText.substring(0, idx + 1) + month + "/" + day + lineText.substring(idx + 6) + "\n");
						}
						else
						{
							bufferedWriter.write(lineText + "\n");
						}
					}
				}
				else if(lineText.contains("<name>current_place_log</name>"))
				{
					while ((lineText = bufferedReader.readLine()) != null)
					{
						if(lineText.contains("</rows>"))
						{
							bufferedWriter.write(lineText + "\n");
							break;
						}
						else if(lineText.contains("<value column=\"4\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"4\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"8\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"8\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"5\">"))
						{
							// just for Aug -> Sep
							int idx = lineText.indexOf("/");
							String day = lineText.substring(idx + 4, idx + 6);
							int c_day = Integer.parseInt(day) + 28;
							String month = c_day > 31 ? "09" : "08";
							day = String.format("%02", ((c_day - 1) % 31) + 1); 
							bufferedWriter.write(lineText.substring(0, idx + 1) + month + "/" + day + lineText.substring(idx + 6) + "\n");
						}
						else
						{
							bufferedWriter.write(lineText + "\n");
						}
					}
				}
				else if(lineText.contains("<name>location_log</name>"))
				{
					while ((lineText = bufferedReader.readLine()) != null)
					{
						if(lineText.contains("</rows>"))
						{
							bufferedWriter.write(lineText + "\n");
							break;
						}
						else if(lineText.contains("<value column=\"11\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"11\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"8\">"))
						{
							String time = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_time = Long.toString(Long.parseLong(time) + Long.parseLong("2419200000"));
							bufferedWriter.write("<value column=\"8\">" + c_time + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"9\">"))
						{
							// just for Aug -> Sep
							int idx = lineText.indexOf("/");
							String day = lineText.substring(idx + 4, idx + 6);
							int c_day = Integer.parseInt(day) + 28;
							String month = c_day > 31 ? "09" : "08";
							day = String.format("%02", ((c_day - 1)%31) + 1); 
							bufferedWriter.write(lineText.substring(0, idx + 1) + month + "/" + day + lineText.substring(idx + 6) + "\n");
						}
						else if(lineText.contains("<value column=\"1\">"))
						{
							//<value column="1">37.257353</value>
							// -5.280421
							String lat = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_lat = Double.toString(Double.parseDouble(lat) - 5.280421);
							bufferedWriter.write("<value column=\"1\">" + c_lat + "</value>" + "\n");
						}
						else if(lineText.contains("<value column=\"2\">"))
						{
							//<value column="2">127.053084</value>
							// -8.281265
							String lon = lineText.substring(lineText.indexOf(">") + 1, lineText.lastIndexOf("<"));
							String c_lon = Double.toString(Double.parseDouble(lon) - 8.281265);
							bufferedWriter.write("<value column=\"1\">" + c_lon + "</value>" + "\n");
						}
						else
						{
							bufferedWriter.write(lineText + "\n");
						}
					}
				}
			}
			bufferedWriter.close();
			read.close();
			write.close();
		}
		else
		{
			System.err.println("File not found!");
			return;
		}
	}

	public static void main(String[] args) {
		try {
			transfer("./DB.xml", "./DB-transferred.xml");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
