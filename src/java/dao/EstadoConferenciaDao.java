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
import model.EstadoConferencia;

/**
 *
 * @author Diego
 */
public class EstadoConferenciaDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(EstadoConferencia ec) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		EstadoConferencia estadoConferencia = ec;

		em.persist(estadoConferencia);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizar(EstadoConferencia ec) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		EstadoConferencia estadoConferencia = em.find(EstadoConferencia.class, ec.getIdEstadoConferencia());
		estadoConferencia.setNombre(ec.getNombre());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		EstadoConferencia estadoConferencia = em.find(EstadoConferencia.class, id);

		em.remove(estadoConferencia);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<EstadoConferencia> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("EstadoConferencia.findAll", EstadoConferencia.class);
		List<EstadoConferencia> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

}
