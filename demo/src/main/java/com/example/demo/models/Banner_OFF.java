package com.example.demo.models;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import jakarta.persistence.*;

@Data
@Log4j2
@Entity
@Table(name="offers_banners")
public class Banner_OFF {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    String file_name, url, title;

}
