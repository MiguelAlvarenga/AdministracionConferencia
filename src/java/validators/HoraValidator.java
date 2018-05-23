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

@FacesValidator("horaValidator")
public class HoraValidator implements Validator {

    private static final String HORA_PATTERN = "\\b((1[0-2]|0?[1-9]):([0-5][0-9]) ([AaPp][Mm]))";

	private Pattern pattern;
	private Matcher matcher;

	public HoraValidator(){
		  pattern = Pattern.compile(HORA_PATTERN);
	}
    
    
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
	ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());	
                matcher = pattern.matcher(o.toString());
		if(!matcher.matches()){
			FacesMessage msg =
				new FacesMessage(resourceBundle.getString("conferenciaHoraVal"),
						resourceBundle.getString("conferenciaHoraVal"));
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);

	}
        }

}