/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author garo1
 */
public class Schedule implements ServletContextListener{

    private  Scheduler scheduler;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            JobDetail job = JobBuilder.newJob(Record.class)
                    .withIdentity("jobObtenerGrabaciones", "group1").build();
            
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("triggerObtenerGrabaciones", "group1")
                    .withSchedule(
                            SimpleScheduleBuilder.simpleSchedule()
                                    .withIntervalInMinutes(5).repeatForever())
                    .build();
            
            
            scheduler = ((StdSchedulerFactory) sce.getServletContext().getAttribute(QuartzInitializerListener.QUARTZ_FACTORY_KEY)).getScheduler();
            scheduler.scheduleJob(job, trigger);
            System.out.println("Job initialized");
        } catch (Exception ex) {
            System.out.println("ErrorJob:"+ex.toString());
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
    
   
    
    
}
