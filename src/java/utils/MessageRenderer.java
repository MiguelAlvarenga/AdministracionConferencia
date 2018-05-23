package utils;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessages;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.context.ResponseWriter;

import com.sun.faces.renderkit.Attribute;
import com.sun.faces.renderkit.AttributeManager;
import com.sun.faces.renderkit.RenderKitUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage.Severity;

@FacesRenderer(componentFamily = "javax.faces.Message", rendererType = "javax.faces.Message")
public class MessageRenderer extends com.sun.faces.renderkit.html_basic.MessageRenderer {

	private static final Attribute[] ATTRIBUTES
			  = AttributeManager.getAttributes(AttributeManager.Key.MESSAGESMESSAGES);

	@Override
	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		super.encodeBegin(context, component);
	}

	@Override
	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {

		rendererParamsNotNull(context, component);
		if (!shouldEncode(component)) {
			return;
		}

		boolean mustRender = shouldWriteIdAttribute(component);
		UIMessages messages = (UIMessages) component;
		ResponseWriter writer = context.getResponseWriter();

		String clientId = ((UIMessages) component).getFor();
		if (clientId == null && messages.isGlobalOnly()) {
			clientId = "";
		}

		Iterator messageIt = getMessageIter(context, clientId, component);
		if (!messageIt.hasNext()) {
			if (mustRender) {
				if ("javax_faces_developmentstage_messages".equals(component.getId())) {
					return;
				}
				writer.startElement("div", component);
				writeIdAttributeIfNecessary(context, writer, component);
				writer.endElement("div");
			}
			return;
		}

		writeIdAttributeIfNecessary(context, writer, component);
		RenderKitUtils.renderPassThruAttributes(context, writer, component, ATTRIBUTES);

		Map<Severity, List<FacesMessage>> msgs = new HashMap<Severity, List<FacesMessage>>();
		msgs.put(FacesMessage.SEVERITY_INFO, new ArrayList<FacesMessage>());
		msgs.put(FacesMessage.SEVERITY_WARN, new ArrayList<FacesMessage>());
		msgs.put(FacesMessage.SEVERITY_ERROR, new ArrayList<FacesMessage>());
		msgs.put(FacesMessage.SEVERITY_FATAL, new ArrayList<FacesMessage>());

		while (messageIt.hasNext()) {
			FacesMessage curMessage = (FacesMessage) messageIt.next();
			if (curMessage.isRendered() && !messages.isRedisplay()) {
				continue;
			}
			msgs.get(curMessage.getSeverity()).add(curMessage);
		}

		List<FacesMessage> severityMessages = msgs.get(FacesMessage.SEVERITY_FATAL);
		if (!severityMessages.isEmpty()) {
			encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_FATAL, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_ERROR);
		if (!severityMessages.isEmpty()) {
			encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_ERROR, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_WARN);
		if (!severityMessages.isEmpty()) {
			encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_WARN, severityMessages);
		}

		severityMessages = msgs.get(FacesMessage.SEVERITY_INFO);
		if (!severityMessages.isEmpty()) {
			encodeSeverityMessages(context, messages, FacesMessage.SEVERITY_INFO, severityMessages);
		}
	}

	private void encodeSeverityMessages(FacesContext facesContext, UIMessages uiMessages,
			  Severity severity, List<FacesMessage> messages) throws IOException {
		ResponseWriter writer = facesContext.getResponseWriter();
		String alertSeverityClass = "";
		String iconSeverityClass = "";

		if (FacesMessage.SEVERITY_INFO.equals(severity)) {
			alertSeverityClass = "alert-info fade in globalAlert";
			iconSeverityClass = "glyphicon glyphicon-info-sign";
		} else if (FacesMessage.SEVERITY_WARN.equals(severity)) {
			alertSeverityClass = "alert-warning fade in globalAlert";
			iconSeverityClass = "glyphicon glyphicon-exclamation-sign";
		} else if (FacesMessage.SEVERITY_ERROR.equals(severity)) {
			alertSeverityClass = "alert-danger fade in globalAlert";
			iconSeverityClass = "glyphicon glyphicon-remove-sign";
		} else if (FacesMessage.SEVERITY_FATAL.equals(severity)) {
			alertSeverityClass = "alert-danger fade in globalAlert";
			iconSeverityClass = "glyphicon glyphicon-remove-sign";
		}

		if (!FacesMessage.SEVERITY_WARN.equals(severity)) {

			writer.startElement("div", null);
			writer.writeAttribute("class", "alert " + alertSeverityClass, "alert " + alertSeverityClass);
			writer.writeAttribute("role", "alert", "alert");
			writer.startElement("a", uiMessages);
			writer.writeAttribute("class", "close", "class");
			writer.writeAttribute("data-dismiss", "alert", "data-dismiss");
			writer.writeAttribute("href", "#", "href");
			writer.write("&times;");
			writer.endElement("a");

			writer.startElement("span", null);
			writer.writeAttribute("class", iconSeverityClass, "class");

			writer.writeAttribute("aria-hidden", "true", "aria-hidden");
			writer.endElement("span");

			writer.writeText("   ", null);

			for (FacesMessage msg : messages) {
				String summary = msg.getSummary() != null ? msg.getSummary() : "";
				String detail = msg.getDetail() != null ? msg.getDetail() : summary;

				if (uiMessages.isShowSummary()) {
					writer.startElement("span", uiMessages);
					writer.writeText(summary, uiMessages, null);
					writer.endElement("span");
				}

				if (uiMessages.isShowDetail()) {
					writer.writeText(" " + detail, null);
				}

				//  writer.endElement("li");
				msg.rendered();
			}
			//writer.endElement("ul");
			writer.endElement("div");

		} else {

			for (FacesMessage msg : messages) {
				String summary = msg.getSummary() != null ? msg.getSummary() : "";
				String detail = msg.getDetail() != null ? msg.getDetail() : summary;

				if (uiMessages.isShowSummary()) {

					writer.startElement("h2", uiMessages);
					writer.writeAttribute("class", "text-muted red-color-text", "class");

					//Icono de Signo de Exclamación.
					writer.startElement("span", null);
					writer.writeAttribute("class", "glyphicon glyphicon-exclamation-sign", "class");
					writer.writeAttribute("aria-hidden", "true", "aria-hidden");
					writer.endElement("span");
					writer.writeText("   ", null);

					writer.writeText(summary, uiMessages, null);
					writer.endElement("h2");

				}

				if (uiMessages.isShowDetail()) {
					writer.writeText(" " + detail, null);
				}

				msg.rendered();
			}

		}

	}
}
