/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.ConferenciaDao;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import model.Conferencia;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import org.jdom2.xpath.XPathExpression;
import org.jdom2.xpath.XPathFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author garo1
 */
public class Record implements Job{
    
    
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

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        ConferenciaDao daoConferencia=new ConferenciaDao();
        List<Conferencia> confList=daoConferencia.obtenerPendientesGrabacion();
        for(Conferencia c :confList){
            getRecordLink(c.getIdConferencia());
        }
        
        daoConferencia=new ConferenciaDao();
        confList=daoConferencia.obtenerEmision();
        for(Conferencia c :confList){
            if(!BbbCalls.EnEmision(String.valueOf(c.getIdConferencia()))){
                daoConferencia = new ConferenciaDao();
                daoConferencia.actualizar(c,3,c.getIdUsuarioXPrograma().getIdUsuarioXPrograma());
            };
        }
    }
}
