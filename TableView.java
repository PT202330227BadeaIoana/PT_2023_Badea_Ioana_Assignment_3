package Presentation;

import java.awt.Dimension;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Clasa pentru crearea tabelului
 * @author ioana
 *
 */
public class TableView extends JFrame {
	private JPanel panel;
	private JTable table;

	/**Se creeaza constructorul
	 * 
	 * @param o obiect tip model
	 * @param o2 obict tip logica
	 */
	public TableView(Object o, Object o2) {

		panel = new JPanel();
		this.setTitle("TABLE");

		retrive(o, o2);
		
		/**
		 * se creeaza scrollPane pentru tabel
		 */
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setVisible(true);
		panel.add(scrollPane);

		this.add(panel);
		this.setSize(700, 400);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		this.setVisible(true);

	}

	public void setTable(JTable t) {
		this.table = t;
	}
	
	/**
	 * Apelul metodei reflection pentru a crea tabelul
	 * @param o obiectul
	 * @param o2 BLL-ul obiectului
	 * @return
	 */
	public void retrive(Object o, Object o2) {
		String[] header = create(o);
		String[][] data = populate(o2);
		DefaultTableModel model = new DefaultTableModel(data, header);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(new Dimension(550, 200));
		table.setFillsViewportHeight(true);
		
	}
	
	/**Pe baza parametrului primit se face header-ul tabelului si se returneaza tabelul cu toate campurile
	 * @param o obiectul
	 * @return o lista de header
	 */
	public static String[] create(Object o) {
		List<String> fields = new ArrayList<>();		
		for(Field f : o.getClass().getDeclaredFields()) {
			fields.add(f.getName());
		}
		String[] header = new String[ fields.size()];
		fields.toArray(header);
		return header;
	}
	
	/**
	 * Populeaza tabelul cu datele obtinute din metoda findALl.
	 * @param o obiectul BLL 
	 * @return o matrice cu datele din tabel
	 */
	public static String[][] populate(Object o){
		String[][]data=null;
		try {
			Method[] methods = o.getClass().getDeclaredMethods();
			Method m = null;
			for(Method m2: methods) {
				if(m2.getName().equals("findAll")) {
					m = m2;
					break;
				}
			}
			Object res = m.invoke(o);
			data = (String[][])res;
		} catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
		return data;
	}
}
