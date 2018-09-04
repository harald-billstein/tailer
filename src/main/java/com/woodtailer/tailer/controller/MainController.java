package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.client.socket.MyMessageHandler;
import com.woodtailer.tailer.pulse.PulseChecker;
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
  private boolean isPulseServiceRunning;
  @Getter
  private boolean isTailingServerRunning;

  private WebSocketSession session;

  private TailingService tailingService;
  private MyMessageHandler myMessageHandler;
  private PulseChecker heartBeatChecker;

  private Thread pulseThread;

  @Value("${logfile.url}")
  private String path;

  public MainController(
      TailingService tailingService, MyMessageHandler myMessageHandler,
      PulseChecker heartBeatChecker) {
    this.tailingService = tailingService;
    this.heartBeatChecker = heartBeatChecker;
    this.myMessageHandler = myMessageHandler;
    init();
  }

  private void init() {
    tailingService.setTailerServiceListener(this);
    startSocket();
  }

  public void initPulseService() {
    LOGGER.info("INIT PULSE THREAD");

    pulseThread = new Thread(() -> {

      while (session.isOpen() && isPulseServiceRunning) {

        try {
          Thread.sleep(10000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        if (heartBeatChecker.getPuls()) {
          update("PULS");
        }
      }
      LOGGER.info("PULSE THREAD STOPPED");
    }
    );
  }

  public void startSocket() {
    boolean isSocketOnline = false;

    while (!isSocketOnline) {
      try {
        session = myMessageHandler.connect();
        isSocketOnline = session.isOpen();
        LOGGER.info("CONNECTION SUCCESS");
      } catch (ExecutionException | InterruptedException e1) {
        LOGGER.error(
            "CONNECTION FAILED - RECONNECT IN " + (THREAD_SLEEP_DEFAULT_TIME / 1000)
                + " SECONDS");
        threadSleep();
      }
    }
  }

  public void startTailingService() {

    if (session.isOpen()) {
      tailingService.start();
      isTailingServerRunning = true;
      LOGGER.info("TAILING SERVICE STARTED");
    } else if (!session.isOpen()) {
      LOGGER.warn("TAILING SERVICE : SOCKET SERVER DOWN");
    }
  }


  public void stopTailingService() {
    LOGGER.warn("STOPPING TAILING SERVICE");
    isTailingServerRunning = false;
    tailingService.stop();
  }

  public void startPulseService() {
    if (!isPulseServiceRunning) {
      isPulseServiceRunning = true;
      initPulseService();
      pulseThread.start();
    }
  }

  public void stopPulseService() {
    LOGGER.warn("STOPPING PULSE SERVICE");
    isPulseServiceRunning = false;
  }

  @Override
  public void update(String s) {
    LOGGER.info("ACTIVE THREADS : " + Thread.activeCount());

    if (s.equals("PULS")) {
      LOGGER.info("PULS DETECTED");
    } else {
      LOGGER.info("NEW LOG FOUND - " + s);
    }

    if (session.isOpen()) {
      myMessageHandler.sendMessage(s);
    } else {
      LOGGER.info("SOCKET OFFLINE");
      stopPulseService();
      stopTailingService();
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
