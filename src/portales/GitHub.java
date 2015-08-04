/**
 * 
 */
package portales;

import network.FormField;
import network.HttpTools;


/**
 * @author Andoni Lloret
 *
 */
public class GitHub {
	
	HttpTools http;
	
	String urlLogin="";
	String urlPost = "";
	String user = "";
	String passwd = "";
	
	public GitHub(String user, String passwd, String urlLogin, String urlPost){
		this.user = user;
		this.passwd = passwd;
		this.urlLogin = urlLogin;
		this.urlPost = urlPost;
		http = new HttpTools();
	}
	
	public boolean login(){
		
		String page;
		try {
			//RECOGEMOS EL HTML DE LA PÁGINA DE LOGIN DONDE SE ENCUENTRA EL FORMULARIO
			page = http.GetPageContent(urlLogin);
			
			//GENERAMOS LOS PARAMETROS NECESARIOS PARA ESE FORMULARIO
			FormField username = new FormField("login", user);
			FormField password = new FormField("password", passwd);	
			String postParams = http.getFormParams(page, "login", username, password);
			
			//HACEMOS EL POST
			page = http.sendPost(urlPost, postParams);
			
			//BUSCAMOS DENTRO DEL HTML DE VUELTA UN STRING DE ERROR DE LOGIN
			if(page.contains("Incorrect username or password")){
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
