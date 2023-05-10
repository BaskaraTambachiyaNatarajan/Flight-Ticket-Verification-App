package com;

import com.Interface.IDiscount;
import com.Interface.ITicketValidation;

public class Validation implements ITicketValidation,IDiscount{
	
	//Will get discount code according to fareclass
	public String getDiscountCode(String fareClass) {
		// TODO Auto-generated method stub
		if(fareClass.length()==0 || fareClass.length()>1) {
			return "";
		}
		char c=fareClass.charAt(0);
		if(c>='A' && c<='E') {
			return "OFFER_20";
		}
		else if(c>='F' && c<='K') {
			return "OFFER_30";
		}
		else if(c>='L' && c<='R') {
			return "OFFER_25";
		}
		else {
			return "";
		}
	}

	//Validate email
	public boolean validateEmail(String mailId) {
		return mailId.matches("^[a-zA-Z0-9].[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
	}
	
	//Validate MobilePhone
	public boolean validateMobilePhone(String number) {
		return number.matches("^[0-9]{10}$");
	}
	
	//Valid Ticketing Date
	public boolean validateTicketingDate(String ticketing_date,String travel_date) {
		if(!ticketing_date.matches("^(19[0-9][0-9]|20[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$") || !travel_date.matches("^(19[0-9][0-9]|20[0-9][0-9])-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")) {
			return false;
		}
        String[] ticket = ticketing_date.split("-");
        String[] travel = travel_date.split("-");
        for(int i=ticket.length-1;i>=0;i--) {
        	if(Integer.parseInt(travel[i])>Integer.parseInt(ticket[i])) {
        		return true;
        	}else if(Integer.parseInt(travel[i])<Integer.parseInt(ticket[i])) {
        		return false;
        	}
        }
		return false;
	}
	
	//Validate the PNR if it has only 6 digits
	public boolean validatePNR(String pnr) {
		return pnr.matches("^(?=.*[a-zA-Z])[a-zA-Z0-9]{6}$");
	}
	
	//Validate only registered cabins are given
	public boolean validateBookedCabin(String cabin) {
		String[] cabins = {"economy", "premium economy", "business", "first"};
		for(String c : cabins) {
			if(c.equals(cabin.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

}
