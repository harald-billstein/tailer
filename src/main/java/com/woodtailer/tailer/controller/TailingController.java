package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.client.socket.MyMessageHandler;
import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


@Controller
public class TailingController implements TailerServiceListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingController.class);

  private TailingService tailingService;
  private MyMessageHandler myMessageHandler;
  private HeartBeatChecker heartBeatChecker;


  @Value("${logfile.url}")
  private String path;

  public TailingController(
      TailingService tailingService, MyMessageHandler myMessageHandler,
      HeartBeatChecker heartBeatChecker) {
    this.tailingService = tailingService;
    this.heartBeatChecker = heartBeatChecker;
    this.myMessageHandler = myMessageHandler;
    myMessageHandler.connect();
  }

  public void startPingService() {

    new Thread(() -> {
      while (true) {
        System.out.println(heartBeatChecker.getPuls());
        update(String.valueOf(heartBeatChecker.getPuls()));
        try {
          Thread.sleep(10000);
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
    myMessageHandler.sendMessage(s);
    LOGGER.info("WAITING...");
  }
}
