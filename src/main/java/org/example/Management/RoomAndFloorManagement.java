package org.example.Management;

import org.hibernate.Session;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RoomAndFloorManagement {
    public void manageRoomAndFloor(Scanner sc, Session session){
        RoomsAndFloors roomsAndFloors = new RoomsAndFloors();
        while (true) {
            System.out.print("""
                    1. Add new floor & rooms
                    2. Add new rooms to existing floor
                    3. Update prices of rooms
                    4. back
                    """);
            System.out.print("Enter Number: ");
            try {
                int input = sc.nextInt();
                if(input==1){
                    roomsAndFloors.checkExistingPriceOfRoom(session, sc);
                }else if(input==2){
                    roomsAndFloors.updateExistingFloor(session, sc);
                }else if(input==3){
                    roomsAndFloors.updatePriceList(session, sc);
                }else {
                    break;
                }
            }catch(InputMismatchException e){
                break;
            }
        }
    }
}
