package Presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLayer.ProductBLL;
import model.Product;

/**
 * Aceasta clasa este facuta pentru operatiile cu Product. * /
 * 
 * @author ioana
 *
 */
public class ProductView extends JFrame {

	private JTextField idP2;
	private JTextField stock2;
	private JTextField name2;
	private JTextField price2;

	private JButton insert;
	private JButton update;
	private JButton delete;
	private JButton findAll;

	private ProductBLL productBLL;

	/**
	 * Constructor pentru ProductView, seteaza elemetele utilizate. Face legatura cu
	 * ProductBLL.
	 */
	public ProductView() {

		productBLL = new ProductBLL();

		this.setBounds(100, 100, 734, 607);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		JLabel title = new JLabel("PRODUCT");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD, 35));
		title.setBounds(261, 11, 269, 44);
		this.getContentPane().add(title);

		JLabel idP = new JLabel("ID PRODUCT:");
		idP.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		idP.setBounds(27, 98, 131, 37);
		this.getContentPane().add(idP);

		JLabel stock = new JLabel("STOCK:");
		stock.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		stock.setBounds(27, 135, 131, 37);
		this.getContentPane().add(stock);

		JLabel name = new JLabel("NAME:");
		name.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		name.setBounds(27, 172, 131, 37);
		this.getContentPane().add(name);

		JLabel price = new JLabel("PRICE:");
		price.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		price.setBounds(27, 209, 131, 37);
		this.getContentPane().add(price);

		idP2 = new JTextField();
		idP2.setBounds(168, 98, 182, 37);
		this.getContentPane().add(idP2);
		idP2.setColumns(10);

		stock2 = new JTextField();
		stock2.setColumns(10);
		stock2.setBounds(168, 135, 182, 37);
		this.getContentPane().add(stock2);

		name2 = new JTextField();
		name2.setColumns(10);
		name2.setBounds(168, 172, 182, 37);
		this.getContentPane().add(name2);

		price2 = new JTextField();
		price2.setColumns(10);
		price2.setBounds(168, 209, 182, 37);
		this.getContentPane().add(price2);

		/**
		 * Insereaza un produs in baza de date.
		 */
		insert = new JButton("INSERT");
		insert.setFont(new Font("Times New Roman", Font.BOLD, 20));
		insert.setBounds(521, 98, 168, 37);
		this.getContentPane().add(insert);
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int idP = getIDP2();
					int stock = getStock2();
					String name = getName();
					float price = getPrice();
					Product p = new Product(idP, stock, name, price);
					productBLL.insertProduct(p);
					refreshTotal();
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		update = new JButton("UPDATE");
		update.setFont(new Font("Times New Roman", Font.BOLD, 20));
		update.setBounds(521, 144, 168, 37);
		this.getContentPane().add(update);
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getIDP2();
					Product p = productBLL.findProductbyID(id);
					String field = "";
					String value = "";
					if (getStock2() != 0) {
						field = "stock";
						value = String.valueOf(getStock2());
						productBLL.updateProduct(p, field, value, id);
					} else {
						System.out.println("da");
						if (!getName().equals("")) {
							field = "name";
							value = getName();
							productBLL.updateProduct(p, field, value, id);
						} else {
							System.out.println("da");
							if (getPrice() != 0) {
								field = "price";
								value = String.valueOf(getPrice());
								productBLL.updateProduct(p, field, value, id);
							}
						}
					}
					System.out.println("da");
					// productBLL.updateProduct(p, field, value, id);
					refreshTotal();
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Sterge un produs din baza de date .
		 */
		delete = new JButton("DELETE");
		delete.setFont(new Font("Times New Roman", Font.BOLD, 20));
		delete.setBounds(521, 192, 168, 37);
		this.getContentPane().add(delete);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getIDP2();
					Product p = productBLL.findProductbyID(id);
					productBLL.deleteProduct(id);
					refreshTotal();
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Afiseaza datele din tabelul product.
		 */
		findAll = new JButton("SHOW");
		findAll.setFont(new Font("Times New Roman", Font.BOLD, 20));
		findAll.setBounds(521, 245, 168, 37);
		this.getContentPane().add(findAll);
		findAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableView productTable = new TableView(new Product(), productBLL);
					productTable.setVisible(true);
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		this.setVisible(false);
	}

	/**
	 * Returneaza id-ul produsului din field-ul id-ului
	 * 
	 * @return id-ul produsului
	 */
	public int getIDP2() {
		return Integer.parseInt(idP2.getText());
	}

	/**
	 * Returneaza stock-ul produsului din field-ul stock-ului
	 * 
	 * @return stock-ul produsului
	 */
	public int getStock2() {
		return Integer.parseInt(stock2.getText());
	}

	/**
	 * Returneaza numele produsului din field-ul numelui
	 * 
	 * @return numele produsului
	 */
	public String getName() {
		return name2.getText();
	}

	/**
	 * Returneaza pretul produsului din field-ul pretului
	 * 
	 * @return pretul produsului
	 */
	public Float getPrice() {
		return Float.parseFloat(price2.getText());
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
		idP2.setText(null);
		stock2.setText(null);
		name2.setText(null);
		price2.setText(null);
	}
}
