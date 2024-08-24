package org.example.Service;

import org.example.Constant.RoomType;
import org.example.Entity.Billing;
import org.example.Entity.BookingDetails;
import org.example.Entity.CustomerRecords;
import org.example.Entity.ReservationDetails;
import org.example.Entity.RoomCategory;
import org.example.Entity.RoomDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReservationServices {
    ReservationInputServices reservationInputServices = new ReservationInputServices();
    public void processRoomReservation(Session session, Scanner sc, String idCard){
        CustomerServices customerServices = new CustomerServices();
        CustomerRecords customerRecords = customerServices.getCustomerDetail(session, sc, idCard);
        RoomType roomType = reservationInputServices.getRoomType(sc);
        int rooms = reservationInputServices.inputNumberOfRooms(sc);
        int days = reservationInputServices.inputDaysToBook(sc);
        String checkIn = reservationInputServices.checkIn(sc);
        String checkOut = reservationInputServices.checkOut(checkIn, days);
        RoomCategory roomCategory = getRoomCategory(roomType, session);
        List<RoomDetail> availableRooms = checkAvailability(roomCategory, session, checkIn, checkOut, customerRecords, sc, rooms);
        if (availableRooms.isEmpty()) {
            System.out.println(roomCategory.getRoomType() +" Room is not available");
        }else {
            float totalAmount = days*roomCategory.getPrice()*rooms;
            float paidAmount = reservationInputServices.inputPaidAmount(totalAmount, sc);
            setReservationDetails(availableRooms, sc, session, checkIn, checkOut, customerRecords, rooms, paidAmount, totalAmount);
        }
    }
    public List<RoomDetail> findRooms(RoomCategory roomCategory, Session session){
        Query query = session.createSQLQuery("SELECT * FROM room_detail WHERE category_id = :categoryId")
                .addEntity(RoomDetail.class)
                .setParameter("categoryId", roomCategory.getId());
        List<RoomDetail> availableRooms = query.list();
        return availableRooms;
    }
    public void setReservationDetails(List<RoomDetail> arrayRoomDetail,Scanner sc ,Session session, String checkIn, String checkOut, CustomerRecords customerRecords, int rooms, float totalAmount,  float paidAmount){
        if(arrayRoomDetail.size() == rooms){
            Transaction tx = session.beginTransaction();
            ReservationDetails reservationDetails = new ReservationDetails();
            Billing billing = new Billing();
            reservationDetails.setCheckIn(checkIn);
            reservationDetails.setCheckOut(checkOut);
            reservationDetails.setCustomerRecords(customerRecords);
            billing.setPaidAmount(paidAmount);
            billing.setTotalAmount(totalAmount);
            billing.setReservationDetails(reservationDetails);
            session.save(reservationDetails);
            session.save(billing);
            for(RoomDetail roomDetail: arrayRoomDetail){
                BookingDetails bookingDetails = new BookingDetails();
                bookingDetails.setReservationDetails(reservationDetails);
                bookingDetails.setRoomDetail(roomDetail);
                session.save(bookingDetails);
            }
            tx.commit();
            System.out.println("Your hava Room booked "+rooms+" successfully");
            for(RoomDetail roomDetail: arrayRoomDetail){
                System.out.println("Your room id is: " +roomDetail.getId());
            }
        }
        askToBookAnotherRoom(session, sc, customerRecords);
    }
    public List<RoomDetail> checkAvailability(RoomCategory roomCategory, Session session, String checkIn, String checkOut, CustomerRecords customerRecords, Scanner sc, int rooms){
        List<RoomDetail> availableRooms = findRooms(roomCategory ,session);
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime checkInTime = LocalDateTime.parse(checkIn, myFormatObj);
        LocalDateTime checkOutTime = LocalDateTime.parse(checkOut, myFormatObj);
        List<RoomDetail> arrayRoomDetail = new ArrayList<>();
        for(RoomDetail roomDetail : availableRooms){
            if(isRoomAvailable(session, checkInTime, checkOutTime, roomDetail, myFormatObj) && arrayRoomDetail.size() < rooms){
                arrayRoomDetail.add(roomDetail);
            }
        }
        return arrayRoomDetail;
    }
    public boolean isRoomAvailable(Session session, LocalDateTime checkInTime, LocalDateTime checkOutTime, RoomDetail roomDetail, DateTimeFormatter myFormatObj){
        Query query = session.createSQLQuery("Select * from booking_details b inner join reservation_details r on b.reservation_id = r.id  where room_id = '"+roomDetail.getId()+"'")
                .addEntity(ReservationDetails.class);
        List<ReservationDetails> reservationDetailsList = query.list();
        for(ReservationDetails reservationDetails : reservationDetailsList) {
            LocalDateTime tableCheckInTime = LocalDateTime.parse(reservationDetails.getCheckIn(), myFormatObj);
            LocalDateTime tableCheckOutTime = LocalDateTime.parse(reservationDetails.getCheckOut(), myFormatObj);
            if(!(checkOutTime.isBefore(tableCheckInTime) || checkInTime.isAfter(tableCheckOutTime))) {
                return  false;
            }
        }
        return true;
    }
    public void askToBookAnotherRoom(Session session, Scanner sc, CustomerRecords customerRecords){
        System.out.print("Do you want book another room (y/n): ");
        char input = sc.next().charAt(0);
        if(input=='y'){
            String idCard = customerRecords.getIdCard();
            processRoomReservation(session, sc, idCard);
        }
    }
    public RoomCategory getRoomCategory( RoomType roomType, Session session){
        Query query =session.createQuery("From RoomCategory where roomType =: type");
        query.setParameter("type",roomType);
        return (RoomCategory) query.uniqueResult();
    }
}
