package bean;

import dao.EstiloDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.Estilo;

@ViewScoped
@ManagedBean
public class EstiloBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Estilo estilo = new Estilo();
	private List<Estilo> lista = new ArrayList<>();

	//Propios del XHTML
	private String tituloModal;
	private boolean nuevo = true;	//true: Nuevo registro; false: Actualizar registro.

	public EstiloBean() {
		obtenerTodos();

	}

	public Estilo getEstilo() {
		return estilo;
	}

	public void setEstilo(Estilo estilo) {
		this.estilo = estilo;
	}

	public List<Estilo> getLista() {
		return lista;
	}

	public void setLista(List<Estilo> lista) {
		this.lista = lista;
	}

	public String getTituloModal() {
		if (nuevo) {
			tituloModal = "Nuevo Estilo";
		} else {
			tituloModal = "Actualizar Estilo";
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

	public void guardar() {
		EstiloDao daoEstilo = new EstiloDao();
		if (nuevo) {
			daoEstilo.insertar(estilo);
		} else {
			daoEstilo.actualizar(estilo);
		}

	}

	public void eliminar() {

		EstiloDao daoEstilo = new EstiloDao();
		daoEstilo.eliminar(estilo.getIdEstilo());
		lista.remove(estilo);
		limpiar();

	}

	public void obtenerTodos() {

		EstiloDao daoEstilo = new EstiloDao();
		lista = daoEstilo.obtenerTodos();

	}

	public void limpiar() {
		estilo = new Estilo();
	}

}
