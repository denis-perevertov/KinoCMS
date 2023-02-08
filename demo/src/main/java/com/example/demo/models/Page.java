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
@Table(name="pages")
public class Page {

    @Id
    @Column(name="page_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name, description, main_picture, picture1, picture2,
            picture3, picture4, picture5, seo_url, seo_title,
            seo_keywords, seo_descr;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate date;

}
