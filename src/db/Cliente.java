package db;

import java.util.ArrayList;

public class Cliente {
	private int id;
	private String nombre;
	private String email;
	private ArrayList<Portal> portales;
	
	public Cliente(){}
	public Cliente(int id, String nombre, String email, ArrayList<Portal> portales){
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.portales = portales;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public ArrayList<Portal> getPortales() {
		return portales;
	}
	public void setPortales(ArrayList<Portal> portales) {
		this.portales = portales;
	}
	
	
}
