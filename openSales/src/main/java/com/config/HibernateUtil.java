package com.config;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
   private static final SessionFactory sessionFactory;
   private static final ServiceRegistry serviceRegistry;
   static {
      try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();        
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
             
      } catch (Throwable ex) {
         // Make sure you log the exception, as it might be swallowed
         System.err.println("Initial SessionFactory creation failed." + ex);
         throw new ExceptionInInitializerError(ex);
      }
   }
 
   private static final ThreadLocal<Session> session = new ThreadLocal<Session>();
 
   public static Session getCurrentSession() throws HibernateException {
      Session s = session.get();
      // Open a new Session, if this thread has none yet
      if (s == null) {
         s = sessionFactory.openSession();
         // Store it in the ThreadLocal variable
         session.set(s);
      }
      return s;
   }
 
   public static void closeSession() throws HibernateException {
      Session s = (Session) session.get();
      if (s != null)
         s.close();
      session.set(null);
   }
}