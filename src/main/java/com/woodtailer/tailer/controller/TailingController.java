package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.mailsender.MailService;
import com.woodtailer.tailer.properties.LogProperties;
import com.woodtailer.tailer.server.socket.SocketDistributor;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;


@Controller
public class TailingController implements TailerServiceListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingController.class);

  private static TailingController tailingController = new TailingController();
  private final SocketDistributor socketDistributor = new SocketDistributor();
  // TODO BUILDER PATTERN
  private MailService mailService = new MailService();
  //private EmailSubscribers emailSubscribers;
  //private String path = "/Users/Harald/dev/log.log";

  private TailingController() {

  }

  public static TailingController getInstance() {
    return tailingController;
  }

  public void startTailingService() {
    LOGGER.info("STARTING TAILING SERVICE");
    String path = getUrl();

    TailingService tailingService = new TailingService();
    tailingService.setTailerServiceListener(this);
    tailingService.run(path);
  }

  private String getUrl() {
    return LogProperties.getInstance().getUrl();
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
        .sendMailToSubscribers(e.getMessage(), EmailSubscribers.getInstance().getAddresses());
  }
}
