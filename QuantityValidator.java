package businessLayer;

import model.Order;

public class QuantityValidator implements Validator<Order> {
	private static int min=1;
	@Override
	/**
	 * Se verifica daca catitatea produsului este valida(mai mare decat 0).
	 * @param t produsul validat
	 * @throws exceptie care va fi aruncata daca nu corespunde conditiei date
	 */
	public void validate(Order t) {
		// TODO Auto-generated method stub
		if(min>t.getQuantity()) {
			throw new IllegalArgumentException("Quantity is not valid!");
		}
	}

}
