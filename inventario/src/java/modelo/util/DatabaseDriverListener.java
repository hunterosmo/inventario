/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.util;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener para gestionar correctamente el ciclo de vida del driver JDBC
 * y evitar memory leaks en la aplicación
 * 
 * @author Hunter
 */
@WebListener
public class DatabaseDriverListener implements ServletContextListener {
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Inicializando aplicación inventario ===");
        
        // Registrar el driver MySQL explícitamente
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver MySQL registrado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al registrar driver MySQL: " + e.getMessage());
        }
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Cerrando aplicación inventario ===");
        
        // Cerrar SessionFactory de Hibernate primero
        try {
            if (HibernateUtil.getSessionFactory() != null) {
                HibernateUtil.getSessionFactory().close();
                System.out.println("SessionFactory cerrado correctamente");
            }
        } catch (Exception e) {
            System.err.println("Error al cerrar SessionFactory: " + e.getMessage());
        }
        
        // Desregistrar todos los drivers JDBC
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
                System.out.println("Driver desregistrado: " + driver.getClass().getName());
            } catch (SQLException e) {
                System.err.println("Error al desregistrar driver: " + e.getMessage());
            }
        }
        
        // Forzar garbage collection
        System.gc();
        System.out.println("=== Aplicación cerrada correctamente ===");
    }
}