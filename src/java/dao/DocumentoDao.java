package dao;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Conferencia;
import model.Documento;
import org.apache.commons.io.FileUtils;

public class DocumentoDao {

    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
    private EntityManager em;

    public void insertar(Documento d, int idConferencia) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Conferencia conferencia = em.find(Conferencia.class, idConferencia);
        Documento documento = d;
        documento.setIdConferencia(conferencia);
        System.out.println("");
        em.persist(documento);

        em.getTransaction().commit();
        em.close();
    }

    public void actualizar(Documento d) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Documento documento = em.find(Documento.class, d.getIdDocumento());
        documento.setNombre(d.getNombre());

        em.getTransaction().commit();
        em.close();
    }

    public void eliminar(int id) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Documento documento = em.find(Documento.class, id);

        /*
        try {
                FileUtils.deleteQuietly(new File("/var/AdministradorConferencias/pdf/"+documento.getIdConferencia().getIdConferencia()+"/"+documento.getIdDocumento()+documento.getNombre()));
            } catch (Exception ex) {
                System.out.println("Error borrando archivo pdf:"+ex.toString());
            }
        */
        
        em.remove(documento);
        em.getTransaction().commit();
        em.close();
    }

    public List<Documento> obtenerTodos() {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        Query query = em.createNamedQuery("Documento.findAll", Documento.class);
        List<Documento> lista = query.getResultList();

        em.getTransaction().commit();
        em.close();
        return lista;
    }

    public List<Documento> obtenerTodosXConferencia(Conferencia conferencia) {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        TypedQuery<Documento> sql = em.createQuery("SELECT d FROM Documento d WHERE d.idConferencia = :conferencia ORDER BY d.idDocumento", Documento.class);
        sql.setParameter("conferencia", conferencia);
        List<Documento> lista = sql.getResultList();

        em.getTransaction().commit();
        em.close();
        return lista;
    }
    
    public Documento getDocumentoXId(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Documento> sql = em.createQuery("SELECT d FROM Documento d WHERE d.idDocumento=:idDocumento", Documento.class);
		sql.setParameter("idDocumento", id);

		Documento doc = sql.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return doc;
    }

}
