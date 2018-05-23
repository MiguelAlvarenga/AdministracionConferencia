package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Rol;

public class RolDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(Rol r) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Rol rol = r;

		em.persist(rol);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizar(Rol r) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Rol rol = em.find(Rol.class, r.getIdRol());
		rol.setNombre(r.getNombre());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Rol rol = em.find(Rol.class, id);

		em.remove(rol);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Rol> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Rol.findAll", Rol.class);
		List<Rol> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

}
