package org.example.Service;

import org.example.Constant.RoomType;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDateTime;
public class ReservationInputServices {
    public RoomType getRoomType(Scanner sc){
        RoomType roomType = null;
        do{
            System.out.print("""
                   1. STANDARD,
                   2. MODERATE,
                   3. SUPERIOR,
                   4. JUNIOR_SUITE,
                   5. SUITE
                    """);
            System.out.print("Enter Room Type here: ");
            try {
                int input = sc.nextInt();
                roomType = RoomType.roomType(input);
                break;
            }catch(InputMismatchException | IllegalArgumentException e){
                System.out.println("Enter again valid Room type number");
                sc.nextLine();
            }
        }while (true);
        return roomType;
    }
    public String checkIn(Scanner sc){
        String input, time;
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate todayDate = LocalDate.now();
        do{
            System.out.print("Please enter the check-in date (dd-MM-yyyy): ");
            input = sc.next();
            try {
                LocalDate inputDate = LocalDate.parse(input, myFormatObj);
                if (!inputDate.isBefore(todayDate)) {
                    break;
                } else {
                    System.out.println("The check-in date must be today or later. Enter again.");
                }
            }catch(DateTimeException e){
                System.out.println("Enter valid date of check in again example: 01-01-2024");
            }
        }while (true);
        time = input+" "+checkInHour(sc);
        return  time;
    }
    public String checkInHour(Scanner sc){
        int hour;
        String time;
        do {
            System.out.print("Please enter the check in hour in 24-hour format (0-23): ");
            hour = sc.nextInt();
            if (hour > 0 && hour <= 24){
                break;
            }else {
                System.out.println("Enter again valid hour in 24-hour format");
            }
        }while (true);
        if(hour<10){
            time = "0"+hour+":00:00";
        }else {
            time = hour+":00:00";
        }
        return time;
    }
    public String checkOut(String checkIn, int days){
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(checkIn, myFormatObj);
        LocalDateTime modifiedDateTime = dateTime.plusDays(days);
        return (String) modifiedDateTime.format(myFormatObj);
    }
    public int inputDaysToBook(Scanner sc){
        int days;
        do {
            try {
                System.out.print("How many days do you want to book the room for: ");
                days = sc.nextInt();
                if(days>0){
                    break;
                }else {
                    System.out.println("Enter a valid number of days (greater than 0).");
                }
            }catch (Exception e){
                System.out.println("Enter again valid days");
                sc.next();
            }
        }while (true);
        return days;
    }
    public int inputNumberOfRooms(Scanner sc){
        int rooms;
        do {
            try {
                System.out.print("How many rooms do you want to book: ");
                rooms = sc.nextInt();
                if(rooms>0){
                    break;
                }else {
                    System.out.println("Enter a valid number of rooms (greater than 0).");
                }
            }catch (Exception e){
                System.out.println("Enter again valid number of rooms");
                sc.next();
            }
        }while (true);
        return rooms;
    }
    public float inputPaidAmount(float totalAmount, Scanner sc){
        System.out.println("The total amount is: " + totalAmount);
        float paidAmount;
        do{
            System.out.print("Enter the paid amount: ");
            paidAmount = sc.nextFloat();
            if(paidAmount<totalAmount/10){
                System.out.println("The paid amount is insufficient. You need to pay at least: " +totalAmount/10);
            }else {
                break;
            }
        }while (true);
        return paidAmount;
    }
}
