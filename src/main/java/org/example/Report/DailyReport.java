package org.example.Report;

import org.hibernate.Session;
import org.hibernate.query.Query;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class DailyReport {
    public void todayCheckIn(Session session){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate todayDate = LocalDate.now();
        String date = todayDate.format(myFormatObj);
        Query query = session.createSQLQuery("SELECT c.full_name, c.gender, c.id_card, b.room_id FROM customer_records c INNER JOIN reservation_details r on r.customer_id = c.id INNER JOIN booking_details b ON r.id = b.reservation_id WHERE r.check_in like '%"+date+"%'");
        List<Object[]> list = query.list();
        System.out.println("Full Name : Gender : Id Card : Room id");
        for(Object[] row : list){
            System.out.println(row[0] +" : "+ row[1] +" : "+ row[2] +" : "+ row[3]);
        }
    }
    public void todayCheckOut(Session session){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate todayDate = LocalDate.now();
        String date = todayDate.format(myFormatObj);
        Query query = session.createSQLQuery("SELECT c.full_name, c.gender, c.id_card, b.room_id FROM customer_records c INNER JOIN reservation_details r on r.customer_id = c.id INNER JOIN booking_details b ON r.id = b.reservation_id WHERE r.check_out like '%"+date+"%'");
        List<Object[]> list = query.list();
        System.out.println("Full Name : Gender : Id Card : Room id");
        for(Object[] row : list){
            System.out.println(row[0] +" : "+ row[1] +" : "+ row[2] +" : "+ row[3]);
        }
    }
    public void reservedRooms(Session session){
        Query query = session.createSQLQuery("Select b.room_id from booking_details b inner join reservation_details r on b.reservation_id = r.id WHERE NOW() < STR_TO_DATE(r.check_out, '%d-%m-%Y %H:%i:%s')");
        List<Integer> list = query.list();
        ReportHelper reportHelper = new ReportHelper();
        System.out.println("Reserved room & type are");
        System.out.println("Room ID : Category");
        for(int roomId : list){
            int categoryId = reportHelper.getRoomCategoryId(session, roomId);
            String roomCategory = reportHelper.getRoomCategory(session, categoryId);
            System.out.println(roomId +" : "+ roomCategory);
        }
    }
    public void getEmptyRoomAndType(Session session){
        Query query = session.createSQLQuery("Select b.room_id from booking_details b inner join reservation_details r on b.reservation_id = r.id WHERE NOW() > STR_TO_DATE(r.check_out, '%d-%m-%Y %H:%i:%s')");
        List<Integer> list = query.list();
        ReportHelper reportHelper = new ReportHelper();
        System.out.println("Empty rooms & types are");
        System.out.println("Room ID : Category");
        for(int roomId : list){
            int categoryId = reportHelper.getRoomCategoryId(session, roomId);
            String roomCategory = reportHelper.getRoomCategory(session, categoryId);
            System.out.println(roomId +" : "+ roomCategory);
        }
    }
}
