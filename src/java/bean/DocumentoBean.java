package bean;

import dao.DocumentoDao;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.Part;
import model.Conferencia;
import model.Documento;

@ViewScoped
@ManagedBean
public class DocumentoBean implements Serializable {

	private String nombre;
	private static final long serialVersionUID = 1L;
	private Documento documento = new Documento();
	private List<Documento> lista = new ArrayList<>();
	private Part UploadedFile;
	private Conferencia conferencia;
	private String fileContent;

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	public Part getUploadedFile() {
		return UploadedFile;
	}

	public void setUploadedFile(Part UploadedFile) {
		this.UploadedFile = UploadedFile;
	}

	public void upload() {
		try {
			int bytesRead = -1;
			InputStream input = UploadedFile.getInputStream();
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			byte[] buffer = new byte[10240];
			while ((bytesRead = input.read(buffer)) != -1) {
				output.write(buffer, 0, bytesRead);
			}

			documento.setDocumento(output.toByteArray());
			documento.setNombre(Paths.get(UploadedFile.getSubmittedFileName()).getFileName().toString().replace(" ", "-"));
			documento.setContenttype(UploadedFile.getContentType());

			DocumentoDao daoDocumento = new DocumentoDao();

			daoDocumento.insertar(documento, conferencia.getIdConferencia());
			documento = new Documento();
		} catch (Exception e) {
			System.out.println("error" + e.toString());
			// Error handling
		}
	}

	public void download() {
		try {

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();

			ec.responseReset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
			ec.setResponseContentType(documento.getContenttype()); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ExternalContext#getMimeType() for auto-detection based on filename.
			//ec.setResponseContentLength(documento.getDocumento()); // Set it with the file size. This header is optional. It will work if it's omitted, but the download progress will be unknown.
			ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + documento.getNombre() + "\""); // The Save As popup magic is done here. You can give it any file name you want, this only won't work in MSIE, it will use current request URL as file name instead.

			OutputStream output = ec.getResponseOutputStream();
			output.write(documento.getDocumento());
    // Now you can write the InputStream of the file to the above OutputStream the usual way.
			// ...

			fc.responseComplete(); // Important! */
		} catch (Exception e) {
			System.out.println("error" + e.toString());
			// Error handling
		}
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public List<Documento> getLista() {
		return lista;
	}

	public void setLista(List<Documento> lista) {
		this.lista = lista;
	}

	public void insertar() {
	}

	public void actualizar(Documento documento) {
	}

	public void eliminar() {
		DocumentoDao daoDocumento = new DocumentoDao();
		daoDocumento.eliminar(documento.getIdDocumento());
		lista.remove(documento);
		documento = new Documento();

	}

	public void obtenerTodos() {
		DocumentoDao daoDocumento = new DocumentoDao();
		lista = daoDocumento.obtenerTodosXConferencia(conferencia);
	}

	public DocumentoBean() {
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void validateFile(FacesContext ctx,
			  UIComponent comp,
			  Object value) {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		Part file = (Part) value;
		if (file.getSize() > (20 * (1024 * 1024))) {//20 MB
                        mensajeGlobalError(resourceBundle.getString("conferenciaArchivoGrande"));
                        throw new ValidatorException(msgs);
		}
		if (!"application/pdf".equals(file.getContentType())) {
                    mensajeGlobalError(resourceBundle.getString("conferenciaSoloPDF"));
                    throw new ValidatorException(msgs);
		}
		/*if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}*/
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
