package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.mailsender.MailService;
import com.woodtailer.tailer.server.socket.SocketDistributor;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


@Controller
public class TailingController implements TailerServiceListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingController.class);

  private SocketDistributor socketDistributor;
  private MailService mailService;
  private EmailSubscribers emailSubscribers;

  @Value("${logfile.url}")
  private String path;

  public TailingController(MailService mailService, EmailSubscribers emailSubscribers,
      SocketDistributor socketDistributor) {
    this.mailService = mailService;
    this.emailSubscribers = emailSubscribers;
    this.socketDistributor = socketDistributor;
  }

  public void startTailingService() {
    LOGGER.info("STARTING TAILING SERVICE");

    TailingService tailingService = new TailingService();
    tailingService.setTailerServiceListener(this);
    tailingService.run(path);
  }

  @Override
  public void update(String s) {
    LOGGER.info("New log found: " + s);

    try {
      LOGGER.info("Sending to socketDistributor central");
      socketDistributor.SendLog(s);

      if (s.contains("WARN") || s.contains("ERROR")) {
        sendMail(new Exception(s));
      }

    } catch (Exception e) {
      LOGGER.error("sending to socketDistributor central failed: " + e);
    }
  }

  @Override
  public void sendMail(Exception e) {
    mailService
        .sendMailToSubscribers(e.getMessage(), emailSubscribers.getAddresses());
  }
}
