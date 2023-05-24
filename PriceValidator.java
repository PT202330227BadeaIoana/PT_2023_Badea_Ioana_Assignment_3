package businessLayer;

import model.Product;

public class PriceValidator implements Validator<Product> {
	private static final int min = 0;
	
	/**
	 * Se verifica daca pretul produsului este valid(mai mare decat 0).
	 * @param t produsul validat
	 * @throws exceptie care va fi aruncata daca nu corespunde conditiei date
	 */
	@Override
	public void validate(Product t) {
		// TODO Auto-generated method stub
		if(min>t.getPrice()) {
			throw new IllegalArgumentException("Price is not valid!");
		}
	}

}
