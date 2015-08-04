package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class MySQLdb {

	private Connection connection;
	private String host="localhost";
	private String puerto="3306";
	private String usuarioDB="cliente";
	private String password="1234";

	public MySQLdb(){
		makeConnectionToDB();
	}

	public Connection getConnection() {
		return connection;
	}

	private void makeConnectionToDB(){
		//CARGAMOS EL DRIVER PARA CONEXIONES CON MYSQL. JDBC
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el driver JDBC");
		}

		//CREAMOS UNA CONEXION
		try {
			connection = DriverManager.getConnection("jdbc:mysql://"+host+":"+puerto+"/password_validation",usuarioDB,password);
		} catch (SQLException e) {
			System.out.println("No se ha podido conectar. Revise sus credenciales");
		}
	}
	
	public ArrayList<Cliente> getListaClientes(){
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		try {

			MySQLdb db = new MySQLdb();

			String query = 	"SELECT cliente.id, cliente.nombre, cliente.email,portal.id, portal.nombre, portal.loginURL, portal.postURL, cliente_has_portal.username, cliente_has_portal.password"+
							" FROM cliente"+
							" INNER JOIN cliente_has_portal ON cliente_has_portal.cliente_id = cliente.id"+
							" INNER JOIN portal ON portal.id = cliente_has_portal.portal_id";

			Statement st = db.getConnection().createStatement();
			ResultSet rs = st.executeQuery(query);


			//CON EL RESULTADO CREAMOS UN OBJECTO ARRAYLIST CON CLIENTES EN LA DB
			int idCliente = 0;
			Cliente cliente = new Cliente();
			
			while (rs.next())
			{
				if(idCliente != rs.getInt("cliente.id")){
					cliente = new Cliente();
					cliente.setId(rs.getInt("cliente.id"));
					cliente.setNombre(rs.getString("cliente.nombre"));
					cliente.setEmail(rs.getString("email"));
					idCliente = rs.getInt("cliente.id");
					
					listaClientes.add(cliente);
					cliente.setPortales(new ArrayList<Portal>());
				}
				
				cliente.getPortales().add(new Portal(	rs.getInt("portal.id"), 
														rs.getString("portal.nombre"), 
														rs.getString("username"),
														rs.getString("password"), 
														rs.getString("loginURL"), 
														rs.getString("postURL")));

			}
			st.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return listaClientes;
	}
}
