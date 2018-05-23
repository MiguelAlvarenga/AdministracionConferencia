package bean;

import dao.PlantillaCorreoDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Conferencia;
import model.PlantillaCorreo;
import model.TipoPlantilla;

@ViewScoped
@ManagedBean
public class PlantillaCorreoBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private PlantillaCorreo plantillaCorreo = new PlantillaCorreo();
    private Conferencia conferencia = new Conferencia();
    private List<PlantillaCorreo> lista = new ArrayList<>();

    //Propios del XHTML
    private String tituloModal;
    private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

    //Otros
    private int idTipoPlantilla;
    private int idPlantillaCorreoAux;
    private int pantallaOrigen;

    public PlantillaCorreoBean() {
        obtenerTodos();
    }

    public PlantillaCorreo getPlantillaCorreo() {
        if (pantallaOrigen > 0) {
            return enriquecerContenidoCorreo();
        } else {
            return plantillaCorreo;
        }
    }

    public void setPlantillaCorreo(PlantillaCorreo plantillaCorreo) {
        this.plantillaCorreo = plantillaCorreo;
    }

    public List<PlantillaCorreo> getLista() {
        return lista;
    }

    public void setLista(List<PlantillaCorreo> lista) {
        this.lista = lista;
    }

    public String getTituloModal() {

        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("plantillaBtnNuevo");

        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("plantillaActualizar");

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

    public int getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setIdTipoPlantilla(int idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public int getIdPlantillaCorreoAux() {
        return idPlantillaCorreoAux;
    }

    public void setIdPlantillaCorreoAux(int idPlantillaCorreoAux) {
        this.idPlantillaCorreoAux = idPlantillaCorreoAux;
    }

    public Conferencia getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencia conferencia) {
        this.conferencia = conferencia;
    }

    public int getPantallaOrigen() {
        return pantallaOrigen;
    }

    public void setPantallaOrigen(int pantallaOrigen) {
        this.pantallaOrigen = pantallaOrigen;
    }

    public void guardar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        
        TipoPlantilla tipoPlantilla = new TipoPlantilla();
        tipoPlantilla.setIdTipoPlantilla(idTipoPlantilla);
        plantillaCorreo.setIdTipoPlantilla(tipoPlantilla);

        PlantillaCorreoDao dao = new PlantillaCorreoDao();
        if (nuevo) {
            if(plantillaCorreo.getNombre()==null){
            mensajeGlobalError(resourceBundle.getString("ErrorIns") + "; " + resourceBundle.getString("ErrorIns2"));
            return;
        }
            try {
                dao.insertar(plantillaCorreo);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " " + resourceBundle.getString("InsertExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + "; " + resourceBundle.getString("ErrorIns2"));
            }
        } else {
            if(plantillaCorreo.getNombre()==null){
            mensajeGlobalError(resourceBundle.getString("ErrorUpd") + "; " + resourceBundle.getString("ErrorIns2"));
            return;
        }
            try {
                dao.actualizar(plantillaCorreo);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " " + resourceBundle.getString("UpdateExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + "; " + resourceBundle.getString("ErrorIns2"));
            }

        }
    }

    public void eliminar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            PlantillaCorreoDao dao = new PlantillaCorreoDao();
            dao.eliminar(plantillaCorreo.getIdPlantillaCorreo());
            lista.remove(plantillaCorreo);
            limpiar();
            mensajeGlobalInformativo(resourceBundle.getString("Registro") + " " + resourceBundle.getString("DeleteExito"));
        } catch (Exception ex) {
            mensajeGlobalError(resourceBundle.getString("ErrorDlt"));
        }
    }

    public void limpiar() {
        plantillaCorreo = new PlantillaCorreo();
    }

    public final void obtenerTodos() {
        PlantillaCorreoDao dao = new PlantillaCorreoDao();
        lista = dao.obtenerTodos();
    }

    public final void cargarPlantillaCorreo() {
        plantillaCorreo = BuscarPlantilla();
    }

    public PlantillaCorreo BuscarPlantilla() {
        PlantillaCorreo pc = null;
        for (PlantillaCorreo elemento : lista) {
            if (elemento.getIdPlantillaCorreo().equals(idPlantillaCorreoAux)) {
                pc = elemento;
                break;
            }
        }
        return pc;
    }

    public PlantillaCorreo enriquecerContenidoCorreo() {

        PlantillaCorreo pc = new PlantillaCorreo();

        if (conferencia.getIdConferencia() == null) {
            return pc;
        }

        if (plantillaCorreo.getIdPlantillaCorreo() == null) {
            return pc;
        }

        pc.setIdPlantillaCorreo(plantillaCorreo.getIdPlantillaCorreo());
        pc.setIdTipoPlantilla(plantillaCorreo.getIdTipoPlantilla());
        pc.setNombre(plantillaCorreo.getNombre());
        pc.setContenido(plantillaCorreo.getContenido());

        //System.out.println("AFUERA 3 - " + conferencia.getIdUsuarioXPrograma().getIdPrograma().getNombre());
        if (conferencia.getIdUsuarioXPrograma().getIdPrograma().getNombre() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{NombrePrograma\\}", conferencia.getIdUsuarioXPrograma().getIdPrograma().getNombre()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{NombrePrograma\\}", "    "));
        }

        //System.out.println("AFUERA 4 - " + conferencia.getNombre());
        if (conferencia.getNombre() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{NombreConferencia\\}", conferencia.getNombre()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{NombreConferencia\\}", "    "));
        }

        //System.out.println("AFUERA 5 - " + conferencia.getFechaPonencia());
        if (conferencia.getFechaPonencia() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{FechaPonencia\\}", conferencia.getFechaPonencia().toString()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{FechaPonencia\\}", "    "));
        }

        //System.out.println("AFUERA 6 - " + conferencia.getHoraPonencia());
        if (conferencia.getHoraPonencia() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{HoraPonencia\\}", conferencia.getHoraPonencia()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{HoraPonencia\\}", "    "));
        }

        //System.out.println("AFUERA 7 - " + conferencia.getDuracion());
        if (conferencia.getDuracion() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Duracion\\}", conferencia.getDuracion()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Duracion\\}", "    "));
        }

        //System.out.println("AFUERA 8 - " + conferencia.getPonentes());
        if (conferencia.getPonentes() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Ponentes\\}", conferencia.getPonentes()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Ponentes\\}", "    "));
        }

        //System.out.println("AFUERA 9 - " + conferencia.getModeradores());
        if (conferencia.getModeradores() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Moderadores\\}", conferencia.getModeradores()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{Moderadores\\}", "    "));
        }

        //System.out.println("AFUERA 10 - " + conferencia.getModeratorurl());
        if (conferencia.getModeratorurl() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{EnlaceModerador\\}", conferencia.getModeratorurl()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{EnlaceModerador\\}", "    "));
        }

        //System.out.println("AFUERA 11 - " + conferencia.getAttendeeurl());
        if (conferencia.getAttendeeurl() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{EnlaceConferencia\\}", conferencia.getAttendeeurl()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{EnlaceConferencia\\}", "    "));
        }

        //System.out.println("AFUERA 12 - " + conferencia.getAttendeepw());
        if (conferencia.getAttendeepw() != null) {
            //System.out.println("Adentro 1 - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{ContraseñaConferenciaPrivada\\}", conferencia.getAttendeepw()));
            //System.out.println("Adentro 1 - Después");
        } else {
            //System.out.println("Adentro 2 - Else - Antes");
            pc.setContenido(pc.getContenido().replaceAll("\\{ContraseñaConferenciaPrivada\\}", "    "));
        }

        return pc;

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
