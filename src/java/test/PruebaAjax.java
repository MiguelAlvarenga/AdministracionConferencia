package test;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class PruebaAjax {

	private String nombre;
	private String apellido;
	private int contador;

	public PruebaAjax() {
		apellido = "Sanabria";
		contador = 19;
	}

	public String getNombre() {
		return nombre + contador;

	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
		contador++;
		System.out.println("Hoa");
	}

	public String getApellido() {
		return nombre + " " + apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

}
