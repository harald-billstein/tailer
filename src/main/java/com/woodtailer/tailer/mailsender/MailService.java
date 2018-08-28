package com.woodtailer.tailer.mailsender;

import java.util.List;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);
  private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
  private String username = "harald.billstein@gmail.com";

  // TODO REMOVE!!!!!!
  private String password = "harrebarre";

  public MailService(){

  }


  private void sendMail(String emailMessage, String emailaddress) {

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", 465);
    properties.put("mail.smtp.ssl.enable", true);

    mailSender.setJavaMailProperties(properties);
    mailSender.setUsername(username);
    mailSender.setPassword(password);

    MimeMessage message = mailSender.createMimeMessage();

    try {
      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setFrom("WoodTailer@gmail.com");
      helper.setSubject("ERROR/WARN DETECTED");
      helper.setText(emailMessage, true); // true to activate multipart
      helper.addTo(emailaddress);
    } catch (Exception ex) {
      ex.getStackTrace();
    }
    mailSender.send(message);
  }

  public void sendMailToSubscribers(String message, List<String> addresses) {

    if (addresses.size() > 0) {
      for (int i = 0; i < addresses.size(); i++) {
        sendMail(message, addresses.get(i));
      }
      LOGGER.info("SUBSCRIBERS ALERTED");
    } else {
      LOGGER.info("NO SUBSCRIBERS TO ALERT");
    }
  }

  public class MailServiceBuilder{



  }
}
