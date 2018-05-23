package test;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Rol;
import model.Usuario;

public class UsuarioTest {

	public static void main(String[] args) {
		System.out.println("\nInicio del Main");
		insertar();
		System.out.println("\nFin del Main");
	}

	private static void insertar() {

		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
			EntityManager em = emf.createEntityManager();
			Usuario usuario = new Usuario();
			Rol rol = new Rol();

			rol.setIdRol(1);

			usuario.setIdRol(rol); //id_Rol
			usuario.setNombre("Usuario Desde Eclipse");
			usuario.setUsuario("usuEclipse");
			usuario.setContrasena("12345678");
			usuario.setCorreo("eclipse@pedazo.com");
			usuario.setNuevo(true);

			em.getTransaction().begin();
			em.persist(usuario);
			em.getTransaction().commit();

			System.out.println("\nINFO: Ingreso de usuario exitoso.");

		} catch (Exception ex) {
			System.out.println("\nEXCEPTION: " + ex.getMessage());
		}
	}

}
