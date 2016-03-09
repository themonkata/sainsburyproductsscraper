package sainsbury.behavioral;

import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import sainsbury.dataaccessor.ItemsDataAccess;
import sainsbury.dataaccessor.exceptions.NoContentAccessException;
import sainsbury.factory.BeansFactory;
import sainsbury.transformation.ItemsDto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test behavioral story for scrap products from Sainsbury site
 *
 */
public class WebScrapperStory {

	static final BeansFactory factory = BeansFactory.getInstance();
	
	private ItemsDataAccess itemsDataAccess;
	
	private ItemsDto items;
	
	@BeforeStory
	public void setUp() {
		itemsDataAccess = factory.getBean(ItemsDataAccess.class);
	}
	
	@Given("available site of Sainsbury products page")
	public void siteavailable() {
		items = null;
	}
	 
	@When("I connect to $siteUrl")
	public void connect(String siteUrl) throws NoContentAccessException {
		items = itemsDataAccess.retriveItems(siteUrl);
	}
	 
	@Then("I receive the $numberOfProducts products")
	public void retrieveProducts(int numberOfProducts) {
		assertNotNull(this.items);
		assertEquals(numberOfProducts, items.getResults().size());
	}
}
