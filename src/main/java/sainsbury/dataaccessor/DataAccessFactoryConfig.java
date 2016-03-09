package sainsbury.dataaccessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataAccessFactoryConfig {

	@Bean
	public ItemsDataAccess getItemsDataAccess() {
		return new WebScrapDataAccessor();
	}
}
