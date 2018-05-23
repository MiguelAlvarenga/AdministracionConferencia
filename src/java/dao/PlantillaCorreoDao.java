package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.PlantillaCorreo;

public class PlantillaCorreoDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(PlantillaCorreo pc) {
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			PlantillaCorreo plantillaCorreo = pc;
			em.persist(plantillaCorreo);
			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (Exception ex) {
			System.out.println("error:" + ex.toString());
			em.close();
			emf.close();
		}
	}

	public void actualizar(PlantillaCorreo pc) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		PlantillaCorreo plantillaCorreo = em.find(PlantillaCorreo.class, pc.getIdPlantillaCorreo());
		plantillaCorreo.setNombre(pc.getNombre());
		plantillaCorreo.setContenido(pc.getContenido());
		plantillaCorreo.setIdTipoPlantilla(pc.getIdTipoPlantilla());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		PlantillaCorreo plantillaCorreo = em.find(PlantillaCorreo.class, id);

		em.remove(plantillaCorreo);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<PlantillaCorreo> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("PlantillaCorreo.findAll", PlantillaCorreo.class);
		List<PlantillaCorreo> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

}
