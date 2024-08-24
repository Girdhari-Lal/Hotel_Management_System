package org.example.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reservation_details")
public class ReservationDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "check_in", nullable = false)
    private String checkIn;
    @Column(name = "check_out", nullable = true)
    private String checkOut;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private CustomerRecords customerRecords;
    public int getId() {
        return id;
    }
    public String getCheckIn() {
        return checkIn;
    }
    public String getCheckOut() {
        return checkOut;
    }
    public CustomerRecords getCustomerRecords() {
        return customerRecords;
    }
    public void setCheckIn(String check_in) {
        this.checkIn = check_in;
    }
    public void setCheckOut(String check_out) {
        this.checkOut = check_out;
    }
    public void setCustomerRecords(CustomerRecords customerRecords) {
        this.customerRecords = customerRecords;
    }
}
