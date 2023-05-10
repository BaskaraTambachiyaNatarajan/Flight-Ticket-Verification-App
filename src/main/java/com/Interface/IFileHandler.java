package com.Interface;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.opencsv.CSVWriter;

import jakarta.servlet.http.Part;

public interface IFileHandler {
	
	public Map<String, CSVWriter> createOutputFiles(String filepath) throws IOException;
	
	public void writeResult(CSVWriter file, JSONObject user,JSONArray columns, String message);
	
	public JSONObject getDatafromFile(File temp_file, CSVWriter failed_records, CSVWriter success_records) throws IOException;
	
	public File readInputFile(Part filePart) throws IOException;
}
