package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class MobilePhoneTests {

	Validation v =new Validation();
	
	@Test
	public void TestValidMobile() {
		boolean mobile = v.validateMobilePhone("1234567890");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestEmptyMobile() {
		boolean mobile = v.validateMobilePhone("");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestNonNumeric() {
		boolean mobile = v.validateMobilePhone("12345^789o");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestMorethanTen() {
		boolean mobile = v.validateMobilePhone("1234567890098");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestLessthanTen() {
		boolean mobile = v.validateMobilePhone("123456789");
	    Assert.assertEquals(mobile,false);
	}
}
