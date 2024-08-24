package org.example.FactoryProvider;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory FACTORY;
    public static SessionFactory getFactory(){
        FACTORY = new Configuration().configure().buildSessionFactory();
        return FACTORY;
    }
    public static void closeFactoryAndSession(Session session){
        if(FACTORY.isOpen()){
            FACTORY.close();
            session.close();
        }
    }
    
}
