/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import dao.LoginDao;
import dao.UsuarioDao;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.NoResultException;
import model.Usuario;

@ManagedBean
@SessionScoped

public class sesionUBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String user;
    private String password;
    private String passwordNueva;
    private String passwordConfirm;
    private Usuario usuario = new Usuario();
    private boolean cambiandoPW;
    private boolean logged;
    private boolean loggedFirstTime = false;	 //La primera vista mostrar mensaje, la otra no
    private int loggedFail = 0;	 //La primera vista mostrar mensaje, la otra no. 0 es nada 1 es error 2 es bloqueado.	
    private boolean cambioPwdExitoso;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = cryptSHA256(password);
    }

    public String getPasswordNueva() {
        return passwordNueva;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = cryptSHA256(passwordConfirm);
    }

    public void setPasswordNueva(String passwordNueva) {
        this.passwordNueva = cryptSHA256(passwordNueva);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public boolean isLoggedFirstTime() {
        return loggedFirstTime;
    }

    public void setLoggedFirstTime(boolean loggedFirstTime) {
        this.loggedFirstTime = loggedFirstTime;
    }

    public int getLoggedFail() {
        return loggedFail;
    }

    public void setLoggedFail(int loggedFail) {
        this.loggedFail = loggedFail;
    }

    public boolean isCambioPwdExitoso() {
        return cambioPwdExitoso;
    }

    public void setCambioPwdExitoso(boolean cambioPwdExitoso) {
        this.cambioPwdExitoso = cambioPwdExitoso;
    }

    public sesionUBean() {
    }

    public String navRuleLogIn() {
        if (logged) {
            if (usuario.getNuevo()) {
                cambiandoPW = true;
                return "change";
            } else {
                cambiandoPW = false;
                return "main";
            }

        } else {
            cambiandoPW = false;
            return "error";
        }
    }

    public String navRuleChgPwd() {
        if (cambioPwdExitoso) {
            cambiandoPW = false;
            return "main";
        } else {
            cambiandoPW = true;
            return "change";
        }
    }

    public static String cryptSHA256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(texto.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("Erro ENC:" + ex.getMessage());
            return null;
        }
    }

    public void iniciarSesion() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        System.out.println("aqui");
        int flagErrorLoggeo = 0;
        String alertMsg = "";
        if (!logged) {
            LoginDao daoLogin = new LoginDao();
            try {//Iniciando Sesion
                usuario = daoLogin.login(user);
                System.out.println("usuario:" + usuario.getNombre());
            } catch (NoResultException ex) {
                loggedFail = 1;
                alertMsg = resourceBundle.getString("SesionUsuarioNoExiste");
                mensajeGlobalError(alertMsg);
                flagErrorLoggeo = 1;
            } catch (Exception ex) {
                System.out.println("ex:" + ex.toString());
            }
            if (flagErrorLoggeo != 1) {
                if (usuario.getEstado().equals("A")) {
                    if (flagErrorLoggeo == 0) {
                        if (usuario.getContrasena().equals(password)) {

                            alertMsg = resourceBundle.getString("SesionBienvenido") + " " + usuario.getNombre();
                            System.out.println("mensaje:" + alertMsg);

                            mensajeGlobalInformativo(alertMsg);
                            logged = true;
                            loggedFirstTime = true;
                            usuario.setIntentos((short) 0);
                            UsuarioDao daoUsuario = new UsuarioDao();
                            daoUsuario.actualizarIntentos(usuario);
                        } else {
                            loggedFail = 1;
                            alertMsg = resourceBundle.getString("SesionContraInvalida") + ": " + usuario.getUsuario();
                            mensajeGlobalError(alertMsg);
                            usuario.setIntentos((short) (usuario.getIntentos() + 1));
                            UsuarioDao daoUsuario = new UsuarioDao();
                            daoUsuario.actualizarIntentos(usuario);
                        }
                    }
                } else {
                    alertMsg = resourceBundle.getString("usuarioHeaderUsuario") + ": '" + usuario.getUsuario() + "' " + resourceBundle.getString("usuarioBloqueado");
                    mensajeGlobalError(alertMsg);
                }
            } else {
                loggedFail = 2;
                alertMsg = resourceBundle.getString("SesionUsuarioNoExiste");
                //alertMsg = resourceBundle.getString("usuarioHeaderUsuario") + ": '" + usuario.getUsuario() + "' " + resourceBundle.getString("usuarioBloqueado");
                mensajeGlobalError(alertMsg);
            }

        }
    }

    public void cambiarPwd() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String alertMsg = "";
        if (logged) {
            if (usuario.getContrasena().equals(this.password)) {
                System.out.println("passwordNueva:" + passwordNueva);
                System.out.println("passwordConfirm:" + passwordConfirm);
                if (this.passwordNueva.equals(this.passwordConfirm)) {
                    if (this.passwordNueva.equals(this.password)) {
                        cambioPwdExitoso = false;
                        alertMsg = resourceBundle.getString("CambiarPwdError");
                        mensajeGlobalError(alertMsg);
                    } else {
                        cambioPwdExitoso = true;
                        usuario.setContrasena(passwordNueva);
                        usuario.setNuevo(false);
                        UsuarioDao daoUsuario = new UsuarioDao();
                        daoUsuario.actualizarConContrasena(usuario, usuario.getIdRol().getIdRol());

                        alertMsg = resourceBundle.getString("SesionContraDe") + ": '" + usuario.getUsuario() + "' " + resourceBundle.getString("SesionContraActuaExito");
                        mensajeGlobalInformativo(alertMsg);
                    }
                } else {
                    cambioPwdExitoso = false;
                    alertMsg = resourceBundle.getString("SesionContraNoCoin");
                    mensajeGlobalError(alertMsg);
                }
            } else {
                cambioPwdExitoso = false;
                alertMsg = resourceBundle.getString("SesionContraActuaIncorrecta");
                mensajeGlobalError(alertMsg);
            }
        } else {
            alertMsg = resourceBundle.getString("SesionEasterEgg");
            mensajeGlobalError(alertMsg);
        }
    }

    public void cerrarSesion() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        String alertMsg = "";
        if (logged) {
            usuario = null;
            logged = false;
            alertMsg = resourceBundle.getString("SesionCierreExitoso");
            mensajeGlobalInformativo(alertMsg);

        }
    }

    public boolean isCambiandoPW() {
        return cambiandoPW;
    }

    public void setCambiandoPW(boolean cambiandoPW) {
        this.cambiandoPW = cambiandoPW;
    }

    public boolean accesoXRol(String pagina) {
        pagina = pagina.toUpperCase();
        if (pagina.endsWith("FORBIDDEN.XHTML")) {
            return true;
        }
        if (pagina.endsWith("MODERADOR.XHTML")) {
            return true;
        }
        if (pagina.endsWith("INDEX.XHTML")) {
            return true;
        }
        if (pagina.endsWith("CAMBIARPASSWORD.XHTML") && logged) {
            return true;
        }
        if (pagina.endsWith("CONFERENCIAS.XHTML") && logged) {
            return true;
        }
        if (pagina.endsWith("CONFIGURACION.XHTML") && logged && usuario.getIdRol().getIdRol() == 1) {
            return true;
        }
        if (pagina.endsWith("ENVIARCORREO.XHTML") && logged) {
            return true;
        }
        if (pagina.endsWith("PLANTILLASCORREO.XHTML") && logged && usuario.getIdRol().getIdRol() == 1) {
            return true;
        }
        if (pagina.endsWith("PROGRAMAS.XHTML") && logged && usuario.getIdRol().getIdRol() == 1) {
            return true;
        }
        if (pagina.endsWith("USUARIOS.XHTML") && logged && usuario.getIdRol().getIdRol() == 1) {
            return true;
        }
        return false;
    }

    public String revisarAlertas() {

        if (loggedFirstTime == true) {
            mensajeGlobalInformativo("A");
            loggedFirstTime = false;
        }

        if (loggedFail == 1) {
            mensajeGlobalError("B");
            loggedFail = 0;
        }

        if (loggedFail == 2) {
            mensajeGlobalAdvertencia("C");
            loggedFail = 0;
        }

        return "";
    }

    public void mensajeGlobalInformativo(String mensaje) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage("frmMensajeGlobal", new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, ""));
    }

    public void mensajeGlobalError(String mensaje) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage("frmMensajeGlobal", new FacesMessage(FacesMessage.SEVERITY_ERROR, mensaje, ""));
    }

    public void mensajeGlobalAdvertencia(String mensaje) {

        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage("frmMensajeGlobal", new FacesMessage(FacesMessage.SEVERITY_WARN, mensaje, ""));
    }

}
