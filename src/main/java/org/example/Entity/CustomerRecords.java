package org.example.Entity;

import org.example.Constant.Gender;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_records")
public class CustomerRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;
    @Column(name = "id_card", nullable = false)
    private String idCard;
    public int getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }
    public Gender getGender() {
        return gender;
    }
    public String getIdCard() {
        return idCard;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
