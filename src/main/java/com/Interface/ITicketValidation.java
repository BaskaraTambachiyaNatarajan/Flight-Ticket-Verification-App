package com.Interface;

public interface ITicketValidation {
	public boolean validateEmail(String mailId);
	
	public boolean validateMobilePhone(String number);
	
	public boolean validateTicketingDate(String ticketing_date,String travel_date);
	
	public boolean validatePNR(String pnr);
	
	public boolean validateBookedCabin(String cabin);
}
