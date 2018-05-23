/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.ConferenciaDao;
import dao.ConfiguracionRedDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import model.Conferencia;
import model.ConfiguracionRed;
import utils.BbbCalls;

/**
 *
 * @author garo1
 */
@ManagedBean
@ViewScoped
public class ModeradorBean implements Serializable{

    private Conferencia conferencia = new Conferencia();
    private String titulo;
    private String usuarioBbb;
    private String url;
    private Boolean flag;
    private List<String> infoConferencia;
    private List<String> infoParticipantes;
    /**
     * Creates a new instance of ModeradorBean
     */
    public ModeradorBean() {
        FacesContext fc = FacesContext.getCurrentInstance();
        Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
	String id=params.get("id");
        String clave=params.get("clv");
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        if(id!=null){
            ConferenciaDao daoConferencia=new ConferenciaDao();
            conferencia=daoConferencia.getConferenciaXId(Integer.parseInt(id));
            if(conferencia.getModeratorpw().equals(clave)){
                titulo=resourceBundle.getString("ModerPanelTitulo")+": "+conferencia.getNombre();
                refresh();
                flag=true;
            }else{
                titulo=resourceBundle.getString("ModerPagExclu");
                flag=false;
            }
            
        }else{
            titulo=resourceBundle.getString("ModerPagExclu");
            flag=false;
        }
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
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    

    public String getUsuarioBbb() {
        return usuarioBbb;
    }

    public void setUsuarioBbb(String usuarioBbb) {
        this.usuarioBbb = usuarioBbb;
    }

    
    public Conferencia getConferencia() {
        return conferencia;
    }

    public void setConferencia(Conferencia conferencia) {
        this.conferencia = conferencia;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }
    
    public void iniciarModerador() {
        url = BbbCalls.iniciarVC(usuarioBbb, conferencia);
        ConferenciaDao daoConferencia = new ConferenciaDao();
        daoConferencia.actualizarEstadoConferencia(conferencia, 2);
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
    
    public void refresh(){
        String xml=BbbCalls.getMeeting(conferencia);
        infoConferencia=BbbCalls.parseInfoConf(xml);
        System.out.println(infoConferencia);
    }

    public List<String> getInfoConferencia() {
        return infoConferencia;
    }

    public void setInfoConferencia(List<String> infoConferencia) {
        this.infoConferencia = infoConferencia;
    }

    public List<String> getInfoParticipantes() {
        return infoParticipantes;
    }

    public void setInfoParticipantes(List<String> infoParticipantes) {
        this.infoParticipantes = infoParticipantes;
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
