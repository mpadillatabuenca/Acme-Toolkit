package acme.testing.inventor.chimpuns;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpunListTest extends TestHarness { 
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest 
	@CsvFileSource(resources = "/inventor/chimpuns/list-all.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void listTest(final int recordIndex, final String code, final String creationDate, final String title, final String description, final String startTime, final String endingTime, final String budget, final String optionalLink) {
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List chimpuns");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(recordIndex, 0, code);
		super.checkColumnHasValue(recordIndex, 1, creationDate);
		super.checkColumnHasValue(recordIndex, 2, title);
		super.checkColumnHasValue(recordIndex, 3, description);

		super.signOut();
	}
		// Ancillary methods ------------------------------------------------------

}
