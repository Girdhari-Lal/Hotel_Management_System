package org.example.Service;

import org.example.Constant.Gender;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerInputServices {
    public String extractIDCardNumber(Scanner sc){
        Matcher matcher;
        do{
            System.out.print("Please enter your ID card number in the format XXXXX-XXXXXXX-X: ");
            String id = sc.next();
            Pattern pattern = Pattern.compile("\\d{5}-\\d{7}-\\d");
            matcher = pattern.matcher(id);
            if(!matcher.find()){
                System.out.println("Please enter again valid id card number");
            }else {
                break;
            }
        }while (true);
        return matcher.group();
    }
    public String extractFullName(Scanner sc){
        Matcher matcher;
        do {
            System.out.print("Enter your Full-name: ");
            String name = sc.next();
            Pattern pattern = Pattern.compile("^[A-Za-z]{3,20}-[A-Za-z]{3,20}$");
            matcher = pattern.matcher(name);
            if(!matcher.find()){
                System.out.println("Please enter again valid Name");
            }else {
                break;
            }
        }while (true);
        return matcher.group();
    }
    public Gender getGender(Scanner sc){
        Gender gender;
        do {
            System.out.print("""
                    Male
                    Female
                    Transgender
                    """);
            System.out.print("Enter gender name: ");
            String input = sc.next();
            try {
                gender = Gender.valueOf(input.toUpperCase());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Enter again valid gender");
            }
        }while(true);
        return gender;
    }
}
