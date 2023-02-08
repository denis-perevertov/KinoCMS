package com.example.demo.models;

import lombok.Data;

import jakarta.persistence.Entity;
import java.util.Map;

@Data
public class Email {

    String from;
    String to;
    String subject;
    String template;
    Map<String, Object> properties;
}
