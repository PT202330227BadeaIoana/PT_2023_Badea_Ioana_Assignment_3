package model;

public class Client {
	
	private int id;
	private String name;
	private String email;

	/** Am creat constructor si getter si setter pentru fiecare atribut.
	 * @param id id-ul clientului
	 * @param name numele clientului
	 * @param email email-ul clientului
	 * @author ioana
	 *
	 */
	
	public Client() {
		super();
	}
	
	public Client(int id, String name, String email) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Client(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/** metoda pentru afisare ca string
	 * @author ioana
	 *
	 */
	@Override
	public String toString() {
		return "Client [id=" + id + ", name=" + name + ", email=" + email + "]";
	}

	/** string pentru fiecare field din tabel
	 * @author ioana
	 *
	 */
	public String[] getFieldValues() {
		String[] fields = new String[3];
		fields[0] = String.valueOf(id);
		fields[1] = name;
		fields[2] = email;
		return fields;
	}
}
