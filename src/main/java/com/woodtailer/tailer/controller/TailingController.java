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
  private TailingService tailingService;

  @Value("${logfile.url}")
  private String path;

  public TailingController(MailService mailService, EmailSubscribers emailSubscribers,
      SocketDistributor socketDistributor, TailingService tailingService) {
    this.mailService = mailService;
    this.emailSubscribers = emailSubscribers;
    this.socketDistributor = socketDistributor;
    this.tailingService = tailingService;
  }

  public void startTailingService() {
    LOGGER.info("STARTING TAILING SERVICE...");

    tailingService.setTailerServiceListener(this);
    tailingService.run(path);
  }

  @Override
  public void update(String s) {
    LOGGER.info("NEW LOG FOUND");

    try {
      LOGGER.info("SENDING TO SOCKET...");
      socketDistributor.SendLog(s);

      if (s.contains("WARN") || s.contains("ERROR")) {
        sendMail(new Exception(s));
      }

    } catch (Exception e) {
      e.getStackTrace();
      LOGGER.error("SENDING TO SOCKET FAILED..." + e);
    }
    LOGGER.info("WAITING...");
  }

  @Override
  public void sendMail(Exception e) {
    LOGGER.info("WARNING/ERROR DETECTED SENDING MAIL...");
    mailService
        .sendMailToSubscribers(e.getMessage(), emailSubscribers.getAddresses());
  }
}
