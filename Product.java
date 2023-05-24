package model;

import java.util.List;
import java.util.NoSuchElementException;

public class Product {

	private int id;
	private int stock;
	private String name;
	private float price;
	
	/**Am creat constructor, getter si setter pentru fiecare atribut.
	 * @param idP is-ul produsului
	 * @param stock stock-ul produsului
	 * @param name numele produsului
	 * @param price pretul produsului
	 * @author ioana
	 *
	 */

	public Product() {
		super();
	}
	
	public Product(int id, int stock, String name, float price) {
		super();
		this.id = id;
		this.stock = stock;
		this.name = name;
		this.price = price;
	}
	
	public Product(int stock, String name, float price) {
		super();
		this.stock = stock;
		this.name = name;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	/** string pentru fiecare field din tabel
	 * @author ioana
	 *
	 */
	public String[] getFieldsValues() {
		String[] fields = new String[4];
		fields[0]=String.valueOf(id);
		fields[1]=String.valueOf(stock);
		fields[2]=name;
		fields[3]=String.valueOf(price);
		return fields;
	}
	
	/**metoda pentru a a crea un string cu atributele
	 * @author ioana
	 *
	 */
	@Override
	public String toString() {
		return "Product [idP=" + id + ", stockF=" + stock + ", name=" + name + ", price=" + price + "]";
	}

	
}
