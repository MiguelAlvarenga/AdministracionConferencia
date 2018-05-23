/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import model.ConfiguracionRed;

/**
 *
 * @author garo1
 */
public class ConfiguracionRedDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void actualizar(ConfiguracionRed c) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		ConfiguracionRed configuracion = em.find(ConfiguracionRed.class, c.getIdConfiguracionRed());
		configuracion.setNombreDominio(c.getNombreDominio());
		configuracion.setCorreo(c.getCorreo());
		configuracion.setContrasena(c.getContrasena());
		configuracion.setPuerto(c.getPuerto());
		configuracion.setEncriptacion(c.getEncriptacion());
		configuracion.setIpBbb(c.getIpBbb());
		configuracion.setSalt(c.getSalt());
		configuracion.setLogouturl(c.getLogouturl());
                configuracion.setTransporte(c.getTransporte());

		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public ConfiguracionRed obtener() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<ConfiguracionRed> sql = em.createQuery("SELECT c FROM ConfiguracionRed c ORDER BY c.idConfiguracionRed ASC", ConfiguracionRed.class);

		ConfiguracionRed configuracion = sql.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();
		return configuracion;
	}

    public void actualizarPDF(ConfiguracionRed c) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        ConfiguracionRed configuracion = em.find(ConfiguracionRed.class, c.getIdConfiguracionRed());
        configuracion.setDocumento(c.getDocumento());
        configuracion.setNombre(c.getNombre());
        configuracion.setContenttype(c.getContenttype());

        em.getTransaction().commit();
        em.close();
        emf.close();
    }

}
