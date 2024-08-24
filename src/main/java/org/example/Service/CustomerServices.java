package org.example.Service;

import org.example.Constant.Gender;
import org.example.Entity.CustomerRecords;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.Scanner;

public class CustomerServices {
    CustomerInputServices customerInputServices = new CustomerInputServices();
    public CustomerRecords getCustomerDetail(Session session, Scanner sc, String idCard){
        if(idCard == null) {
            idCard = customerInputServices.extractIDCardNumber(sc);
        }
        CustomerRecords customerRecords = checkExistingCustomer(session, idCard);
        if(customerRecords == null) {
            customerRecords = createNewCustomer(session, sc, idCard);
        }
        return customerRecords;
    }
    public CustomerRecords checkExistingCustomer(Session session, String idCard){
        Query query = session.createQuery("FROM CustomerRecords WHERE idCard = :id");
        query.setParameter("id", idCard);
        CustomerRecords customerRecords = (CustomerRecords) query.uniqueResult();
        return customerRecords;
    }
    public CustomerRecords createNewCustomer(Session session, Scanner sc, String idCard) {
        Transaction tx = session.beginTransaction();
        CustomerRecords customerRecords = new CustomerRecords();
        String fullName = customerInputServices.extractFullName(sc);
        Gender gender = customerInputServices.getGender(sc);
        customerRecords.setFullName(fullName);
        customerRecords.setGender(gender);
        customerRecords.setIdCard(idCard);
        session.save(customerRecords);
        tx.commit();
        return customerRecords;
    }
}
/*

*/