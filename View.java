package Presentation;

import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/** Aceasta clasa este facuta pentru alegerea operatiilor dorite
 * * /
 * @author ioana
 *
 */

public class View extends JFrame{
	
	private JLabel title;
	private JLabel title2;
	
	private JButton client;
	private JButton order;
	private JButton product;
	
	View() {
		this.setBounds(100, 100, 764, 530);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(null);
		
		JLabel title = new JLabel("ORDER MANAGEMENT");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 35));
		title.setBounds(171, 47, 399, 83);
		this.getContentPane().add(title);
		
		client = new JButton("Client");
		client.setFont(new Font("Times New Roman", Font.BOLD, 24));
		client.setBounds(54, 262, 197, 50);
		this.getContentPane().add(client);
		client.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ClientView client = new ClientView();
				client.setVisible(true);
			}
		});
		
		title2 = new JLabel("APPLICATION");
		title2.setHorizontalAlignment(SwingConstants.CENTER);
		title2.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 35));
		title2.setBounds(171, 122, 392, 65);
		this.getContentPane().add(title2);
		
		order = new JButton("Order");
		order.setFont(new Font("Times New Roman", Font.BOLD, 24));
		order.setBounds(492, 262, 197, 50);
		this.getContentPane().add(order);
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrderView order = new OrderView();
				order.setVisible(true);
			}
		});
		
		product = new JButton("Product");
		product.setFont(new Font("Times New Roman", Font.BOLD, 24));
		product.setBounds(270, 369, 197, 50);
		this.getContentPane().add(product);
		product.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductView product = new ProductView();
				product.setVisible(true);
			}
		});
		
		this.setVisible(true);
	}
	
}
