package Presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import DataAccess.BillDAO;
import businessLayer.BilBLL;
import businessLayer.OrderBLL;
import businessLayer.ProductBLL;
import model.Bill;
import model.Client;
import model.Order;
import model.Product;

/**
 * Aceasta clasa este facuta pentru operatiile cu Order. * /
 * 
 * @author ioana
 *
 */
public class OrderView extends JFrame {

	private JTextField idO2;
	private JTextField idC2;
	private JTextField product2;
	private JTextField quantity2;

	private JLabel title;

	private JLabel idO;
	private JLabel idC;
	private JLabel product;
	private JLabel quantity;

	private JButton insert;
	private JButton update;
	private JButton delete;
	private JButton findAll;
	private JButton showBill;

	private OrderBLL orderBLL;

	/**
	 * Constructor pentru OrderView, seteaza elemetele utilizate. Face legatura cu
	 * OrderBLL.
	 */
	public OrderView() {

		orderBLL = new OrderBLL();

		this.setBounds(100, 100, 804, 630);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		title = new JLabel("ORDER");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD, 30));
		title.setBounds(317, 11, 171, 44);
		this.getContentPane().add(title);

		idO = new JLabel("ID ORDER:");
		idO.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		idO.setBounds(43, 97, 163, 37);
		this.getContentPane().add(idO);

		idC = new JLabel("ID CLIENT:");
		idC.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		idC.setBounds(43, 145, 163, 37);
		this.getContentPane().add(idC);

		product = new JLabel("ID PRODUCT:");
		product.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		product.setBounds(450, 97, 163, 37);
		this.getContentPane().add(product);

		quantity = new JLabel("QUANTITY:");
		quantity.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		quantity.setBounds(450, 145, 163, 37);
		this.getContentPane().add(quantity);

		idO2 = new JTextField();
		idO2.setBounds(157, 97, 163, 37);
		this.getContentPane().add(idO2);
		idO2.setColumns(10);

		idC2 = new JTextField();
		idC2.setColumns(10);
		idC2.setBounds(157, 145, 163, 37);
		this.getContentPane().add(idC2);

		product2 = new JTextField();
		product2.setColumns(10);
		product2.setBounds(578, 97, 163, 37);
		this.getContentPane().add(product2);

		quantity2 = new JTextField();
		quantity2.setColumns(10);
		quantity2.setBounds(578, 145, 163, 37);
		this.getContentPane().add(quantity2);

		/**
		 * Insereaza o comanda in baza de date.
		 */
		insert = new JButton("INSERT");
		insert.setFont(new Font("Times New Roman", Font.BOLD, 20));
		insert.setBounds(43, 215, 161, 39);
		this.getContentPane().add(insert);
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getIdO2();
					int idC = getIdC2();
					int idP = getProduct2();
					int quantity = getQuantity2();
					Order o = new Order(id, idC, idP, quantity);
					ProductBLL pBLL = new ProductBLL();
					Product p = pBLL.findProductbyID(idP);
					if (p.getStock() > quantity) {
						int val = p.getStock() - quantity;
						pBLL.updateProduct(p, "stock", String.valueOf(val), idP);
						orderBLL.insertOrder(o);
					}
					refreshTotal();
				} catch (Exception exception) {
					showErorrMessage("Complete the fields!");
				}
			}
		});

		/**
		 * Sterge o comanda din baza de date .
		 */
		delete = new JButton("DELETE");
		delete.setFont(new Font("Times New Roman", Font.BOLD, 20));
		delete.setBounds(240, 215, 161, 39);
		this.getContentPane().add(delete);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idO = getIdO2();
					orderBLL.findOrderbyID(idO);
					orderBLL.deleteOrder(idO);
					refreshTotal();
				} catch (Exception exception) {
					showErorrMessage("Complete the fields!");
				}
			}
		});

		/**
		 * Update realizeaza modificari in tabelul "order" si il afiseaza intr-o noua
		 * fereastra.
		 */
		update = new JButton("UPDATE");
		update.setFont(new Font("Times New Roman", Font.BOLD, 20));
		update.setBounds(430, 215, 161, 39);
		this.getContentPane().add(update);
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getIdO2();
					String field = "";
					String value = "";
					Order o = orderBLL.findOrderbyID(id);
					if (getQuantity2() != 0) {
						field = "quantity";
						value = String.valueOf(getQuantity2());
					}
					orderBLL.updateOrder(o, field, value, id);
					refreshTotal();
				} catch (Exception exception) {
					showErorrMessage("Complete the fields!");
				}
			}
		});

		/**
		 * Afiseaza datele din tabelul order.
		 */
		findAll = new JButton("SHOW");
		findAll.setFont(new Font("Times New Roman", Font.BOLD, 20));
		findAll.setBounds(43, 275, 161, 39);
		this.getContentPane().add(findAll);
		findAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableView orderTable = new TableView(new Order(), orderBLL);
					orderTable.setVisible(true);
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Afiseaza datele din tabelul order.
		 */
		showBill = new JButton("BILL");
		showBill.setFont(new Font("Times New Roman", Font.BOLD, 20));
		showBill.setBounds(240, 275, 161, 39);
		this.getContentPane().add(showBill);
		showBill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					ProductBLL pBLL = new ProductBLL();
					int id = getProduct2();
					int idO = getIdO2();
					Product product = pBLL.findProductbyID(id);
					Order o = orderBLL.findOrderbyID(idO);
					System.out.println(product.toString());
					float price = o.getQuantity() * product.getPrice();
					LocalDate currentDate = LocalDate.now();
					int year = currentDate.getYear();
					int month = currentDate.getMonthValue();
					int day = currentDate.getDayOfMonth();
					String date = day + "." + month + "." + year;
					Bill bill = new Bill(product.getId(), price, date);
					BillDAO billDAO = new BillDAO();
					BilBLL billBLL = new BilBLL(billDAO);
					billBLL.insert(bill);
					TableView billTable = new TableView(new Bill(id, price, date), orderBLL);
					billTable.setVisible(true);
				} catch (Exception exception) {
					showErorrMessage("WRONG!");
				}
			}
		});

		this.setVisible(false);
	}

	public int getIdO2() {
		return Integer.parseInt(idO2.getText());
	}

	public int getIdC2() {
		return Integer.parseInt(idC2.getText());
	}

	public int getProduct2() {
		return Integer.parseInt(product2.getText());
	}

	public int getQuantity2() {
		return Integer.parseInt(quantity2.getText());
	}

	/**
	 * Afiseaza un mesaj in interfata
	 * 
	 * @param message mesajul afisat
	 */
	public void showErorrMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "ERROR MESSAGE", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * goleste toate campurile interfetei
	 */
	public void refreshTotal() {
		idO2.setText(null);
		idC2.setText(null);
		product2.setText(null);
		quantity2.setText(null);
	}
}
