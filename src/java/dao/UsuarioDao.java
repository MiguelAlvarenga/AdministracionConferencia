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
import javax.persistence.TypedQuery;
import model.Programa;
import model.Rol;
import model.Usuario;
import model.UsuarioXPrograma;

/**
 *
 * @author Diego
 */
public class UsuarioDao {

	private EntityManagerFactory emf;
	private EntityManager em;

	public UsuarioDao() {
		emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
		em = emf.createEntityManager();
	}

	public void insertar(Usuario u, int idRol) {
                try{
                    em.getTransaction().begin();

		Usuario usuario = usuario = u;
                Rol rol = em.find(Rol.class, idRol);
                usuario.setIdRol(rol);
                
                Programa programa = em.find(Programa.class, 0);//El prorgama por defecto
		UsuarioXPrograma usuarioXPrograma = new UsuarioXPrograma();
		usuarioXPrograma.setIdPrograma(programa);
		usuarioXPrograma.setIdResponsable(usuario);
		usuarioXPrograma.setActivo(true);
                
                
		em.persist(usuario);
		em.persist(usuarioXPrograma);
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

	public void actualizar(Usuario u, int idRol) {

		em.getTransaction().begin();

		 
		 Usuario usuario = em.find(Usuario.class, u.getIdUsuario());
		 usuario.setNombre(u.getNombre());
		 usuario.setApellido(u.getApellido());
		 usuario.setCorreo(u.getCorreo());
                
		 System.out.println("contrasena: " + usuario.getContrasena());
                
		 Rol rol = em.find(Rol.class, idRol);
		 u.setIdRol(rol);
		 usuario.setIdRol(u.getIdRol());
		 usuario.setEstado(u.getEstado());
                 usuario.setUsuario(u.getUsuario());
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizarConContrasena(Usuario u, int idRol) {

		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, u.getIdUsuario());
		usuario.setNombre(u.getNombre());
		usuario.setApellido(u.getApellido());
		usuario.setCorreo(u.getCorreo());
		Rol rol = em.find(Rol.class, idRol);
		u.setIdRol(rol);
		usuario.setIdRol(u.getIdRol());
		usuario.setEstado(u.getEstado());
		usuario.setNuevo(u.getNuevo());
		usuario.setContrasena(u.getContrasena());
                usuario.setUsuario(u.getUsuario());
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {

		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, id);

		//em.remove(usuario);
                usuario.setEstado("E");
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Usuario> obtenerTodos() {

		em.getTransaction().begin();

		Query query = em.createNamedQuery("Usuario.findAll", Usuario.class);
		List<Usuario> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public void actualizarIntentos(Usuario u) {

		em.getTransaction().begin();

		Usuario usuario = em.find(Usuario.class, u.getIdUsuario());
		usuario.setIntentos(u.getIntentos());
		if (u.getIntentos() >= 3) {
			usuario.setEstado("B");
		}
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Usuario> obtenerNombreUsuarios(String nombreUsuario) {

		em.getTransaction().begin();

		TypedQuery<Usuario> sql = em.createQuery("SELECT u FROM Usuario u WHERE u.usuario LIKE :nombreUsuario ORDER BY u.usuario", Usuario.class);
		sql.setParameter("nombreUsuario", nombreUsuario + "%");
		List<Usuario> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}
}
