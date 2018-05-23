package bean;

import dao.RolDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Rol;

@ViewScoped
@ManagedBean
public class RolBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Rol rol = new Rol();
	private List<Rol> lista = new ArrayList<>();

	//Propios del XHTML
	private String tituloModal;
	private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

	public RolBean() {
		obtenerTodos();
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Rol> getLista() {

		return lista;
	}

	public void setLista(List<Rol> lista) {
		this.lista = lista;
	}

	public String getTituloModal() {
		if (nuevo) {
			tituloModal = "Nuevo Rol";
		} else {
			tituloModal = "Actualizar Rol";
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
		RolDao daoRol = new RolDao();
		if (nuevo) {
			daoRol.insertar(rol);
		} else {
			daoRol.actualizar(rol);
		}
	}

	public void eliminar() {
		RolDao daoRol = new RolDao();
		daoRol.eliminar(rol.getIdRol());
		lista.remove(rol);
		limpiar();
	}

	public void limpiar() {
		rol = new Rol();
	}

	public final void obtenerTodos() {
		RolDao daoRol = new RolDao();
		lista = daoRol.obtenerTodos();
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
