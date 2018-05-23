/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

import model.Conferencia;
import model.EstadoConferencia;
import model.Usuario;
import model.UsuarioXPrograma;
import org.w3c.dom.DOMException;
import utils.BbbCalls;

/**
 *
 * @author garo1
 */
public class ConferenciaDao {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("AdministradorConferenciasUP");
	private EntityManager em;

	public void insertar(Conferencia c, int idEstadoConferencia, int idUsuarioXPrograma) {
		try {
			Calendar fechaActual = Calendar.getInstance();

			System.out.println("estado:" + idEstadoConferencia);
			em = emf.createEntityManager();
			em.getTransaction().begin();
			EstadoConferencia estadoConferencia = em.find(EstadoConferencia.class, idEstadoConferencia);
			c.setIdEstadoConferencia(estadoConferencia);
			UsuarioXPrograma usuarioXPrograma = em.find(UsuarioXPrograma.class, idUsuarioXPrograma);
			c.setIdUsuarioXPrograma(usuarioXPrograma);
			c.setFechaCreacion(fechaActual.getTime());
                        c.setDuracion(c.getDuracion()==null? "480":c.getDuracion());
			Conferencia conferencia = c;

			System.out.println("1:" + conferencia.getIdConferencia());
			em.persist(conferencia);
			System.out.println("2:" + conferencia.getIdConferencia());

			/**
			 * llamada al api*
			 */
			String xml = BbbCalls.crearConferencia(conferencia.getNombre(),
					  String.valueOf(conferencia.getIdConferencia()),
					  conferencia.getDuracion(),
					  String.valueOf(conferencia.getGrabacion()));

			/*Configuracion conf=*/
			Document doc = BbbCalls.parseXml(xml);
			if (doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS")) {
				System.out.println("Creacion exitosa para conf=" + doc.getElementsByTagName("meetingID").item(0).getTextContent().trim());
				conferencia.setAttendeepw(doc.getElementsByTagName("attendeePW").item(0).getTextContent().trim());
				conferencia.setModeratorpw(doc.getElementsByTagName("moderatorPW").item(0).getTextContent().trim());
				conferencia.setDuracion(doc.getElementsByTagName("duration").item(0).getTextContent().trim());
				conferencia.setModeratorurl(BbbCalls.getModeratorURL(String.valueOf(conferencia.getIdConferencia()), conferencia.getModeratorpw()));
				//if (conferencia.getTipoConferencia()) {//es privada
					String check = BbbCalls.getChecksum(String.valueOf(conferencia.getIdConferencia()), conferencia.getNombre());
					System.out.println("CheckSum:" + check);
					conferencia.setChecksumattendee(check);
					conferencia.setAttendeeurl(BbbCalls.getAttendeeURL(String.valueOf(conferencia.getIdConferencia()), check));
				//}
			} else {
				System.out.println("error de algun tipo");
			}
                        /*
                        boolean success = (new File("/var/AdministradorConferencias/pdf/"+conferencia.getIdConferencia())).mkdir();
                        if(success){
                            System.out.println("Ruta de archivos creada correctamente");
                        }*/

			em.getTransaction().commit();
			em.close();
			emf.close();
		} catch (ParserConfigurationException | IOException | org.xml.sax.SAXException | DOMException ex) {
			System.out.println("error:" + ex.toString());
			em.close();
			emf.close();
		} catch (Exception ex) {
			System.out.println("error:" + ex.toString());
			em.close();
			emf.close();
		}
	}

	public void actualizar(Conferencia c, int idEstadoConferencia, int idUsuarioXPrograma) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		EstadoConferencia estadoConferencia = em.find(EstadoConferencia.class, idEstadoConferencia);
		c.setIdEstadoConferencia(estadoConferencia);
		UsuarioXPrograma usuarioXPrograma = em.find(UsuarioXPrograma.class, idUsuarioXPrograma);
		c.setIdUsuarioXPrograma(usuarioXPrograma);

		Conferencia conferencia = em.find(Conferencia.class, c.getIdConferencia());
		conferencia.setNombre(c.getNombre());
		conferencia.setDescripcion(c.getDescripcion());
		conferencia.setDuracion(c.getDuracion());
		conferencia.setFechaPonencia(c.getFechaPonencia());
                conferencia.setHoraPonencia(c.getHoraPonencia());
		conferencia.setIdEstadoConferencia(c.getIdEstadoConferencia());
		conferencia.setIdUsuarioXPrograma(c.getIdUsuarioXPrograma());
		conferencia.setModeradores(c.getModeradores());
		conferencia.setPonentes(c.getPonentes());
		conferencia.setPuntoInicio(c.getPuntoInicio());
		conferencia.setTipoConferencia(c.getTipoConferencia());
		conferencia.setGrabacion(c.getGrabacion());
		conferencia.setRecordingurl(c.getRecordingurl());
                    
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void actualizarEstadoConferencia(Conferencia c, int idEstadoConferencia) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		EstadoConferencia estadoConferencia = em.find(EstadoConferencia.class, idEstadoConferencia);
		c.setIdEstadoConferencia(estadoConferencia);

		Conferencia conferencia = em.find(Conferencia.class, c.getIdConferencia());

		conferencia.setIdEstadoConferencia(c.getIdEstadoConferencia());
                
                if(idEstadoConferencia>=3){//eliminandoArchivos si es que los hay
                    BbbCalls.eliminarArchivos(conferencia);
                }
                
                
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public void eliminar(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Conferencia conferencia = em.find(Conferencia.class, id);

            /*    
            try {
                FileUtils.deleteDirectory(new File("/var/AdministradorConferencias/pdf/"+conferencia.getIdConferencia()));
            } catch (Exception ex) {
                System.out.println("Error borrando carpeta de pdf:"+ex.toString());
            }*/
                
		em.remove(conferencia);
		em.getTransaction().commit();
		em.close();
		emf.close();
	}

	public List<Conferencia> obtenerTodos() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		Query query = em.createNamedQuery("Conferencia.findAll", Conferencia.class);
		List<Conferencia> lista = query.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public List<Conferencia> obtenerTodosIndex() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.tipoConferencia=FALSE AND c.idEstadoConferencia IN (1,2)", Conferencia.class);
		List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public List<Conferencia> obtenerTodosIndex(int id, String check) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		//TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.tipoConferencia=FALSE OR (c.idConferencia=:id AND c.checksumattendee=:check) AND c.idEstadoConferencia IN (1,2) ORDER BY c.tipoConferencia DESC", Conferencia.class);
                TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c "
                        + "WHERE (c.tipoConferencia=FALSE AND c.idEstadoConferencia IN (1,2)) "
                        + "or (c.idConferencia=:id AND c.checksumattendee=:check AND c.idEstadoConferencia IN (1,2)) "
                        + "ORDER BY c.tipoConferencia DESC", Conferencia.class);
		sql.setParameter("id", id);
		sql.setParameter("check", check);
		List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}
        
        public List<Conferencia> obtenerTodasGrabaciones() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

                

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.idEstadoConferencia=4 AND c.recordingurl IS NOT NULL", Conferencia.class);
                List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public List<Conferencia> obtenerGrabaciones() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

                

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.idEstadoConferencia=4 AND c.tipoConferencia=FALSE AND c.recordingurl IS NOT NULL", Conferencia.class);
                List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}

	public Conferencia getConferenciaXId(int id) {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.idConferencia= :idConferencia", Conferencia.class);
		sql.setParameter("idConferencia", id);

		Conferencia conf = sql.getSingleResult();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return conf;
	}

	public List<Conferencia> obtenerPendientesGrabacion() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.grabacion=TRUE AND c.recordingurl=NULL AND c.idEstadoConferencia>1", Conferencia.class);
		List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}
        
        public List<Conferencia> obtenerEmision() {
		em = emf.createEntityManager();
		em.getTransaction().begin();

		TypedQuery<Conferencia> sql = em.createQuery("SELECT c FROM Conferencia c WHERE c.idEstadoConferencia=2", Conferencia.class);
		List<Conferencia> lista = sql.getResultList();

		em.getTransaction().commit();
		em.close();
		emf.close();

		return lista;
	}
        

}
