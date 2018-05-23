/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "bitacora_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BitacoraPrograma.findAll", query = "SELECT b FROM BitacoraPrograma b"),
    @NamedQuery(name = "BitacoraPrograma.findByIdBitacoraPrograma", query = "SELECT b FROM BitacoraPrograma b WHERE b.idBitacoraPrograma = :idBitacoraPrograma"),
    @NamedQuery(name = "BitacoraPrograma.findByFechaModificacion", query = "SELECT b FROM BitacoraPrograma b WHERE b.fechaModificacion = :fechaModificacion"),
    @NamedQuery(name = "BitacoraPrograma.findByHoraModificacion", query = "SELECT b FROM BitacoraPrograma b WHERE b.horaModificacion = :horaModificacion"),
    @NamedQuery(name = "BitacoraPrograma.findByActivo", query = "SELECT b FROM BitacoraPrograma b WHERE b.activo = :activo")})
public class BitacoraPrograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bitacora_programa")
    private Integer idBitacoraPrograma;
    @Column(name = "fecha_modificacion")
    @Temporal(TemporalType.DATE)
    private Date fechaModificacion;
    @Column(name = "hora_modificacion")
    private String horaModificacion;
    @Basic(optional = false)
    @Column(name = "activo")
    private boolean activo;
    @JoinColumn(name = "id_usuario_x_programa", referencedColumnName = "id_usuario_x_programa")
    @ManyToOne(optional = false)
    private UsuarioXPrograma idUsuarioXPrograma;

    public BitacoraPrograma() {
    }

    public BitacoraPrograma(Integer idBitacoraPrograma) {
        this.idBitacoraPrograma = idBitacoraPrograma;
    }

    public BitacoraPrograma(Integer idBitacoraPrograma, boolean activo) {
        this.idBitacoraPrograma = idBitacoraPrograma;
        this.activo = activo;
    }

    public Integer getIdBitacoraPrograma() {
        return idBitacoraPrograma;
    }

    public void setIdBitacoraPrograma(Integer idBitacoraPrograma) {
        this.idBitacoraPrograma = idBitacoraPrograma;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public String getHoraModificacion() {
        return horaModificacion;
    }

    public void setHoraModificacion(String horaModificacion) {
        this.horaModificacion = horaModificacion;
    }

    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public UsuarioXPrograma getIdUsuarioXPrograma() {
        return idUsuarioXPrograma;
    }

    public void setIdUsuarioXPrograma(UsuarioXPrograma idUsuarioXPrograma) {
        this.idUsuarioXPrograma = idUsuarioXPrograma;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacoraPrograma != null ? idBitacoraPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacoraPrograma)) {
            return false;
        }
        BitacoraPrograma other = (BitacoraPrograma) object;
        if ((this.idBitacoraPrograma == null && other.idBitacoraPrograma != null) || (this.idBitacoraPrograma != null && !this.idBitacoraPrograma.equals(other.idBitacoraPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.BitacoraPrograma[ idBitacoraPrograma=" + idBitacoraPrograma + " ]";
    }
    
}
