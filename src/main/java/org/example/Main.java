package org.example;

import org.example.FactoryProvider.HibernateUtil;
import org.hibernate.Session;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Session session = HibernateUtil.getFactory().openSession();
        HotelManagementSystem hotelManagementSystem = new HotelManagementSystem();
        DefaultSettingsManager defaultSettingsManager = new DefaultSettingsManager();
        defaultSettingsManager.checkExistingDetails(session, sc);
        hotelManagementSystem.runHotelManagementSystem(session, sc);
        sc.close();
        HibernateUtil.closeFactoryAndSession(session);
    }
}