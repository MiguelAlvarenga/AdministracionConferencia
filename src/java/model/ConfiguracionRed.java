/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Type;

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "configuracion_red")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConfiguracionRed.findAll", query = "SELECT c FROM ConfiguracionRed c"),
    @NamedQuery(name = "ConfiguracionRed.findByIdConfiguracionRed", query = "SELECT c FROM ConfiguracionRed c WHERE c.idConfiguracionRed = :idConfiguracionRed"),
    @NamedQuery(name = "ConfiguracionRed.findByNombreDominio", query = "SELECT c FROM ConfiguracionRed c WHERE c.nombreDominio = :nombreDominio"),
    @NamedQuery(name = "ConfiguracionRed.findByCorreo", query = "SELECT c FROM ConfiguracionRed c WHERE c.correo = :correo"),
    @NamedQuery(name = "ConfiguracionRed.findByContrasena", query = "SELECT c FROM ConfiguracionRed c WHERE c.contrasena = :contrasena"),
    @NamedQuery(name = "ConfiguracionRed.findByPuerto", query = "SELECT c FROM ConfiguracionRed c WHERE c.puerto = :puerto"),
    @NamedQuery(name = "ConfiguracionRed.findByEncriptacion", query = "SELECT c FROM ConfiguracionRed c WHERE c.encriptacion = :encriptacion"),
    @NamedQuery(name = "ConfiguracionRed.findByIpBbb", query = "SELECT c FROM ConfiguracionRed c WHERE c.ipBbb = :ipBbb"),
    @NamedQuery(name = "ConfiguracionRed.findBySalt", query = "SELECT c FROM ConfiguracionRed c WHERE c.salt = :salt"),
    @NamedQuery(name = "ConfiguracionRed.findByLogouturl", query = "SELECT c FROM ConfiguracionRed c WHERE c.logouturl = :logouturl"),
    @NamedQuery(name = "ConfiguracionRed.findByTransporte", query = "SELECT c FROM ConfiguracionRed c WHERE c.transporte = :transporte"),
    @NamedQuery(name = "ConfiguracionRed.findByNombre", query = "SELECT c FROM ConfiguracionRed c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "ConfiguracionRed.findByContenttype", query = "SELECT c FROM ConfiguracionRed c WHERE c.contenttype = :contenttype")})
public class ConfiguracionRed implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_configuracion_red")
    private Integer idConfiguracionRed;
    @Column(name = "nombre_dominio")
    private String nombreDominio;
    @Column(name = "correo")
    private String correo;
    @Column(name = "contrasena")
    private String contrasena;
    @Column(name = "puerto")
    private String puerto;
    @Column(name = "encriptacion")
    private String encriptacion;
    @Column(name = "ip_bbb")
    private String ipBbb;
    @Column(name = "salt")
    private String salt;
    @Column(name = "logouturl")
    private String logouturl;
    @Column(name = "transporte")
    private String transporte;
    @Column(name = "nombre")
    private String nombre;
    @Type(type="org.hibernate.type.BinaryType") 
    @Column(name = "documento")
    private byte[] documento;
    @Column(name = "contenttype")
    private String contenttype;

    public ConfiguracionRed() {
    }

    public ConfiguracionRed(Integer idConfiguracionRed) {
        this.idConfiguracionRed = idConfiguracionRed;
    }

    public Integer getIdConfiguracionRed() {
        return idConfiguracionRed;
    }

    public void setIdConfiguracionRed(Integer idConfiguracionRed) {
        this.idConfiguracionRed = idConfiguracionRed;
    }

    public String getNombreDominio() {
        return nombreDominio;
    }

    public void setNombreDominio(String nombreDominio) {
        this.nombreDominio = nombreDominio;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getEncriptacion() {
        return encriptacion;
    }

    public void setEncriptacion(String encriptacion) {
        this.encriptacion = encriptacion;
    }

    public String getIpBbb() {
        return ipBbb;
    }

    public void setIpBbb(String ipBbb) {
        this.ipBbb = ipBbb;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLogouturl() {
        return logouturl;
    }

    public void setLogouturl(String logouturl) {
        this.logouturl = logouturl;
    }

    public String getTransporte() {
        return transporte;
    }

    public void setTransporte(String transporte) {
        this.transporte = transporte;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getDocumento() {
        return documento;
    }

    public void setDocumento(byte[] documento) {
        this.documento = documento;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String contenttype) {
        this.contenttype = contenttype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConfiguracionRed != null ? idConfiguracionRed.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConfiguracionRed)) {
            return false;
        }
        ConfiguracionRed other = (ConfiguracionRed) object;
        if ((this.idConfiguracionRed == null && other.idConfiguracionRed != null) || (this.idConfiguracionRed != null && !this.idConfiguracionRed.equals(other.idConfiguracionRed))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ConfiguracionRed[ idConfiguracionRed=" + idConfiguracionRed + " ]";
    }
    
}
