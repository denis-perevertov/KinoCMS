package com.example.demo.models;


import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@Log4j2
@Entity
@Table(name="offers")
public class Offer {

    @Id
    @Column(name="offer_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate date;

    String description, main_picture, picture1, picture2, picture3,
            picture4, picture5, youtube_url, seo_url, seo_title,
            seo_keywords, seo_descr ;

}
