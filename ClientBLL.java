 package businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import DataAccess.ClientDAO;
import model.Client;

public class ClientBLL {
	private List<Validator<Client>> validators;
	private ClientDAO clientDAO;

	/**
	 * initializeaza EmailValidator si ClientDAO
	 */
	public ClientBLL() {
		validators = new ArrayList<Validator<Client>>();
		validators.add(new EmailValidator());
		clientDAO = new ClientDAO();
	}

	/**
	 * Gaseste clientul in functie de Id.
	 * @param id id-ul clientului gasit
	 * @return clientul gasit
	 */
	public Client findClientByID(int id) {
		Client st = clientDAO.findById(id);
		if (st == null) {
			throw new NoSuchElementException("The client with id =" + id + " was not found!");
		}
		return st;
	}
	
	/**
	 * Insereaza un nou client in baza de date.
	 * @param c clientul care va fi introdus
	 */
	public void insertClient(Client c) {
		
		for(Validator<Client> v :validators) {			
			v.validate(c);			
		}
		clientDAO.insert(c);		
	}
	
	/**
	 * Modifica un camp al clientului.
	 * @param c clientul care va fi modificat
	 * @param field numele campului de modificat
	 * @param value noua valoare
	 */
	public void updateClient(Client c, String field, String value, int id) {
		if(field.equals("email")) {
			for(Validator<Client> v :validators) {
				v.validate(new Client (c.getId(), c.getName(), value));
			}
		}
		clientDAO.update(c, field, value, id);
	}

	/**
	 * Sterge un client din baza de date.
	 * @param id id-ul clientului care va fi sters
	 */
	public void deleteClient(int id) {
		clientDAO.delete(id);
	}
	
	/** returneaza toti clientii din baza de date
	 * 
	 * @return un array de clienti
	 */
	public String[][] findAll(){
		List<Client> clients = clientDAO.findAll();
		if(clients.isEmpty()) {
			throw new NoSuchElementException("Is empty!");
		}
		String[][] clients2 = new String[clients.size()][];
		int nr=0;
		for(Client c: clients) {
			clients2[nr]=c.getFieldValues();
			nr++;
		}
		return clients2;
	}
}
