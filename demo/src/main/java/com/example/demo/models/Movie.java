package com.example.demo.models;

import com.example.demo.util.MovieType;
import jdk.jfr.Enabled;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.time.LocalDate;

@Data
@Log4j2
@Entity
@Table(name="movies")
public class Movie {

    @Id
    @Column(name="movie_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String name, description, main_picture, picture1, picture2,
            picture3, picture4, picture5, youtube_url, seo_url,
            seo_title, seo_keywords, seo_descr;

    @Enumerated(EnumType.STRING)
    @Column(name="movie_type")
    MovieType type;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    LocalDate release_date;

}
