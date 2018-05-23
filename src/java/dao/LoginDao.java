/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Usuario;

/**
 *
 * @author garo1
 */
public class LoginDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public Usuario login(String usuario) {
		Usuario user;
		em = emf.createEntityManager();
		em.getTransaction().begin();
		user = em.createNamedQuery("Usuario.findByUsuario", Usuario.class)
				  .setParameter("usuario", usuario).getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return user;
	}
}
