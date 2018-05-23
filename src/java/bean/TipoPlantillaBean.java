package bean;

import dao.TipoPlantillaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.TipoPlantilla;

@ViewScoped
@ManagedBean
public class TipoPlantillaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private TipoPlantilla tipoPlantilla = new TipoPlantilla();
    private List<TipoPlantilla> lista = new ArrayList<>();

    //Propios del XHTML
    private String tituloModal;
    private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

    public TipoPlantillaBean() {
        obtenerTodos();
    }

    public TipoPlantilla getTipoPlantilla() {
        return tipoPlantilla;
    }

    public void setTipoPlantilla(TipoPlantilla tipoPlantilla) {
        this.tipoPlantilla = tipoPlantilla;
        System.out.println("prueba: " + this.tipoPlantilla);
    }

    public List<TipoPlantilla> getLista() {
        return lista;
    }

    public void setLista(List<TipoPlantilla> lista) {
        this.lista = lista;
    }

    public String getTituloModal() {
        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("plantillaTPBtnNuevo");

        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("plantillaActializarTP");

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
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        TipoPlantillaDao daoTipoPlantilla = new TipoPlantillaDao();
        if (nuevo) {
            if (tipoPlantilla.getNombre() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + tipoPlantilla.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }

            try {
                daoTipoPlantilla.insertar(tipoPlantilla);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + tipoPlantilla.getNombre() + "' " + resourceBundle.getString("InsertExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + tipoPlantilla.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
        } else {
            if (tipoPlantilla.getNombre() == null) {
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + tipoPlantilla.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
                return;
            }
            try {
                daoTipoPlantilla.actualizar(tipoPlantilla);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + tipoPlantilla.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + tipoPlantilla.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }

        }

    }

    public void eliminar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            TipoPlantillaDao daoTipoPlantilla = new TipoPlantillaDao();
            daoTipoPlantilla.eliminar(tipoPlantilla.getIdTipoPlantilla());
            lista.remove(tipoPlantilla);
            limpiar();
            mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + tipoPlantilla.getNombre() + "' " + resourceBundle.getString("DeleteExito"));
        } catch (Exception ex) {
            mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + tipoPlantilla.getNombre() + "'");
        }

    }

    public void obtenerTodos() {

        TipoPlantillaDao daoTipoPlantilla = new TipoPlantillaDao();
        lista = daoTipoPlantilla.obtenerTodos();

    }

    public void limpiar() {
        tipoPlantilla = new TipoPlantilla();
    }

    public void mensajeGlobalInformativo(String mensaje) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
	}

	public void mensajeGlobalError(String mensaje) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
	}

	public void mensajeGlobalAdvertencia(String mensaje) {
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
	}
}
