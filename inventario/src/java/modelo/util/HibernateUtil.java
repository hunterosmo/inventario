/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object. Updated for Hibernate 4.3.1 and production use.
 *
 * @author Hunter
 */
public class HibernateUtil {
    
    private static volatile SessionFactory sessionFactory;
    private static final Object lock = new Object();
    
    static {
        buildSessionFactory();
    }
    
    private static void buildSessionFactory() {
        try {
            if (sessionFactory == null) {
                synchronized (lock) {
                    if (sessionFactory == null) {
                        System.out.println("Construyendo SessionFactory...");
                        
                        // Create the SessionFactory from standard (hibernate.cfg.xml) 
                        // config file using Configuration instead of AnnotationConfiguration
                        Configuration configuration = new Configuration();
                        configuration.configure("hibernate.cfg.xml");
                        
                        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                                .applySettings(configuration.getProperties())
                                .build();
                        
                        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                        
                        System.out.println("SessionFactory construido exitosamente");
                    }
                }
            }
        } catch (Throwable ex) {
            // Log the exception.
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    /**
     * Get the SessionFactory instance
     * @return SessionFactory
     */
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            buildSessionFactory();
        }
        return sessionFactory;
    }
    
    /**
     * Shutdown the SessionFactory
     */
    public static void shutdown() {
        if (sessionFactory != null && !sessionFactory.isClosed()) {
            try {
                sessionFactory.close();
                System.out.println("SessionFactory cerrado correctamente");
            } catch (Exception e) {
                System.err.println("Error al cerrar SessionFactory: " + e.getMessage());
            }
        }
    }
    
    /**
     * Check if SessionFactory is closed
     * @return true if closed, false if open
     */
    public static boolean isClosed() {
        return sessionFactory == null || sessionFactory.isClosed();
    }
}