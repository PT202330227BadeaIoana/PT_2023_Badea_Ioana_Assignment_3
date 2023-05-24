package model;

public class Order {

	private int id;
	private int idC;
	private int idP;
	private int quantity;

	/**Am creat constructor, getter si setter pentru fiecare atribut.
	 * @param id id-ul comenzii
	 * @param idC id-ul clientului
	 * @param idP id-ul produsului
	 * @param quantity cantitatea
	 * 
	 * @author ioana
	 *
	 */
	public Order() {
		super();
	}
	
	public Order(int id, int idC, int idP, int quantity) {
		super();
		this.id = id;
		this.idC = idC;
		this.idP = idP;
		this.quantity = quantity;
	}
	
	public Order(int idC, int idP, int quantity) {
		super();
		this.idC = idC;
		this.idP = idP;
		this.quantity = quantity;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdC() {
		return idC;
	}
	public void setIdC(int idC) {
		this.idC = idC;
	}
	public int getIdP() {
		return idP;
	}
	public void setIdP(int idP) {
		this.idP = idP;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	/**metoda pentru afisare in fiecare field din tabel
	 * @author ioana
	 *
	 */
	public String[] getFieldsValues() {
		String[] fields = new String[4];
		fields[0]=String.valueOf(id);
		fields[1]=String.valueOf(idC);
		fields[2]=String.valueOf(idP);
		fields[3]=String.valueOf(quantity);
		return fields;
	}
	
	/** metoda pentru afisare
	 * @author ioana
	 *
	 */
	@Override
	public String toString() {
		return "Order [idO=" + id + ", idC=" + idC + ", idP=" + idP + ", quantity=" + quantity + ", client=" + "]";
	}


	
	
	
}
