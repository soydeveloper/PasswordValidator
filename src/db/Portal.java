package db;

public class Portal {
	private int id;
	private String nombre;
	private String username;
	private String password;
	private String loginURL;
	private String postURL;
	
	public Portal(){}
	
	public Portal(int id, String nombre, String username, String password, String loginURL, String postURL){
		this.id = id;
		this.nombre = nombre;
		this.username = username;
		this.password = password;
		this.loginURL = loginURL;
		this.postURL = postURL;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginURL() {
		return loginURL;
	}

	public void setLoginURL(String loginURL) {
		this.loginURL = loginURL;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}
	
	
}
