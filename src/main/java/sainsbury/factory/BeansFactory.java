package sainsbury.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * IoC beans factory
 * 
 * Use spring framework integration for dealing with beans creation and life-cycles
 *
 */
public class BeansFactory {

	private final ApplicationContext context;
	
	private static BeansFactory instance;
	
	private BeansFactory() {
		context = new ClassPathXmlApplicationContext("resources/Spring-Module.xml");
	}
	
	public static BeansFactory getInstance() {
		if (instance == null) {
			synchronized (BeansFactory.class) {
				if (instance == null) {
					instance = new BeansFactory();
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * @param clazzType {@link Class} type for requested bean
	 * @return {@link Bean} instantiation for the requested type
	 */
	public <T> T getBean(Class<T> clazzType) {
		return context.getBean(clazzType);
	}

}
