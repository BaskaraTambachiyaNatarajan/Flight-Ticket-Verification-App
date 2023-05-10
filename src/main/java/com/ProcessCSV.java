package com;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.Interface.IProcessFile;
import com.opencsv.CSVWriter;

import jakarta.servlet.http.Part;

public class ProcessCSV implements IProcessFile {


	//This function will process the input file
	public JSONObject processInputFile(Part filePart) throws IOException {
		// TODO Auto-generated method stub
			FileHandler fh = new FileHandler();
			JSONObject output_files = new JSONObject();
			long folder_name = System.currentTimeMillis();
			String filepath = "E:\\Flight Ticket App\\src\\main\\webapp\\" + folder_name;
			Map<String,CSVWriter> csvFiles = fh.createOutputFiles(filepath);
			
			CSVWriter failed_records = csvFiles.get("fail");
			CSVWriter success_records = csvFiles.get("success");
			
			File temp_file = fh.readInputFile(filePart);
            JSONObject datawithColumns = fh.getDatafromFile(temp_file,failed_records,success_records);
            JSONArray data = datawithColumns.getJSONArray("data");
            JSONArray columns = datawithColumns.getJSONArray("columns");
            if(temp_file.delete()){
                System.out.println("Deleted successfully");
            }
            else{
            	System.out.println("Not deleted");
            }
        	
            if(data.length()>0) {
	            Validation v = new Validation();
	            JSONArray failed_users = new JSONArray();
	            int totalUsers = 0;
	            int successfulUsers = 0;
	            for(int i=0;i<data.length();i++) {
	            	String message = "";
	            	JSONObject user = new JSONObject(data.get(i).toString());
	            	if(!v.validateEmail(user.get("email").toString())) {
	            		message = "Email Invalid";
	            	}
	            	if(!v.validateMobilePhone(user.get("mobile_phone").toString())) {
	            		if(message.length()==0) {
	            			message += "Mobile Phone Invalid";
	            		}else {
	            			message += " , Mobile Phone Invalid";
	            		}
	            	}
	            	if(!v.validateTicketingDate(user.get("ticketing_date").toString(),user.get("travel_date").toString())) {
	            		if(message.length()==0) {
	            			message += "Ticketing date Invalid";
	            		}else {
	            			message += " , Ticketing date Invalid";
	            		}
	            	}
	            	if(!v.validatePNR(user.get("pnr").toString())) {
	            		if(message.length()==0) {
	            			message += "PNR Invalid";
	            		}else {
	            			message += " , PNR Invalid";
	            		}
	            	}
	            	if(!v.validateBookedCabin(user.get("booked_cabin").toString())) {
	            		if(message.length()==0) {
	            			message += "Cabin details Invalid";
	            		}else {
	            			message += " , Cabin details Invalid";
	            		}
	            	}
            		if(message.length()==0) {
            			message = v.getDiscountCode(user.get("fare_class").toString());
                		successfulUsers++;
            		}
	            	if(message.contains("Invalid")) {
	            		fh.writeResult(failed_records,user,columns,message);
	            		failed_users.put(user);
	            	}else {
	            		fh.writeResult(success_records,user,columns,message);
	            	}
	            	totalUsers++;
	            }
	            output_files.put("FailedCount", totalUsers-successfulUsers);
	            output_files.put("SuccessCount", successfulUsers);
	    		output_files.put("Failed", folder_name+"/FailedRecords.csv");
	    		output_files.put("Success", folder_name+"/SuccessRecords.csv");
	            //output_files.put("FailedUsers", failed_users);
            }
            else {
            	System.out.println("Exception Occured");
            }

    		success_records.flush();
    		success_records.close();
    		failed_records.flush();
    		failed_records.close();
    		System.out.println("Completed");
    		return output_files;
	}

}
