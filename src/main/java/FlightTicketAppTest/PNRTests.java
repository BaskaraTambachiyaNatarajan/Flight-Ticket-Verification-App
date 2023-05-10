package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class PNRTests {

	Validation v =new Validation();
	
	@Test
	public void TestValidPNR() {
		boolean mobile = v.validatePNR("RET123");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestEmpty() {
		boolean mobile = v.validatePNR("");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestMoreThanSix() {
		boolean mobile = v.validatePNR("ABC1234");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestLessThanSix() {
		boolean mobile = v.validatePNR("ABC12");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestOnlyNumeric() {
		boolean mobile = v.validatePNR("123456");
	    Assert.assertEquals(mobile,false);
	}
}
