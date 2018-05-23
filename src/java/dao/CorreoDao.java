package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Correo;
import model.Conferencia;

public class CorreoDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(Correo c) {
             try{
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Correo correo = c;
		em.persist(correo);
		em.getTransaction().commit();
		em.close();
		emf.close();
             }
               catch (Exception ex) {
			System.out.println("error:" + ex.toString());
			em.close();
			emf.close();
		}
	}

	public void actualizar(Correo c) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Correo correo = em.find(Correo.class, c.getIdCorreo());
		correo.setIdPlantillaCorreo(c.getIdPlantillaCorreo());
		correo.setIdConferencia(c.getIdConferencia());
		correo.setContenido(c.getContenido());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Correo correo = em.find(Correo.class, id);

		em.remove(correo);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Correo> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Correo.findAll", Correo.class);
		List<Correo> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public List<Correo> obtenerTodosXConferencia(Conferencia conferencia) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Correo> query = em.createQuery("SELECT c FROM Correo c WHERE c.idConferencia= :idConferencia", Correo.class);
		query.setParameter("idConferencia", conferencia);

		List<Correo> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public Correo getCorreoXId(int idCorreo) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Correo> query = em.createQuery("SELECT c FROM Correo c WHERE c.idCorreo= :idCorreo", Correo.class);
		query.setParameter("idCorreo", idCorreo);

		Correo c = query.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return c;
	}

}
