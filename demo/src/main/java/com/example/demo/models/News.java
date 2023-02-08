package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@Log4j2
@Entity
@Table(name="news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="news_id", nullable = false, unique = true)
    Long news_id;
    String name;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate date;

    String description;
    String main_picture;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    String youtube_url;
    String seo_url, seo_title, seo_keywords, seo_descr;

    public News(String name, LocalDate date, String description, String main_picture,
                String picture1, String picture2, String picture3, String picture4,
                String picture5, String youtube_url, String seo_url, String seo_title,
                String seo_keywords, String seo_descr) {
        this.name = name;
        this.date = date;
        this.description = description;
        this.main_picture = main_picture;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
        this.picture4 = picture4;
        this.picture5 = picture5;
        this.youtube_url = youtube_url;
        this.seo_url = seo_url;
        this.seo_title = seo_title;
        this.seo_keywords = seo_keywords;
        this.seo_descr = seo_descr;
    }

    public News() {}
}
