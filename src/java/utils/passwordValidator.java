/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author garo1
 */
@FacesValidator("passwordValidator")
public class passwordValidator implements Validator {

	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
		String password = (String) o;
		String confirm = (String) uic.getAttributes().get("confirm");
                ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
		if (password == null || confirm == null) {
			return; // Just ignore and let required="true" do its job.
		}

		if (!password.equals(confirm)) {
			throw new ValidatorException(new FacesMessage(resourceBundle.getString("SesionContraNoCoin")));
		}
	}

}
