package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailSenderService {

    private JavaMailSender mailSender;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendMail() throws MessagingException {

        String to = "regicuinte@gmail.com";
        String from = "regicuinte@gmail.com";
        final String username = "regicuinte@gmail.com";
        final String password = "xkvejddiqzegysfm";

        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        PasswordAuthentication auth = new PasswordAuthentication(username, password);

        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return auth;
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Testing Subject");
            message.setText("Hello, this is sample for to check send "
                    + "email using JavaMailAPI ");

            Transport.send(message);
            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


        // --- https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mail.html

//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message);
//
//        helper.setFrom("regicuinte@gmail.com");
//        helper.setTo("regicuinte@gmail.com");
//        helper.setSubject("subject");
//        helper.setText("TESJL:TJS");
//
//        mailSender.send(message);


        //Результат: Caused by: java.util.ServiceConfigurationError: jakarta.mail.Provider: com.sun.mail.imap.IMAPProvider not a subtype

        // ---

//        Properties props = new Properties();
//
//        Session session = Session.getInstance(props);
//
//        MimeMessage message = new MimeMessage(session);
//
//        message.setFrom(new InternetAddress("regicuinte@gmail.com"));
//        message.setRecipient(Message.RecipientType.TO, new InternetAddress("regicuinte@gmail.com"));
//        message.setSubject("subject");
//        message.setText("TESLKTJLSEEJ");



}
