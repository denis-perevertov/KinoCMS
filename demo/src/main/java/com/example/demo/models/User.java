package com.example.demo.models;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import com.example.demo.util.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Log4j2
@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id", nullable = false, unique = true)
    Long user_id;

    LocalDateTime registration_date;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate birth_date;

    String email;
    String phone_number;
    String name;
    String surname;
    String alias;
    String city;

    @Enumerated(EnumType.STRING)
    @Column(name="gender")
    Gender gender;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public boolean isMale() {
        return ((gender != null) && (gender.toString().equalsIgnoreCase("MALE")));
    }

    public boolean isFemale() {
        return ((gender != null) && (gender.toString().equalsIgnoreCase("FEMALE")));
    }

}
