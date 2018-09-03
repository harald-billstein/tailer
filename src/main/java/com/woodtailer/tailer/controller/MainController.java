package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.client.socket.MyMessageHandler;
import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import java.util.concurrent.ExecutionException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketSession;


@Controller
public class MainController implements TailerServiceListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
  private static final int THREAD_SLEEP_DEFAULT_TIME = 10000;

  @Getter
  private boolean isSocketOnline;
  @Getter
  private boolean isPingServiceRunning;
  @Getter
  private boolean isTailsingServerRunning;

  private WebSocketSession session;

  private TailingService tailingService;
  private MyMessageHandler myMessageHandler;
  private HeartBeatChecker heartBeatChecker;

  @Value("${logfile.url}")
  private String path;

  public MainController(
      TailingService tailingService, MyMessageHandler myMessageHandler,
      HeartBeatChecker heartBeatChecker) {
    this.tailingService = tailingService;
    this.heartBeatChecker = heartBeatChecker;
    this.myMessageHandler = myMessageHandler;
    startSocket();
  }

  private void startSocket() {

    while (true) {

      try {
        session = myMessageHandler.connect();
        LOGGER.info("CONNECTION SUCCESS");
        break;
      } catch (ExecutionException | InterruptedException e1) {
        LOGGER.error(
            "CONNECTION FAILED - RECONNECT IN " + (THREAD_SLEEP_DEFAULT_TIME / 1000) + " SECONDS");
        threadSleep();
      }
    }
    isSocketOnline = session.isOpen();
  }

  public void startTailingService() {

    if (session.isOpen()) {
      tailingService.setTailerServiceListener(this);

      tailingService.start();
      LOGGER.info("TAILING SERVICE STARTED");
    } else {
      LOGGER.warn("TALING SERVICE FAILED TO START.....RESTARTING");
      startSocket();
      startTailingService();
    }
  }

  public void startPingService() {
    isPingServiceRunning = true;

    new Thread(() -> {
      LOGGER.info("PING SERVICE STARTED");
      while (session.isOpen() && isPingServiceRunning) {

        if (heartBeatChecker.getPuls()) {
          update("PULS");
        }

        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      LOGGER.info("HEARTBEAT STOPPED");
      //startSocket();
      //startPingService();
    }

    ).start();
  }

  public void stopTalingService() {
    LOGGER.warn("STOPPING TALING SERVICE");
    tailingService.stop();
  }

  public void stopPingService() {
    LOGGER.warn("STOPPING PING SERVICE");
    isPingServiceRunning = false;
  }

  @Override
  public void update(String s) {

    if (s.equals("PULS")) {
      LOGGER.info("PULS DETECTED");
    } else {
      LOGGER.info("NEW LOG FOUND - " + s);
    }

    if (session.isOpen()) {
      myMessageHandler.sendMessage(s);
    } else {
      LOGGER.info("SOCKET OFFLINE");
      stopPingService();
      stopTalingService();
    }
    LOGGER.info("WAITING...");
  }

  private void threadSleep() {
    try {
      Thread.sleep(THREAD_SLEEP_DEFAULT_TIME);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
