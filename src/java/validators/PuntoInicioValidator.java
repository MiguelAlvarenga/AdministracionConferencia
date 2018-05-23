package validators;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("puntoInicioValidator")
public class PuntoInicioValidator implements Validator {

    private static final String PTOINI_PATTERN = "^(\\d{1,3}m([0-5]?\\d)s|\\d{1,3}m|([0-5]?\\d)s)$";

	private Pattern pattern;
	private Matcher matcher;

	public PuntoInicioValidator(){
		  pattern = Pattern.compile(PTOINI_PATTERN);
	}
    
    
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
	ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());	
                matcher = pattern.matcher(o.toString());
		if(!matcher.matches()){
			FacesMessage msg =
				new FacesMessage(resourceBundle.getString("conferenciaFormatoInvalido"),
						resourceBundle.getString("conferenciaFormatoInvalido"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

	}
        }

}