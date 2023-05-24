package businessLayer;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import DataAccess.ClientDAO;
import DataAccess.OrderDAO;
import model.Client;
import model.Order;

public class OrderBLL {
	private List<Validator<Order>> validators;
	private OrderDAO orderDAO;
	
	/**
	 * initializeaza QuantityValidator si OrderDAO
	 */
	public OrderBLL() {
		validators = new ArrayList<Validator<Order>>();
		validators.add(new QuantityValidator());
		
		orderDAO = new OrderDAO();
	}
	
	/**
	 * Gaseste comanda in functie de Id.
	 * @param id id-ul comanda gasita
	 * @return comanda gasita
	 */
	public Order findOrderbyID(int id) {
		Order order = orderDAO.findById(id);
		if(order == null) {
			throw new NoSuchElementException("The order with id=" + id +" was not found!");
		}
		return order;
	}
	
	/**
	 * Insereaza o noua comanda in baza de date.
	 * @param order comanda care va fi introdusa
	 */
	public void insertOrder(Order order) {
		for(Validator<Order> o : validators) {
			o.validate(order);
		}
		orderDAO.insert(order);
	}
	
	/**
	 * Modifica un camp al comenzii.
	 * @param order comanda care va fi modificata
	 * @param field numele campului de modificat
	 * @param value noua valoare
	 */
	public void updateOrder(Order order, String field, String value, int id) {
		if(field.equals("quantity")) {
			for(Validator<Order> v :validators) {
				v.validate(new Order(order.getId(), order.getIdC(), order.getIdP(), Integer.parseInt(value)));
			}
		}
		orderDAO.update(order, field, value, id);
	}
	
	/**
	 * Sterge o comanda din baza de date.
	 * @param id id-ul comenzii care va fi stearsa
	 */
	public void deleteOrder(int id) {
		orderDAO.delete(id);
	}
	
	/** returneaza toate comenzile din baza de date
	 * 
	 * @return un array de comenzi
	 */
	public String[][] findAll(){
		List<Order> orders = orderDAO.findAll();
		if(orders.isEmpty()) {
			throw new NoSuchElementException("Is empty!");
		}
		String[][] orders2 = new String[orders.size()][];
		AtomicInteger nr = new AtomicInteger();
		for(Order o:orders) {
			orders2[nr.getAndIncrement()]=o.getFieldsValues();
		}
		return orders2;
	}
}
