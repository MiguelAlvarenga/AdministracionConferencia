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
@Table(name = "plantilla_correo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlantillaCorreo.findAll", query = "SELECT p FROM PlantillaCorreo p"),
    @NamedQuery(name = "PlantillaCorreo.findByIdPlantillaCorreo", query = "SELECT p FROM PlantillaCorreo p WHERE p.idPlantillaCorreo = :idPlantillaCorreo"),
    @NamedQuery(name = "PlantillaCorreo.findByNombre", query = "SELECT p FROM PlantillaCorreo p WHERE p.nombre = :nombre"),
    @NamedQuery(name = "PlantillaCorreo.findByContenido", query = "SELECT p FROM PlantillaCorreo p WHERE p.contenido = :contenido")})
public class PlantillaCorreo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_plantilla_correo")
    private Integer idPlantillaCorreo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "contenido")
    private String contenido;
    @JoinColumn(name = "id_tipo_plantilla", referencedColumnName = "id_tipo_plantilla")
    @ManyToOne(optional = false)
    private TipoPlantilla idTipoPlantilla;
    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH}, mappedBy = "idPlantillaCorreo")
    private List<Correo> correoList;

    public PlantillaCorreo() {
    }

    public PlantillaCorreo(Integer idPlantillaCorreo) {
        this.idPlantillaCorreo = idPlantillaCorreo;
    }

    public PlantillaCorreo(Integer idPlantillaCorreo, String nombre) {
        this.idPlantillaCorreo = idPlantillaCorreo;
        this.nombre = nombre;
    }

    public Integer getIdPlantillaCorreo() {
        return idPlantillaCorreo;
    }

    public void setIdPlantillaCorreo(Integer idPlantillaCorreo) {
        this.idPlantillaCorreo = idPlantillaCorreo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoPlantilla getIdTipoPlantilla() {
        return idTipoPlantilla;
    }

    public void setIdTipoPlantilla(TipoPlantilla idTipoPlantilla) {
        this.idTipoPlantilla = idTipoPlantilla;
    }

    @XmlTransient
    public List<Correo> getCorreoList() {
        return correoList;
    }

    public void setCorreoList(List<Correo> correoList) {
        this.correoList = correoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlantillaCorreo != null ? idPlantillaCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlantillaCorreo)) {
            return false;
        }
        PlantillaCorreo other = (PlantillaCorreo) object;
        if ((this.idPlantillaCorreo == null && other.idPlantillaCorreo != null) || (this.idPlantillaCorreo != null && !this.idPlantillaCorreo.equals(other.idPlantillaCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.PlantillaCorreo[ idPlantillaCorreo=" + idPlantillaCorreo + " ]";
    }
    
}
