package com.example.demo.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class HallId implements Serializable {

    @Column(name="hall_number")
    private Long hall_number;

    @Column(name="theater_id")
    private Long theater_id;

    public HallId() {
    }

    public HallId(Long hall_number, Long theater_id) {
        this.hall_number = hall_number;
        this.theater_id = theater_id;
    }

    public Long getHall_number() {
        return hall_number;
    }

    public void setHall_number(Long hall_number) {
        this.hall_number = hall_number;
    }

    public Long getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(Long theater_id) {
        this.theater_id = theater_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HallId hallId = (HallId) o;
        return Objects.equals(hall_number, hallId.hall_number) && Objects.equals(theater_id, hallId.theater_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hall_number, theater_id);
    }
}
