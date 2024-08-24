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
@Table(name = "billing")
public class Billing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private ReservationDetails reservationDetails;
    @Column(name = "total_amount", nullable = false)
    private float totalAmount;
    @Column(name = "paidAmount", nullable = false)
    private float paidAmount;

    public int getId() {
        return id;
    }
    public float getTotalAmount() {
        return totalAmount;
    }
    public float getPaidAmount() {
        return paidAmount;
    }
    public ReservationDetails getReservationDetails() {
        return reservationDetails;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    public void setPaidAmount(float paidAmount) {
        this.paidAmount = paidAmount;
    }
    public void setReservationDetails(ReservationDetails reservationDetails) {
        this.reservationDetails = reservationDetails;
    }
}
