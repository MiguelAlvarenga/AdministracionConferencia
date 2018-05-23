package utils;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name="localeBean")
@SessionScoped
public class LocaleBean {

	private Locale locale;
	private String lang;
	
	@PostConstruct
	public void init(){		
		locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
	}	
	
	public Locale getLocale() {
		return locale;
	}
	public void changeLanguage() {
		this.locale = new Locale(lang);
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
		
}
