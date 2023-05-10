package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class TicketTests {

	Validation v =new Validation();
	
	@Test
	public void TestValidTicket() {
		boolean mobile = v.validateTicketingDate("2020-01-02","2020-05-02");
	    Assert.assertEquals(mobile,true);
	}
	
	@Test
	public void TestEmptyTicketDate() {
		boolean mobile = v.validateTicketingDate("","2020-05-02");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestEmptyTravelDate() {
		boolean mobile = v.validateTicketingDate("2020-05-02","");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestInvalidTicketDateMonth() {
		boolean mobile = v.validateTicketingDate("2020-13-02","2022-05-02");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestInvalidTicketDateDay() {
		boolean mobile = v.validateTicketingDate("2020-11-31","2021-05-02");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestInvalidTravelDateDay() {
		boolean mobile = v.validateTicketingDate("2020-11-30","2021-02-30");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestInvalidTravelDateMonth() {
		boolean mobile = v.validateTicketingDate("2020-11-31","2021-00-02");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestInvalidFormat() {
		boolean mobile = v.validateTicketingDate("31-11-2020","02-02-2022");
	    Assert.assertEquals(mobile,false);
	}
	
	@Test
	public void TestFalseCriteria() {
		boolean mobile = v.validateTicketingDate("2020-11-30","2020-10-02");
	    Assert.assertEquals(mobile,false);
	}
}
