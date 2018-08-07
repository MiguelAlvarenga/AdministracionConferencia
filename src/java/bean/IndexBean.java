package bean;

import dao.ConferenciaDao;
import dao.UsuarioXProgramaDao;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import model.Conferencia;
import model.Programa;
import model.Usuario;
import utils.BbbCalls;

@ViewScoped
@ManagedBean
public class IndexBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Conferencia conferencia = new Conferencia();
    private Conferencia grabacion = new Conferencia();
    private List<Conferencia> lista = new ArrayList<>();
    private List<Conferencia> listaGrabaciones = new ArrayList<>();
    private List<Programa> listaProgramasxUsuario = new ArrayList<>();

    private String url;
    private String usuarioBbb;
    private int cantidadParticipantes = 1;
    private String passwordBbb;
    private String id;
    private String check;
    private String err;
    private int status;
    private String tituloModal;
    private boolean nuevo = true;

    public IndexBean() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
        String alertMsg;
        id = params.get("id");
        check = params.get("check");

        refresh();
    }

    public void refresh() {
        if (id != null) {
            obtenerTodos(id, check);
            ConferenciaDao conferenciaDao = new ConferenciaDao();
            status = conferenciaDao.getConferenciaXId(Integer.parseInt(id)).getIdEstadoConferencia().getIdEstadoConferencia();
        } else {
            obtenerTodos();
        }
        obtenerGrabaciones();
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

    public String getPasswordBbb() {
        return passwordBbb;
    }

    public void setPasswordBbb(String passwordBbb) {
        this.passwordBbb = passwordBbb;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    
    public List<Programa> getListaProgramasxUsuario(int idUsuario) {
        UsuarioXProgramaDao daoUsuarioXPrograma = new UsuarioXProgramaDao();
        listaProgramasxUsuario = daoUsuarioXPrograma.obtenerSoloProgramasxUsuario(idUsuario);
        return listaProgramasxUsuario;
    }

    public void setListaProgramasxUsuario(List<Programa> listaProgramasxUsuario) {
        this.listaProgramasxUsuario = listaProgramasxUsuario;
    }

    public void setTituloModal(String tituloModal) {
        this.tituloModal = tituloModal;
    }

    public void obtenerTodos() {
        ConferenciaDao daoConferencia = new ConferenciaDao();
        lista = daoConferencia.obtenerTodosIndex();
    }

    public String getResource(String resource) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        return resourceBundle.getString(resource);
    }

    public int unirseVC() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        int flag = (BbbCalls.EnEmision(String.valueOf(conferencia.getIdConferencia())) ? 0 : 1);
        if (flag == 0) {
            if (conferencia.getTipoConferencia()) {
                if (conferencia.getAttendeepw().equals(passwordBbb)) {
                    url = BbbCalls.urlInvitado(usuarioBbb, conferencia);
                } else {
                    flag = 2;
                    System.out.println("Contrasena invalida para  '" + conferencia.getNombre() + "'");
                    mensajeGlobalError(resourceBundle.getString("indexContraInvalida"));

                }
            } else {
                url = BbbCalls.urlInvitado(usuarioBbb, conferencia);
            }

        } else {
            if(conferencia.getIdEstadoConferencia().getIdEstadoConferencia()==2){
                mensajeGlobalAdvertencia(resourceBundle.getString("indexHeaderConferencia")+" '"+conferencia.getNombre()+"' "+resourceBundle.getString("ModerYaFinalizo"));
            }else{
                mensajeGlobalAdvertencia(resourceBundle.getString("indexErrorConferencia"));
            }
        }
        return flag;
    }

    public void redirectInvitado() throws IOException {
        int flag = unirseVC();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

        if (flag == 0) {
            externalContext.redirect(url);
        } else {
            if (id != null) {
                String urlLocal = request.getRequestURL().toString() + "?id=" + BbbCalls.urlEncode(id) + "&check=" + BbbCalls.urlEncode(check);
                externalContext.redirect(urlLocal);
            }
        }
    }

    private void obtenerTodos(String id, String check) {
        ConferenciaDao daoConferencia = new ConferenciaDao();
        lista = daoConferencia.obtenerTodosIndex(Integer.parseInt(id), check);
    }

    private void obtenerGrabaciones() {
        FacesContext fc = FacesContext.getCurrentInstance();
        sesionUBean s = fc.getApplication().evaluateExpressionGet(fc, "#{sesionUBean}", sesionUBean.class);
        Usuario userAux = s.getUsuario();
        ConferenciaDao daoConferencia = new ConferenciaDao();
        if(userAux == null){
            listaGrabaciones = daoConferencia.obtenerGrabaciones();
        }else{
            if(userAux.getIdRol().getIdRol() == 1){
                listaGrabaciones = daoConferencia.obtenerTodasGrabaciones();
            }else{
                listaGrabaciones = daoConferencia.obtenerGrabacionesXUsuario(s.getUsuario());
            }
            
        }
    }

    public List<Conferencia> getListaGrabaciones() {
        return listaGrabaciones;
    }

    public void setListaGrabaciones(List<Conferencia> listaGrabaciones) {
        this.listaGrabaciones = listaGrabaciones;
    }

    public Conferencia getGrabacion() {
        return grabacion;
    }

    public void setGrabacion(Conferencia grabacion) {
        this.grabacion = grabacion;
    }

    public String getEnlace() {
        return grabacion.getRecordingurl() + "&t=" + (grabacion.getPuntoInicio() == null || grabacion.getPuntoInicio().isEmpty() ? "0s" : grabacion.getPuntoInicio());
    }

    public void redirectGrabacion() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String url = grabacion.getRecordingurl() + "&t=" + (grabacion.getPuntoInicio() == null || grabacion.getPuntoInicio().isEmpty() ? "0s" : grabacion.getPuntoInicio());
        System.out.println("url redirect:" + url);
        externalContext.redirect(url);

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
