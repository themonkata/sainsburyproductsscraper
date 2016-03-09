package sainsbury.transformation;

/**
 * Class responsible for the transformation in the communication
 * between JSON and internal model objects.
 * 
 * In the out-going mode those classes basically wrap the internal mode's state 
 * and provide appropriate getter methods to get information to be 
 * written to JSON. This way information is loaded only if it is needed. 
 * 
 * In the in-going mode those classes parse given JSON stream and 
 * provide access to the values to be set. 
 * 
*/
public abstract class Dto {
	
	protected static final String ID_NODE_NAME = "id";
	
	/**
	 * Default constructor.
	 */
	public Dto() {
		// nothing here
	}
	

}
