package com.surepayroll.Utils

import java.io.IOException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender{

    public static void main(String[]args) throws IOException {

        final String username = "skonugan";
        final String password = "SriVihas20151022";

        Properties props = new Properties();
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.ssl.enable", false);
        props.put("mail.smtp.host", "outlook.office365.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            session.setDebug(true)
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("srikanth.konuganti@surepayroll.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("srikanth.konuganti@surepayroll.com"));
            message.setSubject("Test");
            message.setText("HI");


            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}