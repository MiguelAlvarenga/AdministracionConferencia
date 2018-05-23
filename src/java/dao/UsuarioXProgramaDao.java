/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Programa;
import model.Usuario;
import model.UsuarioXPrograma;

/**
 *
 * @author Diego
 */
public class UsuarioXProgramaDao {

	private EntityManagerFactory emf;
	private EntityManager em;

	public UsuarioXProgramaDao() {
		emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
		em = emf.createEntityManager();
	}

	public void insertar(Programa p, Usuario u) {

		em.getTransaction().begin();

		UsuarioXPrograma usuXpro = new UsuarioXPrograma();

		usuXpro.setActivo(true);
		usuXpro.setIdResponsable(u);
		usuXpro.setIdPrograma(p);

		em.persist(usuXpro);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizar(UsuarioXPrograma uxp) {
		em.getTransaction().begin();
		UsuarioXPrograma uxprograma = em.find(UsuarioXPrograma.class, uxp.getIdUsuarioXPrograma());
		uxprograma.setActivo(uxp.getActivo());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<UsuarioXPrograma> obtenerTodosProgramasxUsuario(int idUsuario) {
		em.getTransaction().begin();

		TypedQuery<UsuarioXPrograma> sql = em.createQuery("SELECT u FROM UsuarioXPrograma u WHERE u.idResponsable.idUsuario = :idResponsable AND u.activo = :activo AND u.idPrograma<>0", UsuarioXPrograma.class);
		sql.setParameter("idResponsable", idUsuario);
		sql.setParameter("activo", true);
		List<UsuarioXPrograma> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public UsuarioXPrograma obtenerUXPxUsuarioPrograma(int idUsuario, int idPrograma) {
		em.getTransaction().begin();
		System.out.println("idUsuario:" + idUsuario);
		System.out.println("idPrograma:" + idPrograma);
		TypedQuery<UsuarioXPrograma> sql = em.createQuery("SELECT u FROM UsuarioXPrograma u WHERE u.idResponsable.idUsuario = :idResponsable AND u.idPrograma.idPrograma=:idPrograma", UsuarioXPrograma.class);
		sql.setParameter("idResponsable", idUsuario);
		sql.setParameter("idPrograma", idPrograma);
		UsuarioXPrograma uxp = sql.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return uxp;
	}

	public List<Programa> obtenerSoloProgramasxUsuario(int idUsuario) {
		em.getTransaction().begin();

		TypedQuery<Programa> sql = em.createQuery("SELECT p FROM Programa p WHERE EXISTS (select 'found' from UsuarioXPrograma u where p.idPrograma=u.idPrograma.idPrograma AND u.idResponsable.idUsuario=:idUsuario AND u.activo=true)", Programa.class);
		sql.setParameter("idUsuario", idUsuario);
		System.out.println("bean uxp: idusuario:" + idUsuario);
		//TypedQuery<Programa> sql = em.createQuery("SELECT p FROM Programa p WHERE EXISTS (select 'found' from UsuarioXPrograma u where p.idPrograma=u.idPrograma.idPrograma AND u.activo=true)",Programa.class);
		List<Programa> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

        public List<Usuario> obtenerTodosUsuariosHabilitados(int idPrograma) {
		em.getTransaction().begin();

		String query;
		query = "SELECT u.idResponsable FROM UsuarioXPrograma u WHERE u.activo = true AND u.idPrograma.idPrograma = :idPrograma AND u.idResponsable.estado != 'E'";
		
		TypedQuery<Usuario> sqluxp = em.createQuery(query, Usuario.class);
		sqluxp.setParameter("idPrograma", idPrograma);
		
		List<Usuario> lista = sqluxp.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;

	}

	public List<Usuario> obtenerTodosUsuariosDeshabilitados(int idPrograma) {
		em.getTransaction().begin();

		TypedQuery<Integer> sqluxp = em.createQuery("SELECT u.idResponsable.idUsuario FROM UsuarioXPrograma u WHERE u.idPrograma.idPrograma = :idPrograma AND u.activo= true", Integer.class);
		sqluxp.setParameter("idPrograma", idPrograma);
		List<Integer> listaDesHabilitados = sqluxp.getResultList();
		List<Usuario> lista;

		if (!listaDesHabilitados.isEmpty()) {

			TypedQuery<Usuario> sql = em.createQuery("SELECT u FROM Usuario u WHERE u.estado != 'E' AND u.idUsuario NOT IN (:listaDesHabilitados)", Usuario.class);
			sql.setParameter("listaDesHabilitados", listaDesHabilitados);
			lista = sql.getResultList();

		} else {

			Query query = em.createNamedQuery("Usuario.findAll", Usuario.class);
			lista = query.getResultList();

		}

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public List<UsuarioXPrograma> obtenerRegistro(int idUsuario, int idPrograma) {
		em.getTransaction().begin();

		TypedQuery<UsuarioXPrograma> sqluxp = em.createQuery("SELECT x FROM UsuarioXPrograma x WHERE x.idPrograma.idPrograma = :idPrograma AND x.idResponsable.idUsuario = :idResponsable", UsuarioXPrograma.class);
		sqluxp.setParameter("idPrograma", idPrograma);
		sqluxp.setParameter("idResponsable", idUsuario);
		List<UsuarioXPrograma> lista = sqluxp.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public UsuarioXPrograma getUsuarioXProgramaXId(int idUsuarioXPrograma) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query sql = em.createNamedQuery("UsuarioXPrograma.findByIdUsuarioXPrograma", UsuarioXPrograma.class);
		sql.setParameter("idUsuarioXPrograma", idUsuarioXPrograma);
		UsuarioXPrograma uxp = (UsuarioXPrograma) sql.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return uxp;
	}

}
