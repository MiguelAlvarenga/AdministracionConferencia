package bean;

import dao.ConferenciaDao;
import dao.ConfiguracionRedDao;
import dao.CorreoDao;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Conferencia;
import model.Correo;
import utils.GestionCorreos;

@ViewScoped
@ManagedBean
public class EnviarCorreoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Conferencia conferencia = new Conferencia();
	private Correo correo = new Correo();

	private int idCorreoAux;
	private int idConferenciaAux;
	private String checkAux;
	private String txtCorreoPara;
	private String txtCorreoCC;
	private String txtAsunto;
	private String txtMensaje;

	public EnviarCorreoBean() throws IOException {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		FacesContext fc = FacesContext.getCurrentInstance();
		Map<String, String> params = fc.getExternalContext().getRequestParameterMap();
		checkAux = params.get("check");
		idCorreoAux = Integer.parseInt(params.get("correo")==null? "0":params.get("correo"));
		idConferenciaAux = Integer.parseInt(params.get("conferencia")==null?"0":params.get("conferencia"));
                if(idCorreoAux==0 || idConferenciaAux==0){
                    ConfiguracionRedDao daoCon=new ConfiguracionRedDao();
                    String url=daoCon.obtener().getLogouturl();
                    fc.getExternalContext().redirect(url);
                }else{
                    obtenerCorreo();
                }
		txtMensaje = correo.getContenido();
		System.out.println("txtMensaje" + txtMensaje);

	}

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	public int getIdCorreoAux() {
		return idCorreoAux;
	}

	public void setIdCorreoAux(int idCorreoAux) {
		this.idCorreoAux = idCorreoAux;
	}

	public String getTxtCorreoPara() {
		return txtCorreoPara;
	}

	public void setTxtCorreoPara(String txtCorreoPara) {
		this.txtCorreoPara = txtCorreoPara;
	}

	public String getTxtCorreoCC() {
		return txtCorreoCC;
	}

	public String getTxtAsunto() {
		return txtAsunto;
	}

	public void setTxtAsunto(String txtAsunto) {
		this.txtAsunto = txtAsunto;
	}

	public void setTxtCorreoCC(String txtCorreoCC) {
		this.txtCorreoCC = txtCorreoCC;
	}

	public String getTxtMensaje() {
		return txtMensaje;
	}

	public void setTxtMensaje(String txtMensaje) {
		this.txtMensaje = txtMensaje;
	}

	public void obtenerCorreo() {
		CorreoDao dao = new CorreoDao();
		correo = dao.getCorreoXId(idCorreoAux);
	}

	public void enviarCorreo() {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		List<String> correosDestino = new ArrayList<String>();
		List<String> correosCC = new ArrayList<String>();

		String[] listaCorreos = txtCorreoPara.split(",");
		for (int i = 0; i < listaCorreos.length; i++) {
			correosDestino.add(listaCorreos[i].trim());
		}
                listaCorreos = txtCorreoCC.split(",");
                if(!txtCorreoCC.isEmpty() || txtCorreoCC!=null){
                    for (int i = 0; i < listaCorreos.length; i++) {
                            correosCC.add(listaCorreos[i].trim());
                    }
                }

		GestionCorreos gc = new GestionCorreos();
                try{
                    gc.EnviarCorreos(correosDestino, correosCC, txtAsunto, txtMensaje);
                    mensajeGlobalInformativo(resourceBundle.getString("EnviarCorreoExito"));
                }catch(Exception ex){
                    mensajeGlobalError(resourceBundle.getString("EnviarCorreoError"));
                }
		

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
