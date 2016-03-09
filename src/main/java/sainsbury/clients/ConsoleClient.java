package sainsbury.clients;

import sainsbury.dataaccessor.ItemsDataAccess;
import sainsbury.dataaccessor.exceptions.NoContentAccessException;
import sainsbury.factory.BeansFactory;
import sainsbury.transformation.ItemsDto;

import com.google.gson.Gson;

/**
 * A console client base reader for the product items. 
 * 
 * Reads / scrap and print in the console the products' result
 */
public class ConsoleClient {

	private static final BeansFactory factory = BeansFactory.getInstance();
	private static final Gson gson = new Gson();
	
	public static void main(String[] args) {
		ItemsDataAccess itemsDataAccess = factory.getBean(ItemsDataAccess.class);
		
		try {
			final ItemsDto itemsDto = itemsDataAccess.retriveItems(null);
			final String itemsAsJson = gson.toJson(itemsDto);
			
			System.out.println(itemsAsJson);
		} catch (NoContentAccessException nce) {
			System.err.println(nce.getMessage());
		}
	}

}
