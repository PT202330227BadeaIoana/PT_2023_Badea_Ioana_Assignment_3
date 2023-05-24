package businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import DataAccess.ProductDAO;
import model.Order;
import model.Product;

public class ProductBLL {
	private List<Validator<Product>> validators;
	private ProductDAO productDAO;
	
	/**
	 * initializeaza StockValidator, PriceValidator si ProductDAO
	 */
	public ProductBLL() {
		validators=new ArrayList<>();
		validators.add(new StockValidator());
		validators.add(new PriceValidator());
		
		productDAO = new ProductDAO();
	}
	
	/**
	 * Gaseste produsul in functie de Id.
	 * @param id id-ul produsului gasit
	 * @return produsul gasit
	 */
	public Product findProductbyID(int id) {
		Product product = productDAO.findById(id);
		if(product == null) {
			throw new NoSuchElementException("The product with id=" + id +" was not found!");
		}
		return product;
	}
	
	/**
	 * Insereaza un nou produs in baza de date.
	 * @param product produsul care va fi introdus
	 */
	public void insertProduct(Product product) {
		for(Validator<Product> o : validators) {
			o.validate(product);
		}
		productDAO.insert(product);
	}
	
	/**
	 * Modifica un camp al produsului.
	 * @param product produsul care va fi modificat
	 * @param field numele campului de modificat
	 * @param value noua valoare
	 */
	public void updateProduct(Product product, String field, String value, int id) {
		System.out.println("dabll");
		if(field.equals("stock")) {
			for(Validator<Product> v :validators) {
				v.validate(new Product(product.getId(), Integer.parseInt(value), product.getName(), product.getPrice()));
			}
		}
		productDAO.update(product, field, value, id);
	}
	
	/**
	 * Sterge un produs din baza de date.
	 * @param id id-ul produsului care va fi sters
	 */
	public void deleteProduct(int id) {
		productDAO.delete(id);
	}
	
	/** returneaza toate produsele din baza de date
	 * 
	 * @return un array de produse 
	 */
	public String[][] findAll(){
		List<Product> products = productDAO.findAll();
		if(products.isEmpty()) {
			throw new NoSuchElementException("Is empty!");
		}
		String[][] products2 = new String[products.size()][];
		AtomicInteger nr = new AtomicInteger();
		for(Product o:products) {
			products2[nr.getAndIncrement()]=o.getFieldsValues();
		}
		return products2;
	}
}
