package FlightTicketAppTest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.FileHandler;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;

public class FileHandlerTests {
	
	FileHandler fh = new FileHandler();
	
	private static String filepath = "E:\\Flight Ticket App\\Test";

	@Test
	public void TestCreateFileValid() throws IOException {
		Map<String, CSVWriter> files = fh.createOutputFiles(filepath);
		File f = new File(filepath+"\\FailedRecords.csv");
		File f1 = new File(filepath+"\\SuccessRecords.csv");
	    Assert.assertEquals(f.exists(),true);
	    Assert.assertEquals(f1.exists(),true);
	    Assert.assertEquals(files.containsKey("fail"),true);
	    Assert.assertEquals(files.containsKey("success"),true);
	}

	
	@Test
	public void TestEnterData() throws IOException, CsvValidationException {
		String filepath = "E:\\Flight Ticket App\\Test";
		Map<String, CSVWriter> files = fh.createOutputFiles(filepath);
		CSVWriter temp = files.get("success");
		JSONObject json = new JSONObject();
		json.put("a", "ABC");
		json.put("b", "EFG");
		json.put("c", "HIJ1234");
		JSONArray arr = new JSONArray();
		arr.put("A");
		arr.put("B");
		arr.put("C");
		fh.writeResult(temp, json, arr, "Success");
		try (BufferedReader reader = new BufferedReader(new FileReader(filepath+"\\SuccessRecords.csv"))) {
			String line = "";
			String[] check = new String[4];
			while ((line = reader.readLine()) != null) {
				check = line.split(",");
				Assert.assertEquals(check[0],"ABC");
				Assert.assertEquals(check[1],"EFG");
				Assert.assertEquals(check[2],"HIJ1234");
				Assert.assertEquals(check[3],"Success");
			}
		}
			temp.flush();
			temp.close();
	}

}
