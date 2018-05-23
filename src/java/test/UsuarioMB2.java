package test;

import bean.*;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.bean.ManagedBean;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import model.Usuario;

@ManagedBean
@ViewScoped
public class UsuarioMB2 implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuario = new Usuario();

	private List<Usuario> listaUsuarios;

	private int contador;

	public UsuarioMB2() {
		listaUsuarios = new ArrayList<>();
		contador = 12;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getListaUsuarios() {

		if (listaUsuarios != null) {
			for (int i = 0; i < listaUsuarios.size(); i++) {
				System.out.println(" " + i + " Nombre : " + listaUsuarios.get(i).getNombre());
				System.out.println(" " + i + " Usuario: " + listaUsuarios.get(i).getUsuario());
			}
			System.out.println("\n");
		}

		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

	public void agregar() {

		Usuario usu = new Usuario();
		usu.setIdUsuario(usuario.getIdUsuario());
		usu.setNombre(usuario.getNombre());
		usu.setCorreo(usuario.getCorreo());
		usu.setUsuario(usuario.getUsuario());

		listaUsuarios.add(usu);
	}

	public void eliminar(Usuario usu) {
		/*
		 System.out.println("OBJETO ENVIADO");
		 System.out.println(" Nombre : " + usu.getNombre());
		 System.out.println(" Usuario: " + usu.getUsuario());

		 System.out.println("INENTO; DE SEGURO FALSE");		
		 int posicion=listaUsuarios.indexOf(usu);
		 System.out.println(" Posición: "+posicion);			
		 */

		listaUsuarios.remove(usu);
	}

	public int contar() {
		return contador++;
	}

}
