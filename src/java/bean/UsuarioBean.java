package bean;

import dao.ProgramaDao;
import dao.RolDao;
import dao.UsuarioDao;
import dao.UsuarioXProgramaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Rol;
import model.Usuario;
import model.UsuarioXPrograma;

@ViewScoped
@ManagedBean
public class UsuarioBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Usuario usuario = new Usuario();

    // Listas
    private List<Usuario> lista = new ArrayList<>();
    private List<Rol> listaRoles = new ArrayList<>();
    private List<UsuarioXPrograma> listaProgramas = new ArrayList<>();

//Propios del XHTML
    private String tituloModal;
    private String labelContrasena;
    private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.
    private boolean eliminarReg = true; //true: Eliminar Usuario; false: Desasociar Programa de usuario.
    private int idRolAux = 1; // Auxiliar de rol
    private UsuarioXPrograma idUxp;
    private boolean contrasena = true;
    private String password;
    private int PrimeraVez =0;

    public UsuarioBean() {
        obtenerTodos();
        usuario.setIdUsuario(0);
    }

    public String getResource(String resource) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        return resourceBundle.getString(resource);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEliminarReg() {
        return eliminarReg;
    }

    public void setEliminarReg(boolean eliminar) {
        this.eliminarReg = eliminar;
    }

    public UsuarioXPrograma getIdUxp() {
        return idUxp;
    }

    public void setIdUxp(UsuarioXPrograma idUxp) {
        this.idUxp = idUxp;
    }

    public List<UsuarioXPrograma> getListaProgramas() {
        return listaProgramas;
    }

    public void setListaProgramas(List<UsuarioXPrograma> listaProgramas) {
        this.listaProgramas = listaProgramas;
    }

    public String getLabelContrasena() {
        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            labelContrasena = resourceBundle.getString("usuarioHeaderContra");
            labelContrasena = labelContrasena + ":";
        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            labelContrasena = resourceBundle.getString("usuarioCambiarContra");

        }
        return labelContrasena;

    }

    public void setLabelContrasena(String labelContrasena) {
        this.labelContrasena = labelContrasena;
    }

    public boolean isContrasena() {
        return contrasena;
    }

    public void setContrasena(boolean contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdRolAux() {
        return idRolAux;
    }

    public void setIdRolAux(int idRolAux) {
        this.idRolAux = idRolAux;
    }

    public List<Rol> getListaRoles() {
        return listaRoles;
    }

    public void setListaRoles(List<Rol> listaRoles) {
        this.listaRoles = listaRoles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getLista() {
        return lista;
    }

    public void setLista(List<Usuario> lista) {
        this.lista = lista;
    }

    public String getTituloModal() {
        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("usuarioBtnNuevo");

        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("usuarioActualizar");

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
        if (nuevo) {
            if (usuario.getNombre() == null || usuario.getApellido() == null || usuario.getCorreo() == null || password == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }

            //Valores 'automaticos'
            usuario.setUsuario(generarUsuario());
            usuario.setIntentos((short) 0);
            usuario.setNuevo(true);
            //Convertir Contrasena
            usuario.setContrasena(bean.sesionUBean.cryptSHA256(password));
            usuario.setEstado(usuario.getEstado() == null ? "I" : usuario.getEstado());
            UsuarioDao daoUsuario = new UsuarioDao();
            try {
                daoUsuario.insertar(usuario, idRolAux);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + usuario.getNombre() + "' " + resourceBundle.getString("InsertExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
        } else {
            if (usuario.getUsuario() == null || usuario.getNombre() == null || usuario.getApellido() == null || usuario.getCorreo() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }

            if (contrasena) {
                //Convertir Contrasena
                usuario.setContrasena(bean.sesionUBean.cryptSHA256(password));
                usuario.setNuevo(true);
                UsuarioDao daoUsuario = new UsuarioDao();

                try {
                    daoUsuario.actualizarConContrasena(usuario, idRolAux);
                    mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + usuario.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
                } catch (Exception ex) {
                    mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                }

            } else {
                UsuarioDao daoUsuario = new UsuarioDao();
                try {
                    daoUsuario.actualizar(usuario, idRolAux);
                    mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + usuario.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
                } catch (Exception ex) {
                    mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                }
            }

        }
    }

    //Funcion que genera el formato de usuario: Primera letra del nombre + Primer apellido + Correlativo
    public String generarUsuario() {

        String nombre;
        String apellido;
        int correlativo;
        String[] listaApellidos;
        listaApellidos = usuario.getApellido().split(" ");

        nombre = usuario.getNombre().charAt(0) + "";
        apellido = listaApellidos[0];
        nombre = (nombre + apellido).toLowerCase();

        UsuarioDao daoUsuario = new UsuarioDao();
        List<Usuario> listaNombreUsuario = daoUsuario.obtenerNombreUsuarios(nombre);

        correlativo = listaNombreUsuario.size();
        if (correlativo == 0) {
            nombre = nombre + "1";
        } else {

            int i;
            for (i = 1; i <= correlativo; i++) {
                if (!listaNombreUsuario.get(i - 1).getUsuario().equalsIgnoreCase(nombre + i)) {
                    nombre = nombre + i;
                    break;
                }
            }

            if (i > correlativo) {
                nombre = nombre + (correlativo + 1);
            }
        }

        return nombre;
    }

    public void eliminar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        UsuarioXProgramaDao dao = new UsuarioXProgramaDao();
        ProgramaDao prog = new ProgramaDao();
        int programasAsociados = dao.obtenerTodosProgramasxUsuario(usuario.getIdUsuario()).size();
        int programasCreados = prog.obtenerTodosXUsuario(usuario).size();
        
        if (eliminarReg) {
            if(programasAsociados != 0 ){
                mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns4"));
                return;
            }
            if(programasCreados != 0 ){
                mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + usuario.getNombre() + "'; " + resourceBundle.getString("ErrorIns4"));
                return;
            }
            try {
                UsuarioDao daoUsuario = new UsuarioDao();
                daoUsuario.eliminar(usuario.getIdUsuario());
                lista.remove(usuario);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + usuario.getNombre() + "' " + resourceBundle.getString("DeleteExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + usuario.getNombre() + "'");
            }

            limpiar();
            
        } else {
            idUxp.setActivo(false);
            dao.actualizar(idUxp);
            listaProgramas.remove(idUxp);
        }

    }

    public void obtenerTodos() {

        UsuarioDao daoUsuario = new UsuarioDao();
        lista = daoUsuario.obtenerTodos();

        RolDao daoRol = new RolDao();
        listaRoles = daoRol.obtenerTodos();

    }

    public List<UsuarioXPrograma> obtenerProgramasxUsuario() {
        UsuarioXProgramaDao dao = new UsuarioXProgramaDao();
        listaProgramas = dao.obtenerTodosProgramasxUsuario(usuario.getIdUsuario());
        return listaProgramas;
    }

    public void limpiar() {
        usuario = new Usuario();
        idRolAux = 1;
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
