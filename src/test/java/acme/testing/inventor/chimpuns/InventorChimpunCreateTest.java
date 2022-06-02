package acme.testing.inventor.chimpuns;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpunCreateTest extends TestHarness { 
	
	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest 
	@CsvFileSource(resources = "/inventor/chimpuns/create-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void createPositiveTest(final int recordIndex, final String code, final String creationDate, final String title, final String description, final String startTime, final String endingTime, final String budget, final String optionalLink) {
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List chimpuns");
		super.checkListingExists();
		super.clickOnButton("Create chimpun");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endingTime", endingTime);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("optionalLink", optionalLink);
		super.clickOnSubmit("Create chimpun");
		
		super.checkNotErrorsExist();
		
		super.navigateHome();
		super.clickOnMenu("Inventor", "List chimpuns");
		super.checkListingExists();
		super.sortListing(0, "desc");

		super.checkColumnHasValue(recordIndex, 2, title);
		super.checkColumnHasValue(recordIndex, 3, description);
		super.checkColumnHasValue(recordIndex, 4, startTime);
		super.checkColumnHasValue(recordIndex, 5, endingTime);

		super.signOut();
	}
	
	@ParameterizedTest 
	@CsvFileSource(resources = "/inventor/chimpuns/create-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void createNegativeTest(final int recordIndex, final String code, final String creationDate, final String title, final String description, final String startTime, final String endingTime, final String budget, final String optionalLink) {
		super.signIn("inventor1", "inventor1");
		super.clickOnMenu("Inventor", "List chimpuns");
		super.checkListingExists();
		super.clickOnButton("Create chimpun");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startTime", startTime);
		super.fillInputBoxIn("endingTime", endingTime);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("optionalLink", optionalLink);
		super.clickOnSubmit("Create chimpun");
		
		super.checkErrorsExist();

		super.signOut();
	}
		// Ancillary methods ------------------------------------------------------

}
