package businessLayer;

import java.sql.SQLException;

import DataAccess.BillDAO;
import model.Bill;

public class BilBLL {
	private BillDAO billDAO;
	
	public BilBLL(BillDAO billDAO) {
		super();
		this.billDAO = billDAO;
	}

	/**
	 * Insereaza o factura
	 * @param b factura inserata
	 */
	public void insert(Bill b) {
		try {
			billDAO.insert(b);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
