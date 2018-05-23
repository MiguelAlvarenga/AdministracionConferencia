package bean;

import dao.EstadoConferenciaDao;
import dao.EstiloDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.EstadoConferencia;
import model.Estilo;

@ViewScoped
@ManagedBean
public class EstadoConferenciaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private EstadoConferencia estadoConferencia = new EstadoConferencia();
	private List<EstadoConferencia> lista = new ArrayList<>();

	//Propios del XHTML
	private String tituloModal;
	private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

	public EstadoConferenciaBean() {
		obtenerTodos();

	}

	public EstadoConferencia getEstadoConferencia() {
		return estadoConferencia;
	}

	public void setEstadoConferencia(EstadoConferencia estadoConferencia) {
		this.estadoConferencia = estadoConferencia;
	}

	public List<EstadoConferencia> getLista() {
		return lista;
	}

	public void setLista(List<EstadoConferencia> lista) {
		this.lista = lista;
	}

	public String getTituloModal() {
		if (nuevo) {
			tituloModal = "Nuevo Estado de Conferencia";
		} else {
			tituloModal = "Actualizar Estado de Conferencia";
		}
		return tituloModal;
	}

	public void setTituloModal(String tituloModal) {
		this.tituloModal = tituloModal;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public void guardar() {
		EstadoConferenciaDao daoEstadoConferencia = new EstadoConferenciaDao();
		if (nuevo) {
			daoEstadoConferencia.insertar(estadoConferencia);
		} else {
			daoEstadoConferencia.actualizar(estadoConferencia);
		}

	}

	public void eliminar() {

		EstadoConferenciaDao daoEstadoConferencia = new EstadoConferenciaDao();
		daoEstadoConferencia.eliminar(estadoConferencia.getIdEstadoConferencia());
		lista.remove(estadoConferencia);
		limpiar();

	}

	public void obtenerTodos() {

		EstadoConferenciaDao daoEstadoConferencia = new EstadoConferenciaDao();
		lista = daoEstadoConferencia.obtenerTodos();

	}

	public void limpiar() {
		estadoConferencia = new EstadoConferencia();
	}

	public void mensajeGlobalInformativo(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
	}

	public void mensajeGlobalError(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
	}

	public void mensajeGlobalAdvertencia(String mensaje) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
	}
	
}
