package com.woodtailer.tailer.mailsender;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Properties;
import javax.mail.internet.MimeMessage;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


public class MailService {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);

  private JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

  @Value("${email}")
  private String username;
  @Value("${password}")
  private String password;

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

      StringWriter writer = new StringWriter();
      InputStream inputStream = new ClassPathResource("mailtemplet.html").getInputStream();
      IOUtils.copy(inputStream, writer, "utf-8");

      helper.setText(writer.toString().replace("logmessage", emailMessage), true);
      helper.addTo(emailaddress);
    } catch (Exception ex) {
      ex.getStackTrace();
    }
    mailSender.send(message);
  }

  public void sendMailToSubscribers(String message, List<String> addresses) {

    if (addresses.size() > 0) {
      for (String address : addresses) {
        sendMail(message, address);
      }
    }
  }


}
