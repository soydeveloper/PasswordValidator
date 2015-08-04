package validator;

import java.util.ArrayList;

import portales.GitHub;
import db.Cliente;
import db.MySQLdb;
import db.Portal;

public class validator {

	public static void main(String[] argv) {

		MySQLdb db = new MySQLdb();	
		
		//SACAMOS LA INFORMACIÓN DE LA BASE DE DATOS Y GENERAMOS LA ESTRUCTURA DE DATOS
		ArrayList<Cliente> listaClientes = db.getListaClientes();

		//ITERAMOS CLIENTE A CLIENTE Y PORTAL A PORTAL
		for(Cliente cliente : listaClientes){
			System.out.println("Vamos a testear el usuario "+cliente.getNombre());
			for(Portal portal : cliente.getPortales()){
				System.out.println("\tTesteo en el portal "+portal.getNombre()+": "+((loginEnPortal(portal))?"OK":"FAIL! (Se envia un email a "+cliente.getEmail()+")"));

			}
		}
	}
	
	private static boolean loginEnPortal(Portal portal){
		
		boolean resultado = false;
		
		//PARA CADA PORTAL HAY UN CASO DIFERENTE
		switch (portal.getNombre()) {
		case "Github":
			GitHub git = new GitHub(portal.getUsername(), portal.getPassword(), portal.getLoginURL(), portal.getPostURL());
			resultado = git.login();
			break;
		default:
			break;
		}
		return resultado;
	}
}
