package com.code;

import javax.mail.PasswordAuthentication;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {
	public static void sendMail(String recipient) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");
		String myEmail="neilchristianriego3@gmail.com";
		String password=System.getenv("PASSWORD");
		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmail, password);
			}
		});
		System.out.println("Preparing email to send. . .");
		Message msg = prepareMessage(session, myEmail, password, recipient);
		try {
			Transport.send(msg);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static Message prepareMessage(Session session, String myEmail, String password, String recipient) {
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(myEmail));
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
			msg.setSubject("Reset your chainsLog Password");
			msg.setText("http://localhost:8080/reset/" + recipient);
			return msg;
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
