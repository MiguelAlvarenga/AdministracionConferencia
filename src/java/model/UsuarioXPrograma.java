/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "usuario_x_programa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UsuarioXPrograma.findAll", query = "SELECT u FROM UsuarioXPrograma u"),
    @NamedQuery(name = "UsuarioXPrograma.findByIdUsuarioXPrograma", query = "SELECT u FROM UsuarioXPrograma u WHERE u.idUsuarioXPrograma = :idUsuarioXPrograma"),
    @NamedQuery(name = "UsuarioXPrograma.findByActivo", query = "SELECT u FROM UsuarioXPrograma u WHERE u.activo = :activo")})
public class UsuarioXPrograma implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario_x_programa")
    private Integer idUsuarioXPrograma;
    @Column(name = "activo")
    private Boolean activo;
    @JoinColumn(name = "id_programa", referencedColumnName = "id_programa")
    @ManyToOne(optional = false)
    private Programa idPrograma;
    @JoinColumn(name = "id_responsable", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idResponsable;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idUsuarioXPrograma")
    private List<BitacoraPrograma> bitacoraProgramaList;
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "idUsuarioXPrograma")
    private List<Conferencia> conferenciaList;

    public UsuarioXPrograma() {
    }

    public UsuarioXPrograma(Integer idUsuarioXPrograma) {
        this.idUsuarioXPrograma = idUsuarioXPrograma;
    }

    public Integer getIdUsuarioXPrograma() {
        return idUsuarioXPrograma;
    }

    public void setIdUsuarioXPrograma(Integer idUsuarioXPrograma) {
        this.idUsuarioXPrograma = idUsuarioXPrograma;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Programa getIdPrograma() {
        return idPrograma;
    }

    public void setIdPrograma(Programa idPrograma) {
        this.idPrograma = idPrograma;
    }

    public Usuario getIdResponsable() {
        return idResponsable;
    }

    public void setIdResponsable(Usuario idResponsable) {
        this.idResponsable = idResponsable;
    }

    @XmlTransient
    public List<BitacoraPrograma> getBitacoraProgramaList() {
        return bitacoraProgramaList;
    }

    public void setBitacoraProgramaList(List<BitacoraPrograma> bitacoraProgramaList) {
        this.bitacoraProgramaList = bitacoraProgramaList;
    }

    @XmlTransient
    public List<Conferencia> getConferenciaList() {
        return conferenciaList;
    }

    public void setConferenciaList(List<Conferencia> conferenciaList) {
        this.conferenciaList = conferenciaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuarioXPrograma != null ? idUsuarioXPrograma.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UsuarioXPrograma)) {
            return false;
        }
        UsuarioXPrograma other = (UsuarioXPrograma) object;
        if ((this.idUsuarioXPrograma == null && other.idUsuarioXPrograma != null) || (this.idUsuarioXPrograma != null && !this.idUsuarioXPrograma.equals(other.idUsuarioXPrograma))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.UsuarioXPrograma[ idUsuarioXPrograma=" + idUsuarioXPrograma + " ]";
    }
    
}
