package Presentation;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import Connection.ConnectionFactory;
import businessLayer.ClientBLL;
import businessLayer.OrderBLL;
import businessLayer.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

public class Controller {
	private View view;
	private ClientView clientView;
	private OrderView orderView;
	private ProductView productView;


	/**
	 * Gestioneaza interactiune intre View si BusinessLogic.
	 * @param view
	 */
	public Controller(View view) {
		this.view = view;

		clientView = new ClientView();

		orderView = new OrderView();	

		productView = new ProductView();

	}

}
