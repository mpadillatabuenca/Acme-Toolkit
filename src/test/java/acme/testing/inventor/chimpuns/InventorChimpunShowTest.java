package acme.testing.inventor.chimpuns;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpunShowTest extends TestHarness { 
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest 
	@CsvFileSource(resources = "/inventor/chimpuns/show.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void showTest(final int recordIndex, final String code, final String creationMoment, final String title, final String description, final String startTime, final String endingTime, final String budget, final String optionalLink) {
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List chimpuns");
		super.checkListingExists();
		super.sortListing(0, "desc");
		
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationMoment", creationMoment);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startTime", startTime);
		super.checkInputBoxHasValue("endingTime", endingTime);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("optionalLink", optionalLink);
		

		super.signOut();
	}
		// Ancillary methods ------------------------------------------------------

}
