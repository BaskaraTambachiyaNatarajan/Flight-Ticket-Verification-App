package com.Interface;

import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.http.Part;

public interface IProcessFile {
	
	public JSONObject processInputFile(Part filePart) throws IOException;

}
