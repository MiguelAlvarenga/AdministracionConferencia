package bean;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.UsuarioXPrograma;

@ManagedBean
@ViewScoped
public class UsuarioXProgramaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private UsuarioXPrograma usuarioXPrograma = new UsuarioXPrograma();
	private List<UsuarioXPrograma> lista = new ArrayList<>();

	public UsuarioXPrograma getUsuarioXPrograma() {
		return usuarioXPrograma;
	}

	public void setUsuarioXPrograma(UsuarioXPrograma usuarioXPrograma) {
		this.usuarioXPrograma = usuarioXPrograma;
	}

	public List<UsuarioXPrograma> getLista() {
		return lista;
	}

	public void setLista(List<UsuarioXPrograma> lista) {
		this.lista = lista;
	}

	public void insertar() {
	}

	public void actualizar(UsuarioXPrograma usuario) {
	}

	public void eliminar(UsuarioXPrograma usuario) {
	}

	public void obtenerTodos() {
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
