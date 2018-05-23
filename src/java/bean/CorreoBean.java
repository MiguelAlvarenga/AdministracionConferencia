package bean;

import dao.ConferenciaDao;
import dao.CorreoDao;
import dao.ProgramaDao;
import dao.UsuarioXProgramaDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Correo;
import model.PlantillaCorreo;
import model.Conferencia;
import model.Programa;
import model.UsuarioXPrograma;

@ViewScoped
@ManagedBean
public class CorreoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Correo correo = new Correo();
	private List<Correo> lista = new ArrayList<>();

	//Propios del XHTML
	private String tituloModal;
	private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

	//Otros
	private int idPlantillaCorreo;
	private Conferencia conferencia;
	private int idCorreoAux;

	public CorreoBean() {
	}

	public Correo getCorreo() {
		return correo;
	}

	public void setCorreo(Correo correo) {
		this.correo = correo;
	}

	public List<Correo> getLista() {
		return lista;
	}

	public void setLista(List<Correo> lista) {
		this.lista = lista;
	}

	public String getTituloModal() {
		if (nuevo) {
                        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                        tituloModal = resourceBundle.getString("conferenciaNuevoCorreo");
			
		} else {
			ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
                        tituloModal = resourceBundle.getString("conferenciaActualizarCorreo");
		}
		return tituloModal;
	}

	public void setTituloModal(String tituloModal) {
		this.tituloModal = tituloModal;
	}

	public boolean isNuevo() {
		return nuevo;
	}

	public void setNuevo(boolean nuevo) {
		this.nuevo = nuevo;
	}

	public int getIdCorreoAux() {
		return idCorreoAux;
	}

	public void setIdCorreoAux(int idCorreoAux) {
		this.idCorreoAux = idCorreoAux;
	}

	public int getIdPlantillaCorreo() {
		return idPlantillaCorreo;
	}

	public void setIdPlantillaCorreo(int idPlantillaCorreo) {
		this.idPlantillaCorreo = idPlantillaCorreo;
	}

	public Conferencia getConferencia() {
		return conferencia;
	}

	public void setConferencia(Conferencia conferencia) {
		this.conferencia = conferencia;
	}

	public void guardar() {

		CorreoDao dao = new CorreoDao();

		if (nuevo) {

			correo.setIdCorreo(null);

			PlantillaCorreo plantillaCorreo = new PlantillaCorreo();
			plantillaCorreo.setIdPlantillaCorreo(idPlantillaCorreo);
			correo.setIdPlantillaCorreo(plantillaCorreo);

			correo.setIdConferencia(conferencia);

			System.out.println("");
			System.out.println("idPlantillaCorreo" + idPlantillaCorreo);
			System.out.println("idConferencia" + conferencia.getIdConferencia());
			System.out.println("correo" + correo.getContenido());
			System.out.println("");
			System.out.println("");

			dao.insertar(correo);
		} else {
			dao.actualizar(correo);
		}

	}

	public void eliminar() {
		CorreoDao dao = new CorreoDao();
		dao.eliminar(correo.getIdCorreo());
		lista.remove(correo);
		limpiar();
	}

	public void limpiar() {
		correo = new Correo();
		conferencia = new Conferencia();
	}

	public final void obtenerTodos() {
		CorreoDao dao = new CorreoDao();
		lista = dao.obtenerTodos();
	}

	public final void obtenerTodosXConferencia() {
		CorreoDao dao = new CorreoDao();
		lista = dao.obtenerTodosXConferencia(conferencia);
	}

	public final void cargarCorreo() {
		correo = BuscarCorreo();
	}

	public Correo BuscarCorreo() {
		Correo c = null;
		for (Correo elemento : lista) {
			if (elemento.getIdCorreo().equals(idCorreoAux)) {
				c = elemento;
				break;
			}
		}
		return c;
	}

}
