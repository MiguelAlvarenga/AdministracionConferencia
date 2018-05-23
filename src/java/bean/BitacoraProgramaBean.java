package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import model.BitacoraPrograma;

@ViewScoped
@ManagedBean
public class BitacoraProgramaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BitacoraPrograma bitacoraPrograma = new BitacoraPrograma();
	private List<BitacoraPrograma> lista = new ArrayList<>();

	public BitacoraPrograma getBitacoraPrograma() {
		return bitacoraPrograma;
	}

	public void setBitacoraPrograma(BitacoraPrograma bitacoraPrograma) {
		this.bitacoraPrograma = bitacoraPrograma;
	}

	public List<BitacoraPrograma> getLista() {
		return lista;
	}

	public void setLista(List<BitacoraPrograma> lista) {
		this.lista = lista;
	}

	public void insertar() {
	}

	public void actualizar(BitacoraPrograma bitacoraPrograma) {
	}

	public void eliminar(BitacoraPrograma bitacoraPrograma) {
	}

	public void obtenerTodos() {
	}

}
