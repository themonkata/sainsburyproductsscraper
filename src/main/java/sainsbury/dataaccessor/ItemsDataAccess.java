package sainsbury.dataaccessor;

import sainsbury.dataaccessor.exceptions.NoContentAccessException;
import sainsbury.transformation.ItemsDto;

/**
 * Product items' data access layer 
 * 
 * Takes responsibilities for accessing and retrieving the data for the product items
 */
public interface ItemsDataAccess {

	/**
	 * @param uri products uri's container URI, form to be picked up
	 * 
	 * @return get and return all available products items wrapped in {@link ItemsDto}
	 * 
	 * @throws NoContentAccessException	throws in case there are connection errors
	 * 		during the connection to the data base resource
	 */
	ItemsDto retriveItems(final String uri) throws NoContentAccessException;
}
