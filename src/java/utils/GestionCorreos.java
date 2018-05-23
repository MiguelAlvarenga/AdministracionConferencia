package utils;

import dao.ConfiguracionRedDao;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.ConfiguracionRed;

public class GestionCorreos {

    public void EnviarCorreos(List<String> correosDestino, List<String> correosCC, String asunto, String mensaje) {

        ConfiguracionRed cr = new ConfiguracionRed();

        try {
            
            ConfiguracionRedDao daoRed = new ConfiguracionRedDao();
            cr = daoRed.obtener();

            /*Obtenemos de la "base" los parametros del servidor de correo
            cr.setNombreDominio("mail.salud.gob.sv");
            cr.setCorreo("bbb@salud.gob.sv");
            cr.setContrasena("Bbb_3100");
            cr.setPuerto("587");
            cr.setEncriptacion("");
            transporte = "smtp"; //Es de crear, por que se eliminó
            //*/

            // Creamos un Properties para las propiedades de conexión
            Properties props = new Properties();

            //Almacenamos los valores de la Porperties
            props.setProperty("mail.smtp.host", cr.getNombreDominio());	//SMTP Host
            props.setProperty("mail.smtp.port", cr.getPuerto());			//Puerto de comunicación
            props.setProperty("mail.smtp.user", cr.getCorreo());			//Desde
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.auth", "true");
            props.put("mail.smtp.ssl.trust", "mail.salud.gob.sv");

            // Preparamos la sesión
            System.out.println("3. Estableciendo Sesión...");
            Session session = Session.getDefaultInstance(props);
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(cr.getCorreo()));

            System.out.println("4. Estableciendo los destinatarios...");
            for (int i = 0; i < correosDestino.size(); i++) {
                System.out.println(correosDestino.get(i));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(correosDestino.get(i)));
            }
            if (correosCC != null) {
                for (int i = 0; i < correosCC.size(); i++) {
                    System.out.println("a" + correosCC.size());
                    System.out.println(correosCC.get(i));
                    if (correosCC.get(i) != null && !correosCC.get(i).isEmpty()) {
                        message.addRecipient(Message.RecipientType.CC, new InternetAddress(correosCC.get(i)));
                    }
                }
            }

            System.out.println("5. Estableciendo el Asunto del correo");
            message.setSubject(asunto);

            System.out.println("6. Estableciendo el mensaje del correo");
            message.setContent(mensaje, "text/html");

            System.out.println("7. Enviado el correo... por favor, espere un momento.");
            Transport t = session.getTransport(cr.getTransporte());
            t.connect(cr.getCorreo(), cr.getContrasena());
            t.sendMessage(message, message.getAllRecipients());

            // Cierre.
            System.out.println("8. El mensaje fue enviado correctamente!");
            t.close();

        } catch (MessagingException e) {
            System.out.println("MessagingException: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
