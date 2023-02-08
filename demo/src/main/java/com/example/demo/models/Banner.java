package com.example.demo.models;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import jakarta.persistence.*;

@Data
@Log4j2
@Entity
@Table(name="banners")
public class Banner {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String website_background_image;
}
