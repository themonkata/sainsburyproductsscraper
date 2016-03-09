package sainsbury.transformation;

import java.util.List;

/**
 * A POJO class represents data transform object for representation 
 * for a list of product items and aggregation data in relation to the items
 */
public class ItemsDto extends Dto {

	private List<ItemDto> results;
	
	private Double total;

	public List<ItemDto> getResults() {
		return results;
	}

	public void setResults(List<ItemDto> results) {
		this.results = results;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
}
