package org.example.Entity;

import org.example.Constant.RoomType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "room_category")
public class RoomCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;
    @Column(name = "price", nullable = false)
    private float price;
    public int getId() {
        return id;
    }
    public RoomType getRoomType() {
        return roomType;
    }
    public float getPrice() {
        return price;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
