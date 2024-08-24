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
@Table(name = "booking_details")
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @ManyToOne
    @JoinColumn(name = "reservation_id", referencedColumnName = "id")
    private ReservationDetails reservationDetails;
    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "room_id")
    private RoomDetail roomDetail;

    public int getId() {
        return id;
    }
    public RoomDetail getRoomDetail() {
        return roomDetail;
    }
    public ReservationDetails getReservationDetails() {
        return reservationDetails;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setReservationDetails(ReservationDetails reservationDetails) {
        this.reservationDetails = reservationDetails;
    }
    public void setRoomDetail(RoomDetail roomDetail) {
        this.roomDetail = roomDetail;
    }
}
