package FlightTicketAppTest;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.Validation;

public class DiscountCodeTests {

	Validation v =new Validation();

	
	@Test
	public void TestValidDiscountCode() {
		Assert.assertEquals(v.getDiscountCode("A"), "OFFER_20");
		Assert.assertEquals(v.getDiscountCode("H"), "OFFER_30");
		Assert.assertEquals(v.getDiscountCode("P"), "OFFER_25");
		Assert.assertEquals(v.getDiscountCode("X"), "");
	}
	
	@Test
	public void TestLowerCase() {
		Assert.assertEquals(v.getDiscountCode("a"), "");
		Assert.assertEquals(v.getDiscountCode("h"), "");
		Assert.assertEquals(v.getDiscountCode("p"), "");
		Assert.assertEquals(v.getDiscountCode("x"), "");
	}
	
	@Test
	public void TestEmpty() {
		Assert.assertEquals(v.getDiscountCode(""), "");
	}
	
	@Test
	public void TestOtherCharacters() {
		Assert.assertEquals(v.getDiscountCode("1"), "");
		Assert.assertEquals(v.getDiscountCode("<"), "");
		Assert.assertEquals(v.getDiscountCode("."), "");
		Assert.assertEquals(v.getDiscountCode("#"), "");
	}
}
