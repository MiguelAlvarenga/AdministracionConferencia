package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.TipoPlantilla;

public class TipoPlantillaDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(TipoPlantilla tp) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TipoPlantilla tipoPlantilla = tp;
		em.persist(tipoPlantilla);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizar(TipoPlantilla tp) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TipoPlantilla tipoPlantilla = em.find(TipoPlantilla.class, tp.getIdTipoPlantilla());
		tipoPlantilla.setNombre(tp.getNombre());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TipoPlantilla tipoPlantilla = em.find(TipoPlantilla.class, id);

		em.remove(tipoPlantilla);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<TipoPlantilla> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("TipoPlantilla.findAll", TipoPlantilla.class);
		List<TipoPlantilla> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

}
