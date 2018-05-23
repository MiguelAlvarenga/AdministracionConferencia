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
@Table(name = "estado_conferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EstadoConferencia.findAll", query = "SELECT e FROM EstadoConferencia e"),
    @NamedQuery(name = "EstadoConferencia.findByIdEstadoConferencia", query = "SELECT e FROM EstadoConferencia e WHERE e.idEstadoConferencia = :idEstadoConferencia"),
    @NamedQuery(name = "EstadoConferencia.findByNombre", query = "SELECT e FROM EstadoConferencia e WHERE e.nombre = :nombre")})
public class EstadoConferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estado_conferencia")
    private Integer idEstadoConferencia;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEstadoConferencia")
    private List<Conferencia> conferenciaList;

    public EstadoConferencia() {
    }

    public EstadoConferencia(Integer idEstadoConferencia) {
        this.idEstadoConferencia = idEstadoConferencia;
    }

    public EstadoConferencia(Integer idEstadoConferencia, String nombre) {
        this.idEstadoConferencia = idEstadoConferencia;
        this.nombre = nombre;
    }

    public Integer getIdEstadoConferencia() {
        return idEstadoConferencia;
    }

    public void setIdEstadoConferencia(Integer idEstadoConferencia) {
        this.idEstadoConferencia = idEstadoConferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        hash += (idEstadoConferencia != null ? idEstadoConferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EstadoConferencia)) {
            return false;
        }
        EstadoConferencia other = (EstadoConferencia) object;
        if ((this.idEstadoConferencia == null && other.idEstadoConferencia != null) || (this.idEstadoConferencia != null && !this.idEstadoConferencia.equals(other.idEstadoConferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.EstadoConferencia[ idEstadoConferencia=" + idEstadoConferencia + " ]";
    }
    
}
