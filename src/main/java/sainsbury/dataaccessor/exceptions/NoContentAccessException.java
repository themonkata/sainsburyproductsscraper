package sainsbury.dataaccessor.exceptions;

/**
 * An exception indicates there is a no access to the items products
 */
public class NoContentAccessException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6638588983241556104L;

	public NoContentAccessException(String msg) {
		super(msg);
	}
}
