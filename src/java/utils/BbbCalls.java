/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import bean.ConfiguracionRedBean;
import dao.DocumentoDao;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import model.Conferencia;
import model.Documento;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import org.xml.sax.InputSource;

/**
 *
 * @author garo1
 */
public class BbbCalls {

    public static String crearConferencia(String name, String meetingId, String duracion, String record) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();
        String logoutURL = crb.getConfiguracion().getLogouturl();

        String parametros = "name=" + urlEncode(name) + "&meetingID=" + urlEncode(meetingId)
                + "&duration=" + urlEncode(duracion) + "&record=" + urlEncode(record)
                + "&logoutURL=" + urlEncode(logoutURL);

        String url = "http://" + ip + "/bigbluebutton/api/create?";

        url = url + parametros + "&checksum=" + checksum("create" + parametros + salt);

        return getURL(url);
    }

    public static String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getURL(String url) {
        StringBuffer response = null;

        try {
            URL u = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();

            httpConnection.setUseCaches(false);
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("GET");

            httpConnection.connect();
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream input = httpConnection.getInputStream();

                // Read server's response.
                response = new StringBuffer();
                Reader reader = new InputStreamReader(input, "UTF-8");
                reader = new BufferedReader(reader);
                char[] buffer = new char[1024];
                for (int n = 0; n >= 0;) {
                    n = reader.read(buffer, 0, buffer.length);
                    if (n > 0) {
                        response.append(buffer, 0, n);
                    }
                }

                input.close();
                httpConnection.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response != null) {
            return response.toString();
        } else {
            return "";
        }
    }

    public static String postURL(String targetURL, String urlParameters) {
        return postURL(targetURL, urlParameters, "text/xml");
    }

    public static String postURL(String targetURL, String urlParameters, String contentType) {
        URL url;
        HttpURLConnection connection = null;
        int responseCode = 0;
        try {
            //Create connection
            url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", contentType);

            connection.setRequestProperty("Content-Length", ""
                    + Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(
            connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            //Get Response	
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            return null;

        } finally {

            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public static String checksum(String s) {
        String checksum = "";
        try {
            checksum = org.apache.commons.codec.digest.DigestUtils.shaHex(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return checksum;
    }

    public static Document parseXml(String xml)
            throws ParserConfigurationException, IOException, org.xml.sax.SAXException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory
                .newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.parse(new InputSource(new StringReader(xml)));
        return doc;
    }

    public static org.jdom2.Document parseXmlJdom(String xml) throws JDOMException, IOException {
        SAXBuilder sb = new SAXBuilder();
        org.jdom2.Document doc = sb.build(new StringReader(xml));
        return doc;

    }

    public static String iniciarVC(String usuario, Conferencia conferencia) {
        String urlFinal;
        try {
            String xml;

            ConfiguracionRedBean crb = new ConfiguracionRedBean();
            String ip = crb.getConfiguracion().getIpBbb();
            String salt = crb.getConfiguracion().getSalt();
/*
            String url = "http://" + ip + "/bigbluebutton/api/getMeetingInfo?";
            String parametros = "meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&password=" + urlEncode(conferencia.getModeratorpw());
            url = url + parametros + "&checksum=" + checksum("getMeetingInfo" + parametros + salt);
            xml = getURL(url);

            Document doc = BbbCalls.parseXml(xml);
*/          System.out.println("Creando conferencia");
            cargarArchivos(conferencia);
            crearConferenciaArchivos(conferencia);

            String url  = "http://" + ip + "/bigbluebutton/api/join?";
            String parametros = "fullName=" + urlEncode(usuario) + "&meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&password=" + urlEncode(conferencia.getModeratorpw());
            urlFinal = url + parametros + "&checksum=" + checksum("join" + parametros + salt);

        } catch (Exception ex) {
            System.out.println("Error BBBCALLS:" + ex.toString());
            urlFinal = "error";
        }
        return urlFinal;
    }

    private static String crearConferencia(Conferencia conferencia) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();
        String logoutURL = crb.getConfiguracion().getLogouturl();

        String parametros = "name=" + urlEncode(conferencia.getNombre()) + "&meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&duration=" + urlEncode(conferencia.getDuracion())
                + "&attendeePW=" + urlEncode(conferencia.getAttendeepw()) + "&moderatorPW=" + urlEncode(conferencia.getModeratorpw())
                + "&record=" + urlEncode(String.valueOf(conferencia.getGrabacion())) + "&logoutURL=" + urlEncode(logoutURL);

        String url = "http://" + ip + "/bigbluebutton/api/create?";

        url = url + parametros + "&checksum=" + checksum("create" + parametros + salt);

        return getURL(url);
    }

    public static boolean EnEmision(String meetingID) {
        try {
            ConfiguracionRedBean crb = new ConfiguracionRedBean();
            String ip = crb.getConfiguracion().getIpBbb();
            String salt = crb.getConfiguracion().getSalt();

            String parametros = "meetingID=" + urlEncode(meetingID);

            String url = "http://" + ip + "/bigbluebutton/api/isMeetingRunning?";

            url = url + parametros + "&checksum=" + checksum("isMeetingRunning" + parametros + salt);

            String xml = getURL(url);
            System.out.println("en Emision:" + xml);
            Document doc = BbbCalls.parseXml(xml);
            if (doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS")) {
                return doc.getElementsByTagName("running").item(0).getTextContent().trim().equals("true");
            }

        } catch (Exception ex) {
            System.out.println("Error BBBCalls:" + ex.toString());
        }
        return false;
    }

    public static String urlInvitado(String usuario, Conferencia conferencia) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();

        String url = "http://" + ip + "/bigbluebutton/api/join?";
        String parametros = "fullName=" + urlEncode(usuario) + "&meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&password=" + urlEncode(conferencia.getAttendeepw());
        url = url + parametros + "&checksum=" + checksum("join" + parametros + salt);
        return url;
    }

    public static Boolean terminarVC(Conferencia conferencia) {
        try {
            ConfiguracionRedBean crb = new ConfiguracionRedBean();
            String ip = crb.getConfiguracion().getIpBbb();
            String salt = crb.getConfiguracion().getSalt();

            String url = "http://" + ip + "/bigbluebutton/api/end?";
            String parametros = "meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&password=" + urlEncode(conferencia.getModeratorpw());
            url = url + parametros + "&checksum=" + checksum("end" + parametros + salt);
            String xml = getURL(url);
            Document doc = BbbCalls.parseXml(xml);
            if (doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS")) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Error BBBCalls:" + ex.toString());

        }
        return false;
    }

    public static String getModeratorURL(String meetingID, String clave) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String url = crb.getConfiguracion().getLogouturl();
        return url + "web/moderador.xhtml?id=" + urlEncode(meetingID) + "&clv=" + urlEncode(clave);
    }

    public static String getChecksum(String id, String nombre) {
        return checksum(id + nombre);
    }

    public static String getAttendeeURL(String meetingID, String check) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String url = crb.getConfiguracion().getLogouturl();
        System.out.println("url pruebas" + url);
        return url + "web/index.xhtml?id=" + urlEncode(meetingID) + "&check=" + urlEncode(check);
    }

    public static String getMeeting(Conferencia conferencia) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();

        String url = "http://" + ip + "/bigbluebutton/api/getMeetingInfo?";
        String parametros = "meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&password=" + urlEncode(conferencia.getModeratorpw());
        url = url + parametros + "&checksum=" + checksum("getMeetingInfo" + parametros + salt);
        System.out.println("request:" + url);

        String xml = getURL(url);
        System.out.println("response:" + xml);
        return xml;
    }

    public static List<String> parseInfoConf(String xml) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("utils.mis_mensajes", FacesContext.getCurrentInstance().getViewRoot().getLocale());
        List<String> lista = new ArrayList<>();
        try {
            String info;
            Document doc = BbbCalls.parseXml(xml);
            if (doc.getElementsByTagName("returncode").item(0).getTextContent().trim().equals("SUCCESS")) {
                lista.add(resourceBundle.getString("usuarioHeaderEstado")+"-" + (doc.getElementsByTagName("running").item(0).getTextContent().trim().equals("true") ? resourceBundle.getString("ModerEnEmision") : resourceBundle.getString("ModerPendiente")));
                lista.add(resourceBundle.getString("ModerDurEsta")+"-" + doc.getElementsByTagName("duration").item(0).getTextContent().trim());
                lista.add(resourceBundle.getString("ModerGrabHab")+"-" + (doc.getElementsByTagName("recording").item(0).getTextContent().trim().equals("true") ? resourceBundle.getString("ModerSi") : resourceBundle.getString("ModerNo")));
                lista.add(resourceBundle.getString("ModerAsistentes")+"-" + doc.getElementsByTagName("participantCount").item(0).getTextContent().trim());
                lista.add(resourceBundle.getString("indexModeradores")+"-" + doc.getElementsByTagName("moderatorCount").item(0).getTextContent().trim());
            } else {
                lista.add("Info-"+resourceBundle.getString("ModerNoInfo"));
            }

        } catch (Exception ex) {
            System.out.println("Error - " + ex.toString());
            lista.add("Error-" + ex.toString());
        }
        return lista;
    }

    public static String getRecords(Conferencia conferencia) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();

        String url = "http://" + ip + "/bigbluebutton/api/getRecordings?";
        String parametros = "meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia()));
        url = url + parametros + "&checksum=" + checksum("getRecordings" + parametros + salt);
        System.out.println("request:" + url);

        String xml = getURL(url);
        System.out.println("response:" + xml);
        return xml;
    }
        

  public static String crearConferenciaArchivos(Conferencia conferencia) {
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        String ip = crb.getConfiguracion().getIpBbb();
        String salt = crb.getConfiguracion().getSalt();
        String logoutURL = crb.getConfiguracion().getLogouturl();
        List<Documento> listaDoc = new ArrayList<>();
        String xml="";//xml que contendra los archivos
        String parametros = "name=" + urlEncode(conferencia.getNombre()) + "&meetingID=" + urlEncode(String.valueOf(conferencia.getIdConferencia())) + "&duration=" + urlEncode(conferencia.getDuracion())
                + "&attendeePW=" + urlEncode(conferencia.getAttendeepw()) + "&moderatorPW=" + urlEncode(conferencia.getModeratorpw())
                + "&record=" + urlEncode(String.valueOf(conferencia.getGrabacion())) + "&logoutURL=" + urlEncode(logoutURL);

        String url = "http://" + ip + "/bigbluebutton/api/create?";
        DocumentoDao daoDocumento = new DocumentoDao();
        listaDoc=daoDocumento.obtenerTodosXConferencia(conferencia);
        
        url = url + parametros + "&checksum=" + checksum("create" + parametros + salt);    
        if(!listaDoc.isEmpty()){
            xml="<?xml version='1.0' encoding='UTF-8'?><modules><module name=\"presentation\">";
            for(Documento d: listaDoc){                
                //xml=xml+"<document name=\""+d.getNombre()+"\">"+Base64.encodeBase64String(d.getDocumento())+"\"</document>";
                //xml=xml+"<document url='"+"http://www.cyberbee.com/powerpoint/ppstep.pdf"+"'/>";
                xml=xml+"<document url='"+logoutURL+"pdf/"+d.getIdConferencia().getIdConferencia()+"-"+d.getIdDocumento()+d.getNombre()+"'/>";
            }
            xml=xml+"</module></modules>";
        }else{
            xml="<?xml version='1.0' encoding='UTF-8'?><modules><module name=\"presentation\">";
            xml=xml+"<document url='"+logoutURL+"pdf/default.pdf'/>";
            xml=xml+"</module></modules>";
        }
        return postURL(url, xml);
    }

    private static void cargarArchivos(Conferencia conferencia) {
        List<Documento> lista = new ArrayList<>();
        DocumentoDao daoDocumento = new DocumentoDao();
        lista=daoDocumento.obtenerTodosXConferencia(conferencia);
        ConfiguracionRedBean crb = new ConfiguracionRedBean();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try{
        String ruta=externalContext.getResource("pdf").getPath();
        ruta=ruta.substring(ruta.indexOf(":")+1);
        if(!lista.isEmpty()){
            for(Documento d: lista){
                    String destPath = Paths.get(ruta+d.getIdConferencia().getIdConferencia()+"-"+d.getIdDocumento()+d.getNombre()).toAbsolutePath().toString();
                    File destFile= new File(destPath);
                    ByteArrayInputStream input = new ByteArrayInputStream(d.getDocumento()); 
                    FileUtils.copyInputStreamToFile(input, destFile);
                
            }
        }else{
                    String destPath = Paths.get(ruta+"default.pdf").toAbsolutePath().toString();
                    File destFile= new File(destPath);
                    ByteArrayInputStream input = new ByteArrayInputStream(crb.getConfiguracion().getDocumento()); 
                    FileUtils.copyInputStreamToFile(input, destFile);
        }
        } catch (IOException ex) {
                    System.out.println("Error:"+ex.toString());
                }
        
    }

    public static void eliminarArchivos(Conferencia conferencia) {
        List<Documento> lista = new ArrayList<>();
        DocumentoDao daoDocumento = new DocumentoDao();
        lista=daoDocumento.obtenerTodosXConferencia(conferencia);
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try{
        String ruta=externalContext.getResource("pdf").getPath();
        ruta=ruta.substring(ruta.indexOf(":")+1);
        System.out.println("r"+ruta);
        if(!lista.isEmpty()){
            for(Documento d: lista){
                FileUtils.deleteQuietly(new File(ruta+d.getIdConferencia().getIdConferencia()+"-"+d.getIdDocumento()+d.getNombre()));                    
            }
        }
        } catch (IOException ex) {
                    System.out.println("Error:"+ex.toString());
                }
    }
    

}
