package pe.isil.moduloseguridad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

@SpringBootApplication
public class ModuloseguridadApplication {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(ModuloseguridadApplication.class, args);
		System.out.println("Initializing...");

		//Load Driver
		Class.forName("com.mysql.cj.jdbc.Driver");

		//Create Connexion
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moduloseg2", "root", "root1234");

		//testStatement(connection);
		//testPreparedStatement(connection);
		//testPreparedStatementResult(connection);
		//testCallableStatement(connection);
		loginByUsernameAndPass(connection, "474747","123456");
		//updatePassByUsername(connection, "RastaKing", "420420");

		connection.close();
	}

	public static void testStatement(Connection connection) throws Exception{
		//Create statement
		Statement statement = connection.createStatement();

		//Execute sentence
		int affectedRows = statement.executeUpdate("UPDATE USERS SET name='Jose' WHERE id=1");
		ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");
		while (resultSet.next()){
			System.out.println(resultSet.getString("name"));
		}
		System.out.println("Affected Rows: "+affectedRows);
	}

	//ACTUALIZAR REGISTRO
	public static void testPreparedStatement(Connection connection) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("Update USERS SET name=? WHERE id=?");
		preparedStatement.setString(1, "Eduardo");
		preparedStatement.setInt(2,3);

		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("Affected Rows: "+affectedRows);

	}
	public static void testPreparedStatementResult(Connection connection) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE username=?");
		preparedStatement.setString(1, "RastaKing");

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString("name")+" "+resultSet.getString("lastname"));
		}

	}

	// LISTAR CON PROCEDIMIENTOS ALMACENADOS
	public static void testCallableStatement(Connection connection) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call getAllUsers()}");
		ResultSet resultSet = callableStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString("name")+" "+resultSet.getString("lastname"));
		}

	}

	public static void loginByUsernameAndPass(Connection connection, String username, String pass) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call login(?,?)}");
		callableStatement.setString(1, username);
		callableStatement.setString(2, pass);
		ResultSet resultSet = callableStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString(1));
		}
	}

	public static void updatePassByUsername(Connection connection, String username, String pass) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call updatePasswordByUsername(?,?,?)}");
		callableStatement.setString(1,username);
		callableStatement.setString(2, pass);
		callableStatement.registerOutParameter(3, Types.INTEGER);
		callableStatement.execute();
		int affectedRows = callableStatement.getInt(3);

		System.out.println(affectedRows);
	}

}
