package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class EmailTests {
	
	Validation v =new Validation();
	
	@Test
	public void TestValidEmail() {
		boolean mail = v.validateEmail("thamba@gmail.com");
	    Assert.assertEquals(mail, true);
	}
	
	@Test
	public void TestEmptyEmail() {
		boolean mail = v.validateEmail("");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TesttwoAts() {
		boolean mail = v.validateEmail("thamba@boss@gmail.com");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestnoDataBeforeAt() {
		boolean mail = v.validateEmail("@gmail.com");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestnoDataAfterAt() {
		boolean mail = v.validateEmail("baskar@");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestWithoutTLD() {
		boolean mail = v.validateEmail("baskar@gmail");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestwithSpecialCharacterAtStarting() {
		boolean mail = v.validateEmail("_baskar@gmail.com");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestwithSpecialCharacterAtTLD() {
		boolean mail = v.validateEmail("baskar@gmail.co.m");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestwithSpecialCharacterAtDomain() {
		boolean mail = v.validateEmail("baskar@gmai!l.com");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestOneCharacterinTLD() {
		boolean mail = v.validateEmail("baskar@gmail.m");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestFiveCharactersinTLD() {
		boolean mail = v.validateEmail("baskar@gmail.commm");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestNumbersinTLD() {
		boolean mail = v.validateEmail("baskar@gmail.c1m");
	    Assert.assertEquals(mail, false);
	}
	
	@Test
	public void TestNumbersinDomain() {
		boolean mail = v.validateEmail("baskar@gmail12.com");
	    Assert.assertEquals(mail, true);
	}
}
