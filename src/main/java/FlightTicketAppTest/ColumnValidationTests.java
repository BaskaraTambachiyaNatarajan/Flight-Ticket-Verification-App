package FlightTicketAppTest;

import java.util.ArrayList;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.FileHandler;

public class ColumnValidationTests {
	
	FileHandler fh = new FileHandler();
	@Test
	public void TestValidColumns() {
		ArrayList<String> columns = getColumns();
		Assert.assertEquals(fh.ValidateColumn(columns), true);
	}
	
	@Test
	public void TestMissingColumn() {
		ArrayList<String> columns = getColumns();
		columns.remove("Booked_cabin");
		Assert.assertEquals(fh.ValidateColumn(columns), false);
	}
	
	@Test
	public void TestCasesenstive() {
		ArrayList<String> columns = getColumns();
		columns.remove("Booked_cabin");
		columns.add("booked_Cabin");
		Assert.assertEquals(fh.ValidateColumn(columns), false);
	}
	
	@Test
	public void TestAdditionalColumns() {
		ArrayList<String> columns = getColumns();
		columns.add("booked_Cabin");
		Assert.assertEquals(fh.ValidateColumn(columns), true);
	}

	private ArrayList<String> getColumns() {
		// TODO Auto-generated method stub

		ArrayList<String> columns = new ArrayList<>();
		columns.add("Email");
		columns.add("Mobile_phone");
		columns.add("Ticketing_date");
		columns.add("Travel_date");
		columns.add("PNR");
		columns.add("Booked_cabin");
		columns.add("First_name");
		columns.add("Last_name");
		columns.add("Fare_class");
		columns.add("Pax");
		
		return columns;
	}
}
