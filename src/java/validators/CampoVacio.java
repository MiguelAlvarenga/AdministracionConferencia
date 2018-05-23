package validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("campoVacio")
public class CampoVacio implements Validator {

	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
		String texto = (String) o;

		if (texto.isEmpty()) {
			throw new ValidatorException(new FacesMessage("Error de validación: se necesita un valor."));
		}

	}

}
