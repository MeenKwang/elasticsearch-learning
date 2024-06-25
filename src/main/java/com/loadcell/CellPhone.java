package com.loadcell;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLInsert;

import java.time.LocalDate;

@Entity
@Table(name = "cell_phone")
//@SQLInsert(sql = "INSERT IGNORE INTO cell_phone(phone_number, date_modify) " +
//        "VALUES (?, ?)" )
public class CellPhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "date_modify")
    private LocalDate dateModify;

    public CellPhone() {
    }

    public CellPhone(Integer id, LocalDate dateModify) {
        this.id = id;
        this.dateModify = dateModify;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateModify() {
        return dateModify;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setDateModify(LocalDate dateModify) {
        this.dateModify = dateModify;
    }
}
