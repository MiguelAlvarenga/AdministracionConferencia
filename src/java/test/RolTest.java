package test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Rol;

public class RolTest {

	public static void main(String[] args) {

		System.out.println("\nInicio del Main");
		insertar();
		System.out.println("\nFin del Main");
	}

	private static void insertar() {

		try {

			EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
			EntityManager em = emf.createEntityManager();
			Rol rol = new Rol();

			rol.setNombre("Nuevo Rol5");

			em.getTransaction().begin();
			em.persist(rol);
			em.getTransaction().commit();

			System.out.println("\nINFO: Ingreso de rol exitoso.");

		} catch (Exception ex) {
			System.out.println("\nEXCEPTION: " + ex.getMessage());
		}
	}
}
