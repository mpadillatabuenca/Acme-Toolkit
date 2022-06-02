package acme.testing.inventor.chimpuns;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpunDeleteTest extends TestHarness {
	
	// Lifecycle management --------------------------------------
	
	// Test cases ------------------------------------------------
				
		@ParameterizedTest
		@CsvFileSource(resources = "/inventor/chimpuns/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positiveTest (final int recordIndex, final String code, final String creationDate, final String title, final String description, final String startTime, final String endingTime, final String budget, final String optionalLink) {
					
			super.signIn("inventor1", "inventor1");
			super.clickOnMenu("Inventor", "List chimpuns");
			super.checkListingExists();
			super.clickOnListingRecord(0);

			super.checkFormExists();

			super.clickOnSubmit("Delete chimpun");
					
			super.checkNotErrorsExist();
				
			super.signOut();
		}

				

}
