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
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/moduloseg", "root", "root1234");

		//updateClientNameById(connection);
		//deleteClientByDni(connection);
		updateClientNameById(connection, "Andrew", 1);
		updateClientLastnameById(connection, "Marley", 2);
		//readClientByUsername(connection, "RastaKing");
		//readAllClientsNonSensitiveData(connection);
		//deleteClientByUsername(connection, "EriVe");
		//loginByUsernameAndPass(connection, "RastaKing","420");
		//updatePassByUsername(connection, "RastaKing", "420420");
		//deleteClientById(connection, 6);

		connection.close();
	}

	//UPDATE CLIENT NAME BY ID - STATEMENT
	public static void updateClientNameById(Connection connection) throws Exception{
		Statement statement = connection.createStatement();
		int affectedRows = statement.executeUpdate("UPDATE CLIENTS SET name='Ragnar' WHERE id=1");
		ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENTS");
		while (resultSet.next()){
			System.out.println(resultSet.getString("name"));
		}
		System.out.println("Affected Rows: "+affectedRows);
	}
	//DELETE CLIENT BY ID - STATEMENT
	public static void deleteClientByDni(Connection connection) throws Exception{
		Statement statement = connection.createStatement();
		int affectedRows = statement.executeUpdate("DELETE FROM CLIENTS WHERE dni='44444444'");

		System.out.println("Affected Rows: "+affectedRows);
	}
	//UPDATE CLIENT NAME BY ID - PREPARED STATEMENT
	public static void updateClientNameById(Connection connection, String name, int id) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("Update CLIENTS SET name=? WHERE id=?");
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2,id);

		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("Affected Rows: "+affectedRows);

	}
	//UPDATE CLIENT LASTNAME BY ID - PREPARED STATEMENT
	public static void updateClientLastnameById(Connection connection, String lastname, int id) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("Update CLIENTS SET lastname=? WHERE id=?");
		preparedStatement.setString(1, lastname);
		preparedStatement.setInt(2,id);

		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("Affected Rows: "+affectedRows);

	}
	//DELETE CLIENT BY USERNAME - PREPARED STATEMENT
	public static void deleteClientByUsername(Connection connection, String user) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CLIENTS WHERE username=?");
		preparedStatement.setString(1, user);

		int affectedRows = preparedStatement.executeUpdate();
		System.out.println("Affected Rows: "+affectedRows);

	}
	//READ CLIENT BY USERNAME - PREPARED STATEMENT
	public static void readClientByUsername(Connection connection, String user) throws Exception{
		PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM CLIENTS WHERE username=?");
		preparedStatement.setString(1, user);

		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString("name") + " " +
								resultSet.getString("lastname") + " " +
								resultSet.getString("sex") + " " +
								resultSet.getString("address") + " " +
								resultSet.getString("dni"));
		}

	}


	//READ CLIENT DATA STORE PROCEDURE - CALLABLE STATEMENT
	public static void readAllClientsNonSensitiveData(Connection connection) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call getAllClients()}");
		ResultSet resultSet = callableStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString("name")+" "+
								resultSet.getString("lastname") + " " +
								resultSet.getString("dni") + " " +
								resultSet.getString("username") + " " +
								resultSet.getString("birthday"));
		}

	}
	//LOGIN BY USERNAME AND PASS - CALLABLE STATEMENT
	public static void loginByUsernameAndPass(Connection connection, String username, String pass) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call login(?,?)}");
		callableStatement.setString(1, username);
		callableStatement.setString(2, pass);
		ResultSet resultSet = callableStatement.executeQuery();
		while (resultSet.next()){
			System.out.println(resultSet.getString(1));
		}
	}
	//UPDATE PASS BY USERNAME - CALLABLE STATEMENT
	public static void updatePassByUsername(Connection connection, String username, String pass) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call updatePasswordByUsername(?,?,?)}");
		callableStatement.setString(1,username);
		callableStatement.setString(2, pass);
		callableStatement.registerOutParameter(3, Types.INTEGER);
		callableStatement.execute();
		int affectedRows = callableStatement.getInt(3);

		System.out.println(affectedRows);
	}
	//DELETE CLIENT BY ID - CALLABLE STATEMENT
	public static void deleteClientById(Connection connection, int id) throws Exception{
		CallableStatement callableStatement = connection.prepareCall("{call deleteClientById(?)}");
		callableStatement.setInt(1, id);
		int affectedRows = callableStatement.getInt(1);
		System.out.println(affectedRows);
	}

}
