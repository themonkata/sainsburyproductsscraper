package sainsbury.dataaccessor;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import sainsbury.dataaccessor.exceptions.NoContentAccessException;
import sainsbury.transformation.ItemDto;
import sainsbury.transformation.ItemsDto;

/**
 * A data access layer for retrieving products data via html web page 
 * place the role as a data base container for the products
 */
public class WebScrapDataAccessor implements ItemsDataAccess {

	public static final String DEFAULT_CONTENT_URI = 
		"http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html";
	
	public static final String IMG = "img";
	public static final String SRC = "src";
	public static final String HREF = "href";
	
	public static final String UNIT = "/unit";
	private static final String POUND_SIGH = "&pound";

	static class ACCESSOR_QUERIES {
		static final String PRODUCTS = "div.product";
		
		static final String HEADERS = 
				"div.productInner > div.productInfoWrapper > div.productInfo > h3 > a";
		
		static final String PRICE = 
				"div.addToTrolleytabBox > div.addToTrolleytabContainer > "
					+ "div.pricingAndTrolleyOptions > div.priceTab > div.pricing > "
					+ "p.pricePerUnit";
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ItemsDto retriveItems(String uri) throws NoContentAccessException {
		if (uri == null) {
			uri = DEFAULT_CONTENT_URI;
		}
		Document document = null;
		
		Connection connection = Jsoup.connect(DEFAULT_CONTENT_URI);
		
		ItemsDto itemsDto = null;
		try {
			document = connection.get();

			List<ItemDto> itemDtos = null;
			Elements items = document.select(ACCESSOR_QUERIES.PRODUCTS);
			if (items != null && !items.isEmpty()) {
				itemDtos = new ArrayList<ItemDto>(items.size());
				double totalPrice = 0;
				for (Element productElement : items) {
					final String itemTitle = extractTitle(productElement);
					final double unitPrice = extractPrice(productElement);
					final int size = extractSize(productElement);
				
					ItemDto itemDto = new ItemDto();
					itemDto.setSize(size + "kb");
					itemDto.setTitle(itemTitle);
					itemDto.setUnitPrice(unitPrice);
				
					itemDtos.add(itemDto);
				
					totalPrice += unitPrice;
				}
				itemsDto = new ItemsDto();
				itemsDto.setResults(itemDtos);
				itemsDto.setTotal(totalPrice);
			}
		} catch (IOException ioe) {
			throw new NoContentAccessException("There is no connection to the items's content");
		}
		
		return itemsDto;
	}
	
	private String extractTitle(final Element productElement) throws IOException {
		Elements itemHeaders = productElement.select(ACCESSOR_QUERIES.HEADERS);
		Element itemHeader = (itemHeaders != null) && !itemHeaders.isEmpty() ? 
				itemHeaders.first() : null;
		final String itemTitle = (itemHeader != null) ? itemHeader.text() : null;
	
		return itemTitle;
	}
	
	private double extractPrice(final Element productElement) {
		Elements itemUnitPrices = productElement.select(ACCESSOR_QUERIES.PRICE);
		Element itemUnitPrice = (itemUnitPrices != null && !itemUnitPrices.isEmpty()) 
			? itemUnitPrices.first() : null;
	
		final String unitPrice = (itemUnitPrice != null) ? 
					itemUnitPrice.text().replaceFirst(POUND_SIGH, "").replaceFirst(UNIT, "")
					: null;
		double price = 0;
		
		if (unitPrice != null) {
			price = Double.parseDouble(unitPrice);
		}
		
		return price;
	}
	
	private int extractSize(final Element productElement) throws IOException {
		Elements itemHeaders = productElement.select(ACCESSOR_QUERIES.HEADERS);
		Element itemHeader = (itemHeaders != null) && !itemHeaders.isEmpty() ? 
				itemHeaders.first() : null;
		
		Element pic = itemHeader.select(IMG).first();

		int elPicSize = new URL(pic.absUrl(SRC)).openConnection().getContentLength();
		int elDescSize = new URL(itemHeader.absUrl(HREF)).openConnection().getContentLength();
		
		final int size = elDescSize - elPicSize;

		return size;
	}
}
