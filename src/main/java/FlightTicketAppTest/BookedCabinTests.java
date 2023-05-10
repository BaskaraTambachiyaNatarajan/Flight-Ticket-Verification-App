package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class BookedCabinTests {

	Validation v =new Validation();
	
	@Test
	public void TestEconomyCabin() {
		boolean mobile = v.validateBookedCabin("Economy");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestPremiumEconomyCabin() {
		boolean mobile = v.validateBookedCabin("Premium Economy");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestBusinessCabin() {
		boolean mobile = v.validateBookedCabin("Business");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestFirstCabin() {
		boolean mobile = v.validateBookedCabin("First");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestLowerCase() {
		boolean mobile = v.validateBookedCabin("first");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestOtherValues() {
		boolean mobile = v.validateBookedCabin("first12");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestEmptyCabin() {
		boolean mobile = v.validateBookedCabin("");
	    Assert.assertEquals(mobile,false);
	}

}
