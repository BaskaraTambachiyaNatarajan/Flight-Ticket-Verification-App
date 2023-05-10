package com;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Servlet implementation class SendMail
 */
public class SendMail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendMail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //This function will read the properties (mail credentials) from properties file
    public static Properties readPropertiesFile(String file_Name) throws IOException {
		FileInputStream fis = null;
		Properties prop = null;
		try {
			fis = new FileInputStream(file_Name);
			prop = new Properties();
			prop.load(fis);
		} catch(FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch(IOException ioe) {
			ioe.printStackTrace();
		} finally {
			fis.close();
		}
		return prop;
	}
    
    //Will send mail for all valid users who have discount code
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		FileHandler fh = new FileHandler();
		String res = "";
		Properties prop = readPropertiesFile("E:\\Flight Ticket App\\Files\\mailer.properties");
		String from=prop.getProperty("SENDERID");
		String password=prop.getProperty("SENDERPASS");
		String subject=prop.getProperty("SUBJECT");
		String smtp=prop.getProperty("SMTP");
		String port=prop.getProperty("PORT");
		
		String file = request.getParameter("file");
		String filepath = "E:\\Flight Ticket App\\src\\main\\webapp\\" + file;
		
		JSONArray users = fh.getAllUsers(new File(filepath));
		for(int i=0;i<users.length();i++) {
			
			  	JSONObject mailIDs = new JSONObject(users.get(i).toString());
			  	String to = mailIDs.getString("email");
			  	String discountCode = mailIDs.getString("discount_code");
			   //Get the session object  
			  	if(discountCode.length()>0) {
				   Properties props = new Properties();  
				   props.put("mail.smtp.host",smtp);  
				   props.put("mail.smtp.auth", "true"); 
	
				   props.put("mail.smtp.starttls.enable", "true");
				   props.put("mail.smtp.starttls.required", "true");
				   props.put("mail.smtp.ssl.protocols", "TLSv1.2");
				   props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			       props.put("mail.smtp.port", port);    
				     
				   Session session = Session.getDefaultInstance(props,  
				    new javax.mail.Authenticator() {  
				      protected PasswordAuthentication getPasswordAuthentication() {  
				    return new PasswordAuthentication(from,password);  
				      }  
				    });  
				  
				   //Compose the message  
				    try {  
				     MimeMessage message = new MimeMessage(session); 
				     message.setFrom(new InternetAddress(from));  
				     message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
				     message.setSubject(subject);  
				     message.setText("Please find the offer code for you - "+discountCode);  
				       
				    //send the message  
				     Transport.send(message);  
				  
				     System.out.println("message sent successfully for "+to);
				     res = "Successfully Sent";
				   
				     }
				     catch (MessagingException e) {
				    	 e.printStackTrace();
				    	 res = "Sending Mail was failed";
				     }  
			  	}
		}
		PrintWriter out = response.getWriter();
		out.println(res);
	}

}
