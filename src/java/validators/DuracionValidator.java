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

@FacesValidator("duracionValidator")
public class DuracionValidator implements Validator {

    private static final String DUR_PATTERN = "^[0-9]{1,3}$";

	private Pattern pattern;
	private Matcher matcher;

	public DuracionValidator(){
		  pattern = Pattern.compile(DUR_PATTERN);
	}
    
    
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
	ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());	
                matcher = pattern.matcher(o.toString());
		if(!matcher.matches()){
			FacesMessage msg =
				new FacesMessage(resourceBundle.getString("conferenciaDuracion"),
						resourceBundle.getString("conferenciaDuracion"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

	}
        }

}