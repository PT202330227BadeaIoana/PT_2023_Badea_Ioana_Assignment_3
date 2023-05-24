package DataAccess;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Presentation.ClientView;

/**
 * Permite interactiunea cu baza de date si este clasa generica DAO.
 * 
 * @author ioana
 *
 */
public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	/**
	 * se instantiaza clasa
	 * 
	 * @author ioana
	 *
	 */
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * creeaza un select query
	 * 
	 * @param field field-ul care se cauta
	 * @return string cu select query
	 * @author ioana
	 *
	 */
	private String createSelectQuery(String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM `sys`.`");
		sb.append(type.getSimpleName());
		sb.append("` WHERE " + field + " =?;");
		return sb.toString();
	}

	/**
	 * creeaza un insert query
	 * 
	 * @param t obiectul care se inseraza
	 * @return string cu insert query
	 * @author ioana
	 */
	private String createInsertQuery(T t) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO `sys`.`");
		sb.append(type.getSimpleName() + "` (");
		for (Field f : t.getClass().getDeclaredFields()) {
			sb.append(f.getName());
			sb.append(", ");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.deleteCharAt(sb.length() - 1);
		sb.append(") VALUES(");
		sb.append("?,".repeat((int) Math.max(0., t.getClass().getDeclaredFields().length - 1)));
		sb.append("?);");
		return sb.toString();
	}

	/**
	 * creeaza un update query
	 * 
	 * @param t obiectul care trebuie modificat
	 * @return string cu update query
	 * @author ioana
	 *
	 */

	private String createUpdateQuery(T t, String field) {
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE `sys`.`");
		sb.append(type.getSimpleName());
		sb.append("` SET ");
		sb.append(field);
		sb.append("=?");
		sb.append(" WHERE id=?;");
		return sb.toString();
	}

	/**
	 * creeaza un delete query
	 * 
	 * @param id id-ul obiectului care trebuie sters
	 * @return string cu delete query
	 * @author ioana
	 *
	 */
	private String createDeleteQuery(int id) {
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE ");
		sb.append("FROM sys.");
		sb.append(type.getSimpleName());
		sb.append(" WHERE id=?;");
		return sb.toString();
	}

	/**
	 * creeaza un findAll query se selecteaza toate datele din tabel
	 * 
	 * @author ioana
	 *
	 */
	private String createFindAllQuery() {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" * ");
		sb.append(" FROM ");
		sb.append("`sys`.`" + type.getSimpleName() + "`;");
		return sb.toString();
	}

	/**
	 * se extrag toate datele dintr-un tabel
	 * 
	 * @author ioana
	 *
	 */
	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createFindAllQuery();
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**
	 * Se extrag datele in functie de id-ul introdus.
	 * 
	 * @author ioana
	 *
	 */
	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = createSelectQuery("id");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			resultSet = statement.executeQuery();
			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	/**Creeaza o lista de obiecte de tip T.
	 * @author ioana
	 *
	 */
	private List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();
		Constructor[] ctors = type.getDeclaredConstructors();
		Constructor ctor = null;
		for (int i = 0; i < ctors.length; i++) {
			ctor = ctors[i];
			if (ctor.getGenericParameterTypes().length == 0)
				break;
		}
		try {
			while (resultSet.next()) {
				ctor.setAccessible(true);
				T instance = (T) ctor.newInstance();
				for (Field field : type.getDeclaredFields()) {
					String fieldName = field.getName();
					Object value = resultSet.getObject(fieldName);
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * metoda prin care se insereaza un obiect in bd
	 * 
	 * @param t obiectul care trebuie inserat
	 * @return obiectul t
	 * @author ioana
	 *
	 */
	public T insert(T t) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createInsertQuery(t);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			int fieldNumber = 1;
			for (Field field : t.getClass().getDeclaredFields()) {
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), t.getClass());
				Method method = propertyDescriptor.getReadMethod();
				String param = method.invoke(t).toString();
				statement.setString(fieldNumber, param);
				fieldNumber++;
			}
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());

		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}

	/**
	 * metoda prin care se modifica un obiect
	 * 
	 * @param t     ogiectul de modificat
	 * @param field campul de editat
	 * @param value noua valoare introdusa
	 * @param id id-ul obiectului modificat
	 * @return obiectul t
	 * @author ioana
	 *
	 */
	public T update(T t, String field, String value, int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String updateQuery = createUpdateQuery(t, field);
		System.out.println("dao");
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(updateQuery);
			statement.setString(1,  value);
			statement.setInt(2, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, t.getClass().getName() + "DAO:update " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return t;
	}

	/**
	 * metoda prin care se sterge un obiect in functie de id
	 * 
	 * @param id id-ul obiectului
	 * @author ioana
	 *
	 */
	public void delete(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = createDeleteQuery(id);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}

}
