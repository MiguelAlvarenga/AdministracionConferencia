package bean;

import dao.ProgramaDao;
import dao.UsuarioDao;
import dao.UsuarioXProgramaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Programa;
import model.Usuario;
import model.UsuarioXPrograma;

@ViewScoped
@ManagedBean
public class ProgramaBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private Programa programa = new Programa();
    private Usuario usuario = new Usuario();

    //Listas necesarias
    private List<Programa> lista = new ArrayList<>();
    private List<Usuario> listaUsuarios = new ArrayList<>();
    private List<Usuario> listaUsuariosHabilitados = new ArrayList<>();
    private List<Usuario> listaUsuariosHabilitadosPop = new ArrayList<>();

    
    private List<Usuario> listaUsuariosDeshabilitados = new ArrayList<>();
    private List<UsuarioXPrograma> registroUXP = new ArrayList<>();

    //Propios del XHTML
    private String tituloModal;
    private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.
    private int idUsuarioAux = 1; // Auxiliar de usuario
    private int idProgramaRespaldo = -1; //Es bueno guardar el id del Programa.... una nuca sabe cuando el objeto Programa le va agarrar feo..

    public ProgramaBean() {
        obtenerTodos();
    }

    public List<UsuarioXPrograma> getRegistroUXP() {
        return registroUXP;
    }

    public void setRegistroUXP(List<UsuarioXPrograma> registroUXP) {
        this.registroUXP = registroUXP;
    }

    public List<Usuario> getListaUsuariosHabilitados() {
        return listaUsuariosHabilitados;
    }

    public void setListaUsuariosHabilitados(List<Usuario> listaUsuariosHabilitados) {
        this.listaUsuariosHabilitados = listaUsuariosHabilitados;
    }

    public List<Usuario> getListaUsuariosDeshabilitados() {
        return listaUsuariosDeshabilitados;
    }

    public void setListaUsuariosDesHabilitados(List<Usuario> listaUsuariosDeshabilitados) {
        this.listaUsuariosDeshabilitados = listaUsuariosDeshabilitados;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public String getTituloModal() {
        if (nuevo) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("programaBtnNuevo");

        } else {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
            tituloModal = resourceBundle.getString("programaActualizar");

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

    public int getIdUsuarioAux() {
        return idUsuarioAux;
    }

    public void setIdUsuarioAux(int idUsuarioAux) {
        this.idUsuarioAux = idUsuarioAux;
    }

    public Programa getPrograma() {
        return programa;
    }

    public void setPrograma(Programa programa) {
        this.programa = programa;
        idProgramaRespaldo = programa.getIdPrograma();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public List<Usuario> getListaUsuariosHabilitadosPop(Programa programa) {
        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();
        listaUsuariosHabilitadosPop = daoUXP.obtenerTodosUsuariosHabilitados(programa.getIdPrograma());
        return listaUsuariosHabilitadosPop;
    }

    public void setListaUsuariosHabilitadosPop(List<Usuario> listaUsuariosHabilitadosPop) {
        this.listaUsuariosHabilitadosPop = listaUsuariosHabilitadosPop;
    }

    public List<Programa> getLista() {
        return lista;
    }

    public void setLista(List<Programa> lista) {
        this.lista = lista;
    }

    public void guardar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        Calendar fechaActual = Calendar.getInstance();
        programa.setFechaCreacion(fechaActual.getTime());
        ProgramaDao daoPrograma = new ProgramaDao();

        if (nuevo) {
            if (programa.getNombre() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + programa.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }
            try {
                daoPrograma.insertar(programa, idUsuarioAux);
                UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();

            try {

                registroUXP = daoUXP.obtenerRegistro(idUsuarioAux, programa.getIdPrograma());

                if (registroUXP.isEmpty()) {
                    daoUXP = new UsuarioXProgramaDao();
                    daoUXP.insertar(programa, usuario);
                } else {
                    registroUXP.get(0).setActivo(true);
                    daoUXP = new UsuarioXProgramaDao();
                    daoUXP.actualizar(registroUXP.get(0));
                }

                listaUsuariosHabilitados.add(usuario);
                listaUsuariosDeshabilitados.remove(usuario);

                //		obtenerUsuariosHab();
                //		obtenerUsuariosDeshab();
            } catch (Exception ex) {
            }
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + programa.getNombre() + "' " + resourceBundle.getString("InsertExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorIns") + " '" + programa.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
        } else {
            if (programa.getNombre() == null) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + programa.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
                return;
            }
            try {
                daoPrograma.actualizar(programa);
                mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + programa.getNombre() + "' " + resourceBundle.getString("UpdateExito"));
            } catch (Exception ex) {
                mensajeGlobalError(resourceBundle.getString("ErrorUpd") + " '" + programa.getNombre() + "'; " + resourceBundle.getString("ErrorIns2"));
            }
        }
    }

    public void asociar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        System.out.println("1. Asociar " + usuario.getNombre());
        System.out.println("");
        System.out.println("");

        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();

        try {

            registroUXP = daoUXP.obtenerRegistro(usuario.getIdUsuario(), programa.getIdPrograma());

            if (registroUXP.isEmpty()) {
                daoUXP = new UsuarioXProgramaDao();
                daoUXP.insertar(programa, usuario);
            } else {
                registroUXP.get(0).setActivo(true);
                daoUXP = new UsuarioXProgramaDao();
                daoUXP.actualizar(registroUXP.get(0));
            }

            listaUsuariosHabilitados.add(usuario);
            listaUsuariosDeshabilitados.remove(usuario);

            //		obtenerUsuariosHab();
            //		obtenerUsuariosDeshab();
        } catch (Exception ex) {
        }

    }

    public void remover() {

        System.out.println("1. Remover " + usuario.getNombre());
        System.out.println("");
        System.out.println("");

        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();

        try {
            registroUXP = daoUXP.obtenerRegistro(usuario.getIdUsuario(), programa.getIdPrograma());
            registroUXP.get(0).setActivo(false);

            daoUXP = new UsuarioXProgramaDao();
            daoUXP.actualizar(registroUXP.get(0));

            listaUsuariosDeshabilitados.add(usuario);
            listaUsuariosHabilitados.remove(usuario);

//			obtenerUsuariosHab();
//			obtenerUsuariosDeshab();
        } catch (Exception ex) {
        }

        System.out.println("2.Remover " + usuario.getNombre());
        System.out.println("");
        System.out.println("");
    }

    public void eliminar() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        try {
            ProgramaDao daoPrograma = new ProgramaDao();
            daoPrograma.eliminar(programa.getIdPrograma());
            lista.remove(programa);
            mensajeGlobalInformativo(resourceBundle.getString("Registro") + " '" + programa.getNombre() + "' " + resourceBundle.getString("DeleteExito"));
        } catch (Exception ex) {
            mensajeGlobalError(resourceBundle.getString("ErrorDlt") + " '" + programa.getNombre() + "'");
        }

        limpiar();

    }

    public void obtenerTodos() {
        
        ProgramaDao daoPrograma = new ProgramaDao();
        UsuarioDao ud = new UsuarioDao();
        int RolUsuario;
        FacesContext fc = FacesContext.getCurrentInstance();
        sesionUBean s = fc.getApplication().evaluateExpressionGet(fc, "#{sesionUBean}", sesionUBean.class);
        Usuario usuarioAux = s.getUsuario();
        RolUsuario = usuarioAux.getIdRol().getIdRol();
        if(RolUsuario == 1){
            lista = daoPrograma.obtenerTodos();
        }else{
            lista = daoPrograma.obtenerTodosXUsuario(usuarioAux);
        }
        UsuarioDao daoUsuario = new UsuarioDao();
        listaUsuarios = daoUsuario.obtenerTodos();
    }

    public void obtenerUsuariosHab() {
        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();
        listaUsuariosHabilitados = daoUXP.obtenerTodosUsuariosHabilitados(programa.getIdPrograma());
    }

    public void obtenerUsuariosDeshab() {
        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();
        listaUsuariosDeshabilitados = daoUXP.obtenerTodosUsuariosDeshabilitados(programa.getIdPrograma());
    }
    
    public void obtenerUsuariosHabWrap(Programa program) {
        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();
        listaUsuariosHabilitados = daoUXP.obtenerTodosUsuariosHabilitados(program.getIdPrograma());
    }

    public void obtenerUsuariosDeshabWrap(Programa program) {
        UsuarioXProgramaDao daoUXP = new UsuarioXProgramaDao();
        listaUsuariosDeshabilitados = daoUXP.obtenerTodosUsuariosDeshabilitados(program.getIdPrograma());
    }

    public void limpiar() {
        programa = new Programa();
        idProgramaRespaldo = -2;
    }

    public void usuarioSesion(Usuario usu) {
        programa.setIdUsuario(usu);
        idUsuarioAux = usu.getIdUsuario();
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
