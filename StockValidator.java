package businessLayer;

import model.Product;

public class StockValidator implements Validator<Product>{
	private static final int min = 1;
	private static final int max = 100;
	
	/**
	 * Se verifica daca stock-ul produsului este valid(mai mare decat 1 si mai mic decat 100).
	 * @param t produsul validat
	 * @throws exceptie care va fi aruncata daca nu corespunde conditiilor date
	 */
	@Override
	public void validate(Product t) {
		// TODO Auto-generated method stub
		if(min>t.getStock() || max<t.getStock()) {
			throw new IllegalArgumentException("Price is not valid!");
		}
	}

}
