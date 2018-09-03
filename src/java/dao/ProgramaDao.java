
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Programa;
import model.Usuario;

public class ProgramaDao {

	private EntityManagerFactory emf;
	private EntityManager em;

	public ProgramaDao() {
		emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
		em = emf.createEntityManager();
	}

	public void insertar(Programa p, int idUsuario) {
		try {
			em.getTransaction().begin();

			Usuario usuario = em.find(Usuario.class, idUsuario);
			p.setIdUsuario(usuario);
			Programa programa = p;
			programa.setIdPrograma(null);
			em.persist(programa);

			em.getTransaction().commit();
			em.close();
			emf.close();

		} catch (Exception ex) {
			em.close();
			emf.close();
			throw ex;
		}
	}

	public void actualizar(Programa p) {
		try {
			em.getTransaction().begin();

			Programa programa = em.find(Programa.class, p.getIdPrograma());
			programa.setNombre(p.getNombre());
			programa.setActivo(p.getActivo());
			programa.setDescripcion(p.getDescripcion());

			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (Exception ex) {
			em.close();
			emf.close();
			throw ex;
		}
	}

	public void eliminar(int id) {
		try {
			em.getTransaction().begin();

			Programa programa = em.find(Programa.class, id);
			em.remove(programa);

			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (Exception ex) {
			em.close();
			emf.close();
			throw ex;
		}
	}

	public List<Programa> obtenerTodos() {

		List<Programa> lista;

		try {
			em.getTransaction().begin();

			TypedQuery<Programa> query = em.createQuery("SELECT p FROM Programa p WHERE p.idPrograma >= 1 AND p.activo = 'true' ORDER BY p.idPrograma DESC", Programa.class);
			lista = query.getResultList();

			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (Exception ex) {
			em.close();
			emf.close();
			throw ex;
		}

		return lista;
	}
        
        public List<Programa> obtenerTodosXUsuario(Usuario usuario) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Programa.findByUsuario", Programa.class);
                query.setParameter("idUsuario", usuario);
		List<Programa> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public Programa getProgramaXId(int idPrograma) {

		Programa p;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Query sql = em.createNamedQuery("Programa.findByIdPrograma", Programa.class);
			sql.setParameter("idPrograma", idPrograma);
			p = (Programa) sql.getSingleResult();

			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (Exception ex) {
			em.close();
			emf.close();
			throw ex;
		}

		return p;
	}
        
}
