

package bean;

import dao.ConfiguracionRedDao;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import model.ConfiguracionRed;


@ManagedBean
@ViewScoped
public class ConfiguracionRedBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private ConfiguracionRed configuracion = new ConfiguracionRed();
    private Part UploadedFile;
    
    public Part getUploadedFile() {
        return UploadedFile;
    }

    public void setUploadedFile(Part UploadedFile) {
        this.UploadedFile = UploadedFile;
    }
    
    public ConfiguracionRedBean() {
        obtener();
    }

    public void obtener() {
        ConfiguracionRedDao daoConfiguracion = new ConfiguracionRedDao();
        configuracion = daoConfiguracion.obtener();
    }

    public ConfiguracionRed getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(ConfiguracionRed configuracion) {
        this.configuracion = configuracion;
    }

    public void guardar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        ConfiguracionRedDao daoConfiguracion = new ConfiguracionRedDao();
        try{
            daoConfiguracion.actualizar(configuracion);
            mensajeGlobalInformativo(resourceBundle.getString("ConfigGuardarExito"));
        }catch(Exception ex){
            mensajeGlobalError(resourceBundle.getString("ConfigGuardarError"));
        }
    }
    
        public void validateFile(FacesContext ctx,
                         UIComponent comp,
                         Object value) {
  ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
  List<FacesMessage> msgs = new ArrayList<FacesMessage>();
  Part file = (Part)value;
  if (file.getSize() > (20*(1024*1024))) {//20 MB
      mensajeGlobalError(resourceBundle.getString("SubirErrorTaman"));
      throw new ValidatorException(msgs);
  }
  if (!"application/pdf".equals(file.getContentType())) {
      mensajeGlobalError(resourceBundle.getString("SubirErrorPDF"));
      throw new ValidatorException(msgs);
  }
  /*if (!msgs.isEmpty()) {
    throw new ValidatorException(msgs);
  }*/
}
        
   public void upload() {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
    try {
        int bytesRead=-1;
        InputStream input= UploadedFile.getInputStream();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[10240];
        while((bytesRead=input.read(buffer)) !=-1){
            output.write(buffer,0,bytesRead);
        }
        
        configuracion.setDocumento(output.toByteArray());
        configuracion.setNombre(Paths.get(UploadedFile.getSubmittedFileName()).getFileName().toString().replace(" ", "-"));
        configuracion.setContenttype(UploadedFile.getContentType());
       
        ConfiguracionRedDao daoConfiguracion = new ConfiguracionRedDao();
        daoConfiguracion.actualizarPDF(configuracion);
        mensajeGlobalInformativo(resourceBundle.getString("SubirExito"));
    } catch (Exception e) {
        mensajeGlobalError(resourceBundle.getString("SubirError"));
      // Error handling
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
