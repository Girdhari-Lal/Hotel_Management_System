package org.example;

import org.example.Management.RoomAndFloorManagement;
import org.example.Report.DailyReport;
import org.example.Service.ReservationServices;
import org.hibernate.Session;
import java.util.InputMismatchException;
import java.util.Scanner;

public class HotelManagementSystem {
    public void runHotelManagementSystem(Session session, Scanner sc){
        DailyReport dailyReport = new DailyReport();
        while (true){
            System.out.print("""
                    Welcome to the Hotel Management System
                    1. Floor & room setting
                    2. Reservation
                    3. Customers check in today list
                    4. Customers check out today
                    5. Reserved rooms and their types
                    6. Empty rooms and their types
                    """);
            System.out.print("Enter number: ");
            try {
                int num = sc.nextInt();
                if(num==1){
                    RoomAndFloorManagement roomAndFloorManagement = new RoomAndFloorManagement();
                    roomAndFloorManagement.manageRoomAndFloor(sc, session);
                } else if(num==2) {
                    ReservationServices reservationServices = new ReservationServices();
                    reservationServices.processRoomReservation(session, sc, null);
                } else if(num==3) {
                    dailyReport.todayCheckIn(session);
                }else if(num==4){
                    dailyReport.todayCheckOut(session);
                }else if(num==5){
                    dailyReport.reservedRooms(session);
                }else if(num==6){
                    dailyReport.getEmptyRoomAndType(session);
                }else {
                    break;
                }
            }catch(InputMismatchException e){
                break;
            }
        }
    }
}
