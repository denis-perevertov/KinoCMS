package com.example.demo.models;


import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Log4j2
@Entity
@Table(name="halls")
public class Hall {

    @EmbeddedId
    HallId hallId;

    @ManyToOne
    @JoinColumn(name="theater_id", insertable = false, updatable = false)
    Movie_Theater theater;

    String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate creation_date;

    String description, hall_scheme, banner_picture, picture1, picture2,
    picture3, picture4, picture5, seo_url, seo_title, seo_keywords, seo_descr;


}

