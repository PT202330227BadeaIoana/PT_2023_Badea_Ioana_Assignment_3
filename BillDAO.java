package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import model.Bill;

public class BillDAO {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	/**
	 *  Insereaza o factura in tabel
	 * @param bill factura introdusa
	 * @throws SQLException 
	 */
	public Bill insert(Bill bill) throws SQLException {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "INSERT INTO `sys`.`bill`(`id`, `price`, `date`) VALUES (?,?,?)";
		int result = -1;
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, bill.getId());
			statement.setFloat(2, bill.getPrice());
			statement.setString(3, bill.getDate());
			result = statement.executeUpdate();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return bill;
	}
}
