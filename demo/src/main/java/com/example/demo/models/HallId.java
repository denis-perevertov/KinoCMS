package com.example.demo.models;

import java.io.Serializable;

public class HallId implements Serializable {
    private Long hall_id;
    private Long theater_id;

    public HallId(Long hall_id, Long theater_id) {
        this.hall_id = hall_id;
        this.theater_id = theater_id;
    }

    public Long getHall_id() {
        return hall_id;
    }

    public void setHall_id(Long hall_id) {
        this.hall_id = hall_id;
    }

    public Long getTheater_id() {
        return theater_id;
    }

    public void setTheater_id(Long theater_id) {
        this.theater_id = theater_id;
    }
}
