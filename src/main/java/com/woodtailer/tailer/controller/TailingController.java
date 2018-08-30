package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.client.socket.SocketController;
import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.mailsender.MailService;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


@Controller
public class TailingController implements TailerServiceListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingController.class);

  private MailService mailService;
  private EmailSubscribers emailSubscribers;
  private TailingService tailingService;
  private SocketController socketController;
  private HeartBeatChecker heartBeatChecker;


  @Value("${logfile.url}")
  private String path;

  public TailingController(MailService mailService, EmailSubscribers emailSubscribers,
      TailingService tailingService, SocketController socketController,
      HeartBeatChecker heartBeatChecker) {
    this.mailService = mailService;
    this.emailSubscribers = emailSubscribers;
    this.tailingService = tailingService;
    this.socketController = socketController;
    this.heartBeatChecker = heartBeatChecker;
    socketController.start();
  }

  public void startPingService() {

    new Thread(() -> {
      while (true) {
        System.out.println(heartBeatChecker.getPuls());
        update(String.valueOf(heartBeatChecker.getPuls()));
        try {
          Thread.sleep(30000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }

    }).start();
  }

  public void startTailingService() {
    LOGGER.info("STARTING TAILING SERVICE...");
    tailingService.setTailerServiceListener(this);
    tailingService.run(path);
  }

  @Override
  public void update(String s) {
    LOGGER.info("NEW LOG FOUND - " + s);
    socketController.sendLog(s);
    LOGGER.info("WAITING...");
  }
}
