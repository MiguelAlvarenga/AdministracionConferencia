/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import utils.BbbCalls;
import dao.ConferenciaDao;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Conferencia;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
//import org.w3c.dom.Document;

/**
 *
 * @author garo1
 */
public class CallToBBB {
    
    
    public static void main(String[] args){
        /*ConferenciaDao daoConferencia=new ConferenciaDao();
        Conferencia conferencia=daoConferencia.getConferenciaXId(30);
        //Conferencia conferencia=daoConferencia.getConferenciaXId(26);
        BbbCalls.eliminarArchivos(conferencia);*/
        
        ConferenciaDao daoConferencia=new ConferenciaDao();
        List<Conferencia> confList=daoConferencia.obtenerEmision();
        for(Conferencia c :confList){

            if(!BbbCalls.EnEmision(String.valueOf(c.getIdConferencia()))){
                daoConferencia = new ConferenciaDao();
                daoConferencia.actualizar(c,3,c.getIdUsuarioXPrograma().getIdUsuarioXPrograma());
            };
        }
        return;
    }   
    
    public static void getRecordLink(int idConferencia){
        String url="";
        try {
            ConferenciaDao daoConferencia = new ConferenciaDao();
            Conferencia conferencia = daoConferencia.getConferenciaXId(idConferencia);
            String xml=BbbCalls.getRecords(conferencia);
            System.out.println("xml:"+xml);
            Document doc =BbbCalls.parseXmlJdom(xml);
            XPathFactory xFactory=XPathFactory.instance();
            
            XPathExpression<Element> expr=xFactory.compile("//url",Filters.element());
            List<Element> urls=expr.evaluate(doc);
            
            for (Element e: urls) {
                url=e.getValue();
            }
            System.out.println("url:"+url);
            
            Calendar cal = Calendar.getInstance();
            Date curdate=new Date();
            Date fechaConf;
            cal.setTime(curdate);
            curdate=cal.getTime();
            cal.setTime((conferencia.getFechaPonencia()==null? conferencia.getFechaCreacion():conferencia.getFechaPonencia()));
            cal.add(Calendar.DATE,10);
            fechaConf=cal.getTime();            
            System.out.println(idConferencia+"- fec:"+fechaConf+"- curr"+curdate);
            if(!url.isEmpty()){
                conferencia.setRecordingurl(url);
                daoConferencia = new ConferenciaDao();
                
                daoConferencia.actualizar(conferencia,4,conferencia.getIdUsuarioXPrograma().getIdUsuarioXPrograma());
            }else if(curdate.after(fechaConf) && conferencia.getRecordingurl()==null){
                System.out.println("actualizando");
                conferencia.setGrabacion(false);//le doy 10 dias para buscar si ya se publico la conferencia
                daoConferencia = new ConferenciaDao();
                daoConferencia.actualizar(conferencia, conferencia.getIdEstadoConferencia().getIdEstadoConferencia(), conferencia.getIdUsuarioXPrograma().getIdUsuarioXPrograma());
            }
            
        } catch (Exception ex) {    
            System.out.println("error:"+ex.toString());
        }
    }
    
    
    public static void crearConferencia(){
        String ip="192.168.100.14";
        String salt="fe6cb95a4e271a91ca7c40726252dc77";
        
        String name="Prueba";
        String meetingId="3";
        String moderatorPWD=String.valueOf(System.currentTimeMillis());
        String parametros="name="+urlEncode(name)+"&meetingID="+urlEncode(meetingId);
        
        String url="http://"+ip+"/bigbluebutton/api/create?";
        
        url=url+parametros+"&checksum="+checksum("create"+parametros+salt);
        
        System.out.println("respuesta:"+getURL(url));
        
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
				if (n > 0)
				response.append(buffer, 0, n);
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
    
public static String checksum(String s) {
	String checksum = "";
	try {
		checksum = org.apache.commons.codec.digest.DigestUtils.shaHex(s);
	} catch (Exception e) {
		e.printStackTrace();
	}
	return checksum;
}    
    
}
