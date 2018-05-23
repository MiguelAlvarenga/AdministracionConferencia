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

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "estilo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Estilo.findAll", query = "SELECT e FROM Estilo e"),
    @NamedQuery(name = "Estilo.findByIdEstilo", query = "SELECT e FROM Estilo e WHERE e.idEstilo = :idEstilo"),
    @NamedQuery(name = "Estilo.findByNombre", query = "SELECT e FROM Estilo e WHERE e.nombre = :nombre")})
public class Estilo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_estilo")
    private Integer idEstilo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    public Estilo() {
    }

    public Estilo(Integer idEstilo) {
        this.idEstilo = idEstilo;
    }

    public Estilo(Integer idEstilo, String nombre) {
        this.idEstilo = idEstilo;
        this.nombre = nombre;
    }

    public Integer getIdEstilo() {
        return idEstilo;
    }

    public void setIdEstilo(Integer idEstilo) {
        this.idEstilo = idEstilo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEstilo != null ? idEstilo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Estilo)) {
            return false;
        }
        Estilo other = (Estilo) object;
        if ((this.idEstilo == null && other.idEstilo != null) || (this.idEstilo != null && !this.idEstilo.equals(other.idEstilo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Estilo[ idEstilo=" + idEstilo + " ]";
    }
    
}
