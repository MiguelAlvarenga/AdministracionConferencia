package bean;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import model.Programa;

@SessionScoped
@ManagedBean
public class ProgramaAuxBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private Programa programa = new Programa();

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

}
