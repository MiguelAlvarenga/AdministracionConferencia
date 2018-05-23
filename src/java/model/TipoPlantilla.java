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
@Table(name = "tipo_plantilla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipoPlantilla.findAll", query = "SELECT t FROM TipoPlantilla t"),
    @NamedQuery(name = "TipoPlantilla.findByIdTipoPlantilla", query = "SELECT t FROM TipoPlantilla t WHERE t.idTipoPlantilla = :idTipoPlantilla"),
    @NamedQuery(name = "TipoPlantilla.findByNombre", query = "SELECT t FROM TipoPlantilla t WHERE t.nombre = :nombre")})
public class TipoPlantilla implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tipo_plantilla")
    private Integer idTipoPlantilla;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTipoPlantilla")
    private List<PlantillaCorreo> plantillaCorreoList;

    public TipoPlantilla() {
    }

    public TipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public TipoPlantilla(Integer idTipoPlantilla, String nombre) {
        this.idTipoPlantilla = idTipoPlantilla;
        this.nombre = nombre;
    }

    public Integer getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setIdTipoPlantilla(Integer idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @XmlTransient
    public List<PlantillaCorreo> getPlantillaCorreoList() {
        return plantillaCorreoList;
    }

    public void setPlantillaCorreoList(List<PlantillaCorreo> plantillaCorreoList) {
        this.plantillaCorreoList = plantillaCorreoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTipoPlantilla != null ? idTipoPlantilla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipoPlantilla)) {
            return false;
        }
        TipoPlantilla other = (TipoPlantilla) object;
        if ((this.idTipoPlantilla == null && other.idTipoPlantilla != null) || (this.idTipoPlantilla != null && !this.idTipoPlantilla.equals(other.idTipoPlantilla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.TipoPlantilla[ idTipoPlantilla=" + idTipoPlantilla + " ]";
    }
    
}
