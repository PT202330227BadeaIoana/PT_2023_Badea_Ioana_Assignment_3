package Presentation;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.management.ReflectionException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import businessLayer.ClientBLL;
import model.Client;

/**
 * Aceasta clasa este facuta pentru operatiile cu Clienti. Această clasă extinde
 * JFrame și furnizează interfața utilizatorului c.
 * 
 * @author ioana
 *
 */

public class ClientView extends JFrame {

	private JTextField id2;
	private JTextField name2;
	private JTextField email2;

	private JLabel title;
	private JLabel id;
	private JLabel name;
	private JLabel email;

	private JButton insert;
	private JButton delete;
	private JButton update;
	private JButton findAll;

	private ClientBLL clientBLL;

	/**
	 * Constructor pentru ClientView, seteaza elemetele utilizate. Face legatura cu
	 * CLientBLL.
	 */
	public ClientView() {

		clientBLL = new ClientBLL();

		this.setBounds(100, 100, 884, 595);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);

		title = new JLabel("CLIENT");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 35));
		title.setBounds(265, 42, 308, 60);
		this.getContentPane().add(title);

		id = new JLabel("ID:");
		id.setFont(new Font("Times New Roman", Font.BOLD, 20));
		id.setBounds(43, 154, 88, 33);
		this.getContentPane().add(id);

		id2 = new JTextField();
		id2.setBounds(104, 156, 106, 33);
		this.getContentPane().add(id2);
		id2.setColumns(10);

		name = new JLabel("NAME:");
		name.setFont(new Font("Times New Roman", Font.BOLD, 20));
		name.setBounds(278, 154, 88, 33);
		this.getContentPane().add(name);

		name2 = new JTextField();
		name2.setColumns(10);
		name2.setBounds(376, 156, 141, 33);
		this.getContentPane().add(name2);

		email = new JLabel("EMAIL:");
		email.setFont(new Font("Times New Roman", Font.BOLD, 20));
		email.setBounds(562, 154, 101, 33);
		this.getContentPane().add(email);

		email2 = new JTextField();
		email2.setColumns(10);
		email2.setBounds(673, 154, 141, 33);
		this.getContentPane().add(email2);

		/**
		 * Insereaza un client in baza de date.
		 */
		insert = new JButton("INSERT");
		insert.setFont(new Font("Times New Roman", Font.BOLD, 20));
		insert.setBounds(43, 215, 161, 39);
		this.getContentPane().add(insert);
		insert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					int id = getID();
					String name = getName();
					String email = getEmail();

					Client c = new Client(id, name, email);
			
					clientBLL.insertClient(c);
					refreshTotal();

				} catch (Exception exception) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Sterge un client din baza de date .
		 */
		delete = new JButton("DELETE");
		delete.setFont(new Font("Times New Roman", Font.BOLD, 20));
		delete.setBounds(260, 215, 161, 39);
		this.getContentPane().add(delete);
		delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getID();
					clientBLL.findClientByID(id);
					clientBLL.deleteClient(id);
					//refreshTotal();
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Update realizeaza modificari in tabelul "client" si il afiseaza intr-o noua
		 * fereastra.
		 */
		update = new JButton("UPDATE");
		update.setFont(new Font("Times New Roman", Font.BOLD, 20));
		update.setBounds(470, 215, 161, 39);
		update.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int id = getID();
					Client c = clientBLL.findClientByID(id);
					String field = "";
					String value = "";
					if (!getName().equals("")) {
						field = "name";
						value = getName();
					}
					if (!getEmail().equals("")) {
						field = "email";
						value = getEmail();
					}
					clientBLL.updateClient(c, field, value, id);
					refreshTotal();
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}
			}
		});

		/**
		 * Afiseaza datele din tabelul Client.
		 */
		findAll = new JButton("SHOW");
		findAll.setFont(new Font("Times New Roman", Font.BOLD, 20));
		findAll.setBounds(670, 215, 161, 39);
		this.getContentPane().add(findAll);
		findAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					TableView clientTable = new TableView(new Client(), clientBLL);
					clientTable.setVisible(true);
				} catch (Exception excpetion) {
					showErorrMessage("WRONG!");
				}

			}
		});

		this.getContentPane().add(update);
		this.setVisible(false);
	}

	/**
	 * Returneaza id-ul clientului din field-ul id-ului
	 * 
	 * @return id-ul clientului
	 */
	public int getID() {
		return Integer.parseInt(id2.getText());
	}

	/**
	 * Returneaza numele clientului din field-ul numelui
	 * 
	 * @return numele clientului
	 */
	public String getName() {
		return name2.getText();
	}

	/**
	 * Returneaza email-ul clientului din field-ul email-ului
	 * 
	 * @return email-ul clientului
	 */
	public String getEmail() {
		return email2.getText();
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
		id2.setText(null);
		name2.setText(null);
		email2.setText(null);
	}

}
