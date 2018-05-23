/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Estilo;

/**
 *
 * @author Diego
 */
public class EstiloDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(Estilo e) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Estilo estilo = e;

		em.persist(estilo);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizar(Estilo e) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Estilo estilo = em.find(Estilo.class, e.getIdEstilo());
		estilo.setNombre(e.getNombre());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Estilo estilo = em.find(Estilo.class, id);

		em.remove(estilo);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Estilo> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Estilo.findAll", Estilo.class);
		List<Estilo> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

}
