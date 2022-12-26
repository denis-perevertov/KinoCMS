package com.example.demo.models;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Data
@Log4j2
@Entity
@Table(name="movie_theaters")
public class Movie_Theater {

    @Id
    @Column(name="theater_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long theater_id;

    String name;
    String description;
    String conditions;
    String logo;
    String banner_picture;
    String picture1;
    String picture2;
    String picture3;
    String picture4;
    String picture5;
    String seo_url, seo_title, seo_keywords, seo_descr;

    @OneToMany(mappedBy = "theater")
    List<Hall> hallList = new ArrayList<>();

}
