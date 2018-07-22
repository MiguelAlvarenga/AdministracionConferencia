/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author garo1
 */
@Entity
@Table(name = "conferencia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conferencia.findAll", query = "SELECT c FROM Conferencia c ORDER BY c.idConferencia DESC"),
    @NamedQuery(name = "Conferencia.findByIdConferencia", query = "SELECT c FROM Conferencia c WHERE c.idConferencia = :idConferencia"),
    @NamedQuery(name = "Conferencia.findByNombre", query = "SELECT c FROM Conferencia c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Conferencia.findByTipoConferencia", query = "SELECT c FROM Conferencia c WHERE c.tipoConferencia = :tipoConferencia"),
    @NamedQuery(name = "Conferencia.findByGrabacion", query = "SELECT c FROM Conferencia c WHERE c.grabacion = :grabacion"),
    @NamedQuery(name = "Conferencia.findByPonentes", query = "SELECT c FROM Conferencia c WHERE c.ponentes = :ponentes"),
    @NamedQuery(name = "Conferencia.findByModeradores", query = "SELECT c FROM Conferencia c WHERE c.moderadores = :moderadores"),
    @NamedQuery(name = "Conferencia.findByFechaCreacion", query = "SELECT c FROM Conferencia c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Conferencia.findByHoraCreacion", query = "SELECT c FROM Conferencia c WHERE c.horaCreacion = :horaCreacion"),
    @NamedQuery(name = "Conferencia.findByFechaPonencia", query = "SELECT c FROM Conferencia c WHERE c.fechaPonencia = :fechaPonencia"),
    @NamedQuery(name = "Conferencia.findByHoraPonencia", query = "SELECT c FROM Conferencia c WHERE c.horaPonencia = :horaPonencia"),
    @NamedQuery(name = "Conferencia.findByDuracion", query = "SELECT c FROM Conferencia c WHERE c.duracion = :duracion"),
    @NamedQuery(name = "Conferencia.findByPuntoInicio", query = "SELECT c FROM Conferencia c WHERE c.puntoInicio = :puntoInicio"),
    @NamedQuery(name = "Conferencia.findByDescripcion", query = "SELECT c FROM Conferencia c WHERE c.descripcion = :descripcion"),
    @NamedQuery(name = "Conferencia.findByAttendeepw", query = "SELECT c FROM Conferencia c WHERE c.attendeepw = :attendeepw"),
    @NamedQuery(name = "Conferencia.findByModeratorpw", query = "SELECT c FROM Conferencia c WHERE c.moderatorpw = :moderatorpw"),
    @NamedQuery(name = "Conferencia.findByModeratorurl", query = "SELECT c FROM Conferencia c WHERE c.moderatorurl = :moderatorurl"),
    @NamedQuery(name = "Conferencia.findByChecksumattendee", query = "SELECT c FROM Conferencia c WHERE c.checksumattendee = :checksumattendee"),
    @NamedQuery(name = "Conferencia.findByAttendeeurl", query = "SELECT c FROM Conferencia c WHERE c.attendeeurl = :attendeeurl"),
    @NamedQuery(name = "Conferencia.findByRecordingurl", query = "SELECT c FROM Conferencia c WHERE c.recordingurl = :recordingurl"), 
    @NamedQuery(name = "Conferencia.findByIdUsuario", query = "SELECT c FROM Conferencia c WHERE c.idUsuario = :idUsuario")})
public class Conferencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_conferencia")
    private Integer idConferencia;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "tipo_conferencia")
    private boolean tipoConferencia;
    @Basic(optional = false)
    @Column(name = "grabacion")
    private boolean grabacion;
    @Column(name = "ponentes")
    private String ponentes;
    @Column(name = "moderadores")
    private String moderadores;
    @Basic(optional = false)
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @Column(name = "hora_creacion")
    private String horaCreacion;
    @Column(name = "fecha_ponencia")
    @Temporal(TemporalType.DATE)
    private Date fechaPonencia;
    @Column(name = "hora_ponencia")
    private String horaPonencia;
    @Column(name = "duracion")
    private String duracion;
    @Column(name = "punto_inicio")
    private String puntoInicio;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "attendeepw")
    private String attendeepw;
    @Column(name = "moderatorpw")
    private String moderatorpw;
    @Column(name = "moderatorurl")
    private String moderatorurl;
    @Column(name = "checksumattendee")
    private String checksumattendee;
    @Column(name = "attendeeurl")
    private String attendeeurl;
    @Column(name = "recordingurl")
    private String recordingurl;
    @JoinColumn(name = "id_estado_conferencia", referencedColumnName = "id_estado_conferencia")
    @ManyToOne(optional = false)
    private EstadoConferencia idEstadoConferencia;
    @JoinColumn(name = "id_usuario_x_programa", referencedColumnName = "id_usuario_x_programa")
    @ManyToOne(optional = false)
    private UsuarioXPrograma idUsuarioXPrograma;
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuario idUsuario;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConferencia")
    private List<Documento> documentoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idConferencia")
    private List<Correo> correoList;

    public Conferencia() {
    }

    public Conferencia(Integer idConferencia) {
        this.idConferencia = idConferencia;
    }

    public Conferencia(Integer idConferencia, String nombre, boolean tipoConferencia, boolean grabacion, Date fechaCreacion) {
        this.idConferencia = idConferencia;
        this.nombre = nombre;
        this.tipoConferencia = tipoConferencia;
        this.grabacion = grabacion;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getIdConferencia() {
        return idConferencia;
    }

    public void setIdConferencia(Integer idConferencia) {
        this.idConferencia = idConferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getTipoConferencia() {
        return tipoConferencia;
    }

    public void setTipoConferencia(boolean tipoConferencia) {
        this.tipoConferencia = tipoConferencia;
    }

    public boolean getGrabacion() {
        return grabacion;
    }

    public void setGrabacion(boolean grabacion) {
        this.grabacion = grabacion;
    }

    public String getPonentes() {
        return ponentes;
    }

    public void setPonentes(String ponentes) {
        this.ponentes = ponentes;
    }

    public String getModeradores() {
        return moderadores;
    }

    public void setModeradores(String moderadores) {
        this.moderadores = moderadores;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Date getFechaPonencia() {
        return fechaPonencia;
    }

    public void setFechaPonencia(Date fechaPonencia) {
        this.fechaPonencia = fechaPonencia;
    }

    public String getHoraPonencia() {
        return horaPonencia;
    }

    public void setHoraPonencia(String horaPonencia) {
        this.horaPonencia = horaPonencia;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getPuntoInicio() {
        return puntoInicio;
    }

    public void setPuntoInicio(String puntoInicio) {
        this.puntoInicio = puntoInicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAttendeepw() {
        return attendeepw;
    }

    public void setAttendeepw(String attendeepw) {
        this.attendeepw = attendeepw;
    }

    public String getModeratorpw() {
        return moderatorpw;
    }

    public void setModeratorpw(String moderatorpw) {
        this.moderatorpw = moderatorpw;
    }

    public String getModeratorurl() {
        return moderatorurl;
    }

    public void setModeratorurl(String moderatorurl) {
        this.moderatorurl = moderatorurl;
    }

    public String getChecksumattendee() {
        return checksumattendee;
    }

    public void setChecksumattendee(String checksumattendee) {
        this.checksumattendee = checksumattendee;
    }

    public String getAttendeeurl() {
        return attendeeurl;
    }

    public void setAttendeeurl(String attendeeurl) {
        this.attendeeurl = attendeeurl;
    }

    public String getRecordingurl() {
        return recordingurl;
    }

    public void setRecordingurl(String recordingurl) {
        this.recordingurl = recordingurl;
    }

    public EstadoConferencia getIdEstadoConferencia() {
        return idEstadoConferencia;
    }

    public void setIdEstadoConferencia(EstadoConferencia idEstadoConferencia) {
        this.idEstadoConferencia = idEstadoConferencia;
    }

    public UsuarioXPrograma getIdUsuarioXPrograma() {
        return idUsuarioXPrograma;
    }

    public void setIdUsuarioXPrograma(UsuarioXPrograma idUsuarioXPrograma) {
        this.idUsuarioXPrograma = idUsuarioXPrograma;
    }
    
    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @XmlTransient
    public List<Documento> getDocumentoList() {
        return documentoList;
    }

    public void setDocumentoList(List<Documento> documentoList) {
        this.documentoList = documentoList;
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
        hash += (idConferencia != null ? idConferencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conferencia)) {
            return false;
        }
        Conferencia other = (Conferencia) object;
        if ((this.idConferencia == null && other.idConferencia != null) || (this.idConferencia != null && !this.idConferencia.equals(other.idConferencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Conferencia[ idConferencia=" + idConferencia + " ]";
    }
    
}
