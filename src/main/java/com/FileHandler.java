package com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Interface.IFileHandler;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.Part;

public class FileHandler implements IFileHandler {
	
	//Will create two files to store successful and failed users
	public Map<String, CSVWriter> createOutputFiles(String filepath) throws IOException {
		// TODO Auto-generated method stub
		Path path = Paths.get(filepath);
		Files.createDirectories(path);
		File f = new File(filepath+"\\FailedRecords.csv");
    	if (f.createNewFile()) {
            System.out.println("File created: " + f.getName());
          } else {
            System.out.println("File already exists.");
          }
		CSVWriter failed_records = new CSVWriter(new FileWriter(filepath+"\\FailedRecords.csv"));
		

		File f1 = new File(filepath+"\\SuccessRecords.csv");
    	if (f1.createNewFile()) {
            System.out.println("File created: " + f1.getName());
          } else {
            System.out.println("File already exists.");
          }
		CSVWriter success_records = new CSVWriter(new FileWriter(filepath+"\\SuccessRecords.csv"));
		Map<String,CSVWriter> files = new HashMap<>();
		files.put("fail", failed_records);
		files.put("success", success_records);
		return files;
	}
	

	//Write the users data to Success (or) Failed users file
	public void writeResult(CSVWriter file, JSONObject user,JSONArray columns, String message) {
		// TODO Auto-generated method stub
		String[] row = new String[columns.length()+1];
		for(int i=0;i<columns.length();i++) {
			row[i] = user.get(columns.get(i).toString().toLowerCase()).toString();
		}
		row[row.length-1] = message;
		file.writeNext(row);
	}
	
	//Get all valid users from SuccessRecords file
	public JSONArray getAllUsers(File file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file.getAbsolutePath()));
        JSONArray data = new JSONArray();
        ArrayList<String> columns = new ArrayList<>();
		try {
            String line = "";
            int line_number = 1;
            while ((line = reader.readLine()) != null) {
            	if(line_number==1) {
            		line_number = line_number+1;
            		String[] headers = line.split(",");
            		for(String column:headers) {
            			columns.add(column.strip().replace("\"", ""));
            		}
            	}else {
                    JSONObject row_data = new JSONObject();
            		String[] values = line.split(",");
            		int i;
	            	for(i=0;i<values.length;i++) {
	            		row_data.put(columns.get(i).toLowerCase(), values[i].strip().replace("\"", ""));
	            	}
	            	while(i!=columns.size()) {
	            		row_data.put(columns.get(i).toLowerCase(), "");
	            		i++;
	            	}
            		data.put(row_data);
            	}
            }
	    }
	    catch (Exception e) {
	        e.printStackTrace();
	    }
		finally {
            reader.close();
		}
		return data;
	}
	
	//Will read the input (uploaded) file and returns user data in json data
	public JSONObject getDatafromFile(File temp_file, CSVWriter failed_records, CSVWriter success_records) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(temp_file.getAbsolutePath()));
		JSONObject datawithColumns = new JSONObject();
		datawithColumns.put("data", new JSONArray());
		datawithColumns.put("columns", new JSONArray());
        JSONArray data = new JSONArray();
        ArrayList<String> columns = new ArrayList<>();
		try {
            String line = "";
            int line_number = 1;
            while ((line = reader.readLine()) != null) {
            	if(line_number==1) {
            		line_number = line_number+1;
            		String[] headers = line.split(",");
            		for(String column:headers) {
            			columns.add(column.strip());
            		}
            		columns.add("Error");
            		if(!ValidateColumn(columns)) {
                    	System.out.println("Error in Headers");
            			return datawithColumns;
            		}
            		String[] arr = new String[columns.size()];  
            		arr = columns.toArray(arr);  
            		failed_records.writeNext(arr);
            		columns.remove(columns.size()-1);
            		columns.add("Discount_Code");
            		arr = columns.toArray(arr);  
            		success_records.writeNext(arr);
            		columns.remove(columns.size()-1);
            	}else {
                    JSONObject row_data = new JSONObject();
            		String[] values = line.split(",");
            		int i;
	            	for(i=0;i<values.length;i++) {
	            		row_data.put(columns.get(i).toLowerCase(), values[i].strip());
	            	}
	            	while(i!=columns.size()) {
	            		row_data.put(columns.get(i).toLowerCase(), "");
	            		i++;
	            	}
            		data.put(row_data);
            	}
            }
            System.out.println("data--->"+data);
	    }
	    catch (Exception e) {
            System.out.println("Exception--->");
	        e.printStackTrace();
	    }
		finally {
            reader.close();
		}
		datawithColumns.put("data", data);
		JSONArray col = new JSONArray(columns);
		datawithColumns.put("columns", col);
		return datawithColumns;
	}
	
	//Read the input file and creates a temp file in local storage
	public File readInputFile(Part filePart) throws IOException {
		// TODO Auto-generated method stub

        String f_name = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        File temp_file = new File("E:\\"+f_name);
        temp_file.createNewFile();
        InputStream fileContent = filePart.getInputStream();
        BufferedInputStream reader = new BufferedInputStream(fileContent);
        FileOutputStream fout = new FileOutputStream(temp_file);
        try (BufferedOutputStream bw = new BufferedOutputStream(fout)) {
            int by = 0;
            while ((by = reader.read()) != -1) {
                bw.write(by);
            }
            bw.close();
        }
        catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
            fileContent.close();
            reader.close();
            fout.close();
        }
        return temp_file;
	}


	//To validate all required columns are available in uploaded CSV
	public boolean ValidateColumn(ArrayList<String> columns) {
		if(!columns.contains("Email")) {
			return false;
		}
		else if(!columns.contains("Mobile_phone")) {
			return false;
		}
		else if(!columns.contains("Ticketing_date")) {
			return false;
		}
		else if(!columns.contains("Travel_date")) {
			return false;
		}
		else if(!columns.contains("PNR")) {
			return false;
		}
		else if(!columns.contains("Booked_cabin")) {
			return false;
		}
		else if(!columns.contains("First_name")) {
			return false;
		}
		else if(!columns.contains("Last_name")) {
			return false;
		}
		else if(!columns.contains("Fare_class")) {
			return false;
		}
		else if(!columns.contains("Pax")) {
			return false;
		}
		return true;
	}
}
