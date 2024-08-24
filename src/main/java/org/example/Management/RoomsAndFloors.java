package org.example.Management;

import org.example.Constant.RoomType;
import org.example.Entity.RoomCategory;
import org.example.Entity.RoomDetail;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import javax.persistence.PersistenceException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RoomsAndFloors {
    public void addRoomsPriceAndType(Session session, Scanner sc){
        Transaction tx = session.beginTransaction();
        RoomType[] roomTypes ={RoomType.STANDARD,RoomType.MODERATE,RoomType.SUPERIOR,RoomType.JUNIOR_SUITE,RoomType.SUITE};
        boolean isError;
        for (RoomType roomType : roomTypes) {
            RoomCategory roomCategory = new RoomCategory();
            do {
                try {
                    System.out.print("Enter " + roomType + " room price: ");
                    float input = sc.nextFloat();
                    if(input>0) {
                        roomCategory.setRoomType(roomType);
                        roomCategory.setPrice(input);
                        session.save(roomCategory);
                        isError = false;
                    }else {
                        isError = true;
                        System.out.println("Enter again valid positive value");
                    }
                } catch (InputMismatchException e) {
                    isError = true;
                    System.out.println("Enter again valid price value of " +roomType);
                    sc.next();
                }
            }while (isError);
        }
        tx.commit();
    }
    public void addRoomsInFloor(Scanner sc, Session session, int floor){
        Transaction tx = session.beginTransaction();
        RoomType[] roomTypes ={RoomType.STANDARD,RoomType.MODERATE,RoomType.SUPERIOR,RoomType.JUNIOR_SUITE,RoomType.SUITE};
        boolean isError = false;
        for(RoomType roomType : roomTypes) {
            do {
                try {
                    System.out.print("Please add number of " + roomType + " rooms: ");
                    int input = sc.nextInt();
                    if(input>=0) {
                        RoomCategory roomCategory = getRoom(roomType, session);
                        for (int j = -1; j < input-1; j++) {
                            RoomDetail roomDetail = new RoomDetail();
                            roomDetail.setFloor(floor);
                            roomDetail.setRoom(roomCategory);
                            session.save(roomDetail);
                            isError = false;
                        }
                    }else {
                        isError = true;
                        System.out.println("Enter again valid positive value");
                    }
                }catch(InputMismatchException e){
                    isError = true;
                    System.out.println("Enter again valid number");
                }
            }while (isError);
        }
        tx.commit();
    }
    public int getLastFloor(Session session){
        try {
            Query query = session.createSQLQuery("select max(floor) from room_detail");
            return (Integer) query.uniqueResult();
        }catch (PersistenceException | NullPointerException e){
            return 0;
        }
    }
    public RoomCategory getRoom(RoomType roomType, Session session){
        Query query = session.createQuery("FROM RoomCategory WHERE roomType =: roomType");
        query.setParameter("roomType", roomType);
        return (RoomCategory) query.uniqueResult();
    }
    public void checkExistingPriceOfRoom(Session session,Scanner sc){
        Query query = session.createQuery("From RoomCategory");
        List<RoomDetail> list = query.list();
        if(list.isEmpty()){
            addRoomsPriceAndType(session,sc);
        }
        int floor = getLastFloor(session)+1;
        addRoomsInFloor(sc, session, floor);
    }
    public int[] availableFloorList(Session session){
        int floor = getLastFloor(session);
        if(floor==0){
            System.out.println("Can't update, no floor available in the hotel");
            return null;
        }else if (floor<0){
            System.out.println("Invalid floor input. No floor add in the hotel.");
            return null;
        }
        int[] floors = new int[floor];
        for (int i=1;i<=floor;i++){
            floors[i-1] = i;
        }
        return floors;
    }
    public void updateExistingFloor(Session session, Scanner sc) {
        int[] floors = availableFloorList(session);
        boolean isValid = false;
        do {
            System.out.print("Enter floor number (valid floor number are: " + Arrays.toString(floors) + "): ");
            int floor = sc.nextInt();
            for(int i : floors) {
                if (i == floor) {
                    isValid = true;
                    break;
                }
            }
            if (isValid) {
                addRoomsInFloor(sc, session, floor);
            }else {
                System.out.println("Invalid floor input. Please provide a available floor number.");
            }
        }while (!isValid);
    }
    public void updatePriceList(Session session, Scanner sc){
        int input = 0;
        float price = 0;
        boolean isValid = false;
        Transaction tx = session.beginTransaction();
        Query query = session.createQuery("From RoomCategory");
        List<RoomCategory> list = query.list();
        int numList = 0;
        for(RoomCategory roomCategory : list){
            System.out.println(++numList +". "+ roomCategory.getRoomType() +" "+ roomCategory.getPrice());
        }
        do {
            try {
                System.out.print("Enter number to select type: ");
                input = sc.nextInt();
                if(input>list.size() || input<=0) {
                    System.out.println("Enter a valid number for the account type.");
                    continue;
                }
                System.out.print("Enter new price: ");
                price = sc.nextFloat();
                if(price<=0){
                    System.out.println("Enter a valid price.");
                    continue;
                }
                isValid = true;
            }catch (InputMismatchException e){
                System.out.println("Invalid input! Please enter again.");
                sc.next();
            }
        }while (!isValid);
        for(RoomCategory roomCategory : list){
            if(roomCategory.getId()==input){
                roomCategory.setPrice(price);
                session.save(roomCategory);
                System.out.println("Set new price of " +roomCategory.getRoomType());
            }
        }
        tx.commit();
    }
}
