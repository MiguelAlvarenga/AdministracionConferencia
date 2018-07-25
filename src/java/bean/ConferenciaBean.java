package bean;

import dao.ConferenciaDao;
import dao.ConfiguracionRedDao;
import dao.EstadoConferenciaDao;
import dao.UsuarioXProgramaDao;
import dao.UsuarioDao;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Conferencia;
import model.ConfiguracionRed;
import model.EstadoConferencia;
import model.Programa;
import model.Usuario;
import utils.BbbCalls;

@ViewScoped
@ManagedBean
public class ConferenciaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Conferencia conferencia = new Conferencia();
    private List<Conferencia> lista = new ArrayList<>();
    private List<Programa> listaProgramasxUsuario = new ArrayList<>();
    private List<Usuario> ListaUsuariosAsociados = new ArrayList();
    private List<EstadoConferencia> listaEstadosConf = new ArrayList<>();
    private int idProgramaAux;
    private int idProgramaAux2;
    private int idEstadoAux;
    private int idUsuarioAux;

    private String url;
    private String usuarioBbb;
    private int cantidadParticipantes;

    private String tituloModal;
    private String fecha;
    private boolean nuevo = true;
    private boolean establecerDuracion = false;

    public ConferenciaBean() {

        obtenerTodos();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getResource(String resource) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        return resourceBundle.getString(resource);
    }

    public String getFechaStr(Conferencia conferencia) {
        return new SimpleDateFormat("dd/MM/yyyy").format(conferencia.getFechaPonencia()) + (conferencia.getHoraPonencia() == null ? "" : " " + conferencia.getHoraPonencia());
    }
    
    public String getFechaCreStr(Conferencia conferencia) {
        return new SimpleDateFormat("dd/MM/yyyy").format(conferencia.getFechaCreacion()) + (conferencia.getHoraCreacion()== null ? "" : " " + conferencia.getHoraCreacion());
    }

    public void setFechaDate(Date fecha) {
        if (fecha != null) {
            this.fecha = new SimpleDateFormat("dd/MM/yyyy").format(fecha);
        } else {
            this.fecha = "";
        }

    }

    public boolean isEstablecerDuracion() {
        return establecerDuracion;
    }

    public void setEstablecerDuracion(boolean establecerDuracion) {
        this.establecerDuracion = establecerDuracion;
    }

    public String getUrl() {
        return url;
    }

    public int getCantidadParticipantes() {
        return cantidadParticipantes;
    }

    public void setCantidadParticipantes(int cantidadParticipantes) {
        this.cantidadParticipantes = cantidadParticipantes;
    }

    public String getUsuarioBbb() {
        return usuarioBbb;
    }

    public void setUsuarioBbb(String usuarioBbb) {
        this.usuarioBbb = usuarioBbb;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Conferencia getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencia conferencia) {
        this.conferencia = conferencia;
    }

    public List<Conferencia> getLista() {
        //ConferenciaDao cb = new ConferenciaDao();
        //UsuarioDao ud = new UsuarioDao();
        //this.lista = cb.obtenerTodosXUsuario(ud.obtenerUsuario(13));
        return lista;
    }

    public void setLista(List<Conferencia> lista) {
        this.lista = lista;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public List<Programa> getListaProgramasxUsuario(int idUsuario) {
        UsuarioXProgramaDao daoUsuarioXPrograma = new UsuarioXProgramaDao();
        listaProgramasxUsuario = daoUsuarioXPrograma.obtenerSoloProgramasxUsuario(idUsuario);
        return listaProgramasxUsuario;
    }

    public void setListaProgramasxUsuario(List<Programa> listaProgramasxUsuario) {
        this.listaProgramasxUsuario = listaProgramasxUsuario;
    }

    public List<Usuario> getListaUsuariosAsociados(int IdProgramaAux2) {
        UsuarioXProgramaDao daoUsuarioXPrograma = new UsuarioXProgramaDao();
        ListaUsuariosAsociados = daoUsuarioXPrograma.obtenerTodosUsuariosHabilitados(IdProgramaAux2);
        return ListaUsuariosAsociados;
    }

    public void setListaUsuariosAsociados(List<Usuario> ListaUsuariosAsociados) {
        this.ListaUsuariosAsociados = ListaUsuariosAsociados;
    }

    public String getTituloModal() {
        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("conferenciaBtnNuevo");

        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("conferenciaActualizar");
        }
        return tituloModal;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public int getIdProgramaAux() {
        return idProgramaAux;
    }

    public void setIdProgramaAux(int idUsuarioXProgramaAux) {
        this.idProgramaAux = idUsuarioXProgramaAux;
    }

    public int getIdUsuarioAux() {
        return idUsuarioAux;
    }

    public void setIdUsuarioAux(int idUsuarioAux) {
        this.idUsuarioAux = idUsuarioAux;
    }

    public int getIdEstadoAux() {
        return idEstadoAux;
    }

    public void setIdEstadoAux(int idEstadoAux) {
        this.idEstadoAux = idEstadoAux;
    }

    public List<EstadoConferencia> getListaEstadosConf() {
        return listaEstadosConf;
    }

    public void setListaEstadosConf(List<EstadoConferencia> listaEstadosConf) {
        this.listaEstadosConf = listaEstadosConf;
    }

    public void guardar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        ConferenciaDao daoConferencia = new ConferenciaDao();
        System.out.println("fecha:" + fecha);
        try {
            conferencia.setFechaPonencia(new SimpleDateFormat("dd/MM/yyyy").parse(fecha));
        } catch (ParseException ex) {
            if (nuevo) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            } else {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
            return;
        }

        System.out.println("fechaPonencia:" + conferencia.getFechaPonencia());
        int idUsuarioXProgramaAux;

        if (nuevo) {
            if (conferencia.getNombre() == null || conferencia.getDescripcion() == null || conferencia.getFechaPonencia() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }
            UsuarioXProgramaDao daoUsuarioXPrograma = new UsuarioXProgramaDao();
            try {
                idUsuarioXProgramaAux = daoUsuarioXPrograma.obtenerUXPxUsuarioPrograma(idUsuarioAux, idProgramaAux).getIdUsuarioXPrograma();
                daoConferencia.insertar(conferencia, 1, idUsuarioXProgramaAux, idUsuarioAux);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + conferencia.getNombre() + "' " + resourceBundle.getString("InsertExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }

        } else {
            if (conferencia.getNombre() == null || conferencia.getDescripcion() == null || conferencia.getFechaPonencia() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }
            UsuarioXProgramaDao daoUsuarioXPrograma = new UsuarioXProgramaDao();

            try {
                idUsuarioXProgramaAux = daoUsuarioXPrograma.obtenerUXPxUsuarioPrograma(idUsuarioAux, idProgramaAux).getIdUsuarioXPrograma();
                daoConferencia.actualizar(conferencia, idEstadoAux, idUsuarioXProgramaAux);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + conferencia.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + conferencia.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
        }
        fecha = null;
    }

    public void eliminar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        ConferenciaDao daoConferencia = new ConferenciaDao();
        try {
            daoConferencia.eliminar(conferencia.getIdConferencia());
            lista.remove(conferencia);
            mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + conferencia.getNombre() + "' " + resourceBundle.getString("DeleteExito"));
        } catch (Exception ex) {
            mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + conferencia.getNombre() + "'");
        }
        limpiar();
    }

    public void obtenerTodos() {
        ConferenciaDao daoConferencia = new ConferenciaDao();
        FacesContext fc = FacesContext.getCurrentInstance();
       sesionUBean s = fc.getApplication().evaluateExpressionGet(fc, "#{sesionUBean}", sesionUBean.class);
        Usuario usuario = s.getUsuario();
        if(usuario.getIdRol().getIdRol() == 1){
            lista = daoConferencia.obtenerTodos();
        }else{
            lista = daoConferencia.obtenerTodosXUsuario(usuario);
        }
        

        EstadoConferenciaDao daoEstadoConferencia = new EstadoConferenciaDao();
        listaEstadosConf = daoEstadoConferencia.obtenerTodos();
    }

    public void limpiar() {

        conferencia = new Conferencia();
        fecha = "";
    }
    
    public void terminarVC() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String alertMsg;
        ConferenciaDao daoConferencia = new ConferenciaDao();
        if (BbbCalls.EnEmision(String.valueOf(conferencia.getIdConferencia()))) {
            if (BbbCalls.terminarVC(conferencia)) {
                alertMsg=resourceBundle.getString("indexHeaderConferencia")+": '" +conferencia.getNombre() + "' "+resourceBundle.getString("ModerFinalizada");
                
                if (conferencia.getGrabacion()) {
                    daoConferencia.actualizarEstadoConferencia(conferencia, 3);
                } else {
                    daoConferencia.actualizarEstadoConferencia(conferencia, 3);
                }
            } else {
                alertMsg=resourceBundle.getString("ModerNoSePudoFin")+": '" +conferencia.getNombre();
                daoConferencia.actualizarEstadoConferencia(conferencia, 3);
            }
            mensajeGlobalInformativo(alertMsg);
        } else {
            if(conferencia.getIdEstadoConferencia().getIdEstadoConferencia()>1){
                alertMsg=resourceBundle.getString("indexHeaderConferencia")+": '" +conferencia.getNombre() + "' "+resourceBundle.getString("ModerYaFinalizo");
                mensajeGlobalInformativo(alertMsg);
                daoConferencia.actualizarEstadoConferencia(conferencia, 3);
            }else{
                alertMsg=resourceBundle.getString("indexHeaderConferencia")+": '" +conferencia.getNombre() + "' "+resourceBundle.getString("ModerAunNo");
                mensajeGlobalAdvertencia(alertMsg);
            }
        }

    }
    
     public void redirect() throws IOException {
       ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String alertMsg;
		 ConfiguracionRedDao daoCred = new ConfiguracionRedDao();
		 ConfiguracionRed configuracion=daoCred.obtener();
		 if(configuracion.getDocumento()==null){
			 alertMsg=resourceBundle.getString("errorPPTPorDefecto");
          mensajeGlobalInformativo(alertMsg);
		 }else{
			iniciarModerador();    
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			externalContext.redirect(url);
		 
		 }
    }
     
     public void iniciarModerador() {
        url = BbbCalls.iniciarVC(usuarioBbb, conferencia);
        ConferenciaDao daoConferencia = new ConferenciaDao();
        daoConferencia.actualizarEstadoConferencia(conferencia, 2);
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
