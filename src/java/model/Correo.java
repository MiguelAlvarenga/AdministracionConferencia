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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "correo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Correo.findAll", query = "SELECT c FROM Correo c"),
    @NamedQuery(name = "Correo.findByIdCorreo", query = "SELECT c FROM Correo c WHERE c.idCorreo = :idCorreo"),
    @NamedQuery(name = "Correo.findByContenido", query = "SELECT c FROM Correo c WHERE c.contenido = :contenido")})
public class Correo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_correo")
    private Integer idCorreo;
    @Column(name = "contenido")
    private String contenido;
    @JoinColumn(name = "id_conferencia", referencedColumnName = "id_conferencia")
    @ManyToOne(optional = false)
    private Conferencia idConferencia;
    @JoinColumn(name = "id_plantilla_correo", referencedColumnName = "id_plantilla_correo")
    @ManyToOne(optional = false)
    private PlantillaCorreo idPlantillaCorreo;

    public Correo() {
    }

    public Correo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public Integer getIdCorreo() {
        return idCorreo;
    }

    public void setIdCorreo(Integer idCorreo) {
        this.idCorreo = idCorreo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Conferencia getIdConferencia() {
        return idConferencia;
    }

    public void setIdConferencia(Conferencia idConferencia) {
        this.idConferencia = idConferencia;
    }

    public PlantillaCorreo getIdPlantillaCorreo() {
        return idPlantillaCorreo;
    }

    public void setIdPlantillaCorreo(PlantillaCorreo idPlantillaCorreo) {
        this.idPlantillaCorreo = idPlantillaCorreo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCorreo != null ? idCorreo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Correo)) {
            return false;
        }
        Correo other = (Correo) object;
        if ((this.idCorreo == null && other.idCorreo != null) || (this.idCorreo != null && !this.idCorreo.equals(other.idCorreo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Correo[ idCorreo=" + idCorreo + " ]";
    }
    
}
