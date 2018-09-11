package bean;


import dao.ConferenciaDao;
import dao.UsuarioXProgramaDao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
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
import javax.servlet.http.HttpServletResponse;
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
        if(userAux == null || userAux.getIdUsuario() == null){
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
    public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
        if (fileToZip == null || !fileToZip.exists()) {
            return;
        }
        String zipEntryName = fileToZip.getName();
        if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
            zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
        }

        if (fileToZip.isDirectory()) {
            System.out.println("+" + zipEntryName);
            for (File file : fileToZip.listFiles()) {
                addDirToZipArchive(zos, file, zipEntryName);
            }
        } else {
            System.out.println("   " + zipEntryName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = new FileInputStream(fileToZip);
            zos.putNextEntry(new ZipEntry(zipEntryName));
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
    }
    public void descarga() throws FileNotFoundException, Exception{
    HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
    //aqui es donde se dice los parametros de donde estara y el nombre del comprimido
    String bbburl= grabacion.getRecordingurl();
    String playback = "/var/bigbluebutton/playback/presentation/";
    String [] seccionesURL = bbburl.split("/");
    
    
    //aqui esta el meetingId, es de contar caracteres
    String meetingId = bbburl.substring(bbburl.indexOf("=") + 1, bbburl.length());
    //String serverUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestServerName();
    /*
        File source = new File("/var/bigbluebutton/published/presentation/" + meetingId + "/");
        File source2 = new File(playback + seccionesURL[5] + "/");
        File source = new File("/download/presentation/" + meetingId + "/" + meetingId + ".mp4");
        File dest = new File("/tmp/" + grabacion.getNombre() + "/");
        //File zip = new File("/tmp/" + grabacion.getNombre() + ".zip");
        
        try {
             if(!dest.exists()){
                FileUtils.copyDirectory(source, dest);
                //FileUtils.copyDirectory(source2, dest);
             }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    if(!zip.exists()){
        FileOutputStream fos = new FileOutputStream("/tmp/" + grabacion.getNombre() + ".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        //aqui es donde se dice la direccion de donde estan los archivos y subfolders a comprimir
        addDirToZipArchive(zos, new File("/tmp/" + grabacion.getNombre() + "/"), null);
        zos.flush();
        fos.flush();
        zos.close();
        fos.close();
    }*/
//aqui comienza la descarga

    File file = new File("/var/bigbluebutton/published/presentation/"+meetingId+ "/"+ meetingId + ".mp4");
    //File file = new File("C:/Users/User/Imagenes/AdministracionConferencia/web/resources/img/video" + ".mp4");
    if(!file.exists()){
        System.out.println("file not found");
    }
    response.setContentType("APPLICATION/OCTET-STREAM");
    response.setHeader("Content-Disposition","attachment; filename=\"" + grabacion.getNombre() + ".mp4");

    OutputStream out = response.getOutputStream();
    FileInputStream in = new FileInputStream(file);
    byte[] buffer = new byte[4096];
    int length;
    while ((length = in.read(buffer)) > 0){
       out.write(buffer, 0, length);
    }
    in.close();
    out.close();
    out.flush();

     FacesContext.getCurrentInstance().responseComplete();
        
    }
     public String serverStatus() throws Exception {
         String text;
         try{
             URL dir = new URL("https://bbb.salud.gob.sv");
             HttpURLConnection dirCon = (HttpURLConnection) dir.openConnection();
             dirCon.setRequestMethod("GET");
             dirCon.setConnectTimeout(3000);
             dirCon.connect();
             text = "Online";
         }catch (Exception e){
             text = "Offline";
         }
         return text;
     }
   
}
