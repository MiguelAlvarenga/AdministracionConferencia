/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import dao.UsuarioDao;
import java.util.List;
import model.Rol;
import model.Usuario;

/**
 *
 * @author Diego
 */
public class GenerarUsuario {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Usuario usuario = new Usuario();
		Rol rol = new Rol();

		rol.setIdRol(1);

		usuario.setIdRol(rol); //id_Rol
                
		usuario.setNuevo(true);
		usuario.setContrasena("pizza");
		usuario.setNombre("Genesareno");
		usuario.setApellido("Morientes");
		usuario.setCorreo("eclipse@pedazo.com");
		usuario.setIntentos((short) 0);

		String nombre;
		String apellido;
		int correlativo;
		String[] listaApellidos;
		listaApellidos = usuario.getApellido().split(" ");

		nombre = usuario.getNombre().charAt(0) + "";
		apellido = listaApellidos[0];
		nombre = (nombre + apellido).toLowerCase();

		UsuarioDao daoUsuario = new UsuarioDao();
		List<Usuario> listaNombreUsuario = daoUsuario.obtenerNombreUsuarios(nombre);

		correlativo = listaNombreUsuario.size();
		System.out.println(correlativo);
		if (correlativo == 0) {
			nombre = nombre + "1";
		} else {

			int i;
			for (i = 1; i <= correlativo; i++) {

				if (!listaNombreUsuario.get(i - 1).getUsuario().equalsIgnoreCase(nombre + i)) {
					nombre = nombre + i;
					break;
				}
			}

			if (i > correlativo) {
				nombre = nombre + (correlativo + 1);
			}

		}

		System.out.println(nombre);

		daoUsuario.insertar(usuario, 1);
	}

}
