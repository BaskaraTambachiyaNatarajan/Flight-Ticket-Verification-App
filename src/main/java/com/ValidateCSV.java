package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import org.json.JSONObject;

import com.Interface.IProcessFile;

@MultipartConfig
public class ValidateCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private IProcessFile _processFile;

	public ValidateCSV() {
		super();
		_processFile = new ProcessCSV();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Collection<Part> fileParts = request.getParts();
		JSONObject output = new JSONObject();
		for (Part filePart : fileParts) {
			output = _processFile.processInputFile(filePart);
			break;
		}
		out.println(output);
	}
}
