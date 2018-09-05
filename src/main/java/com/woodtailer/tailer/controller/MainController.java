package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.client.socket.MyMessageHandler;
import com.woodtailer.tailer.client.socket.MyMessageHandlerListener;
import com.woodtailer.tailer.pulse.PulseCheckerListener;
import com.woodtailer.tailer.pulse.PulseCheckerService;
import com.woodtailer.tailer.tailing.TailerServiceListener;
import com.woodtailer.tailer.tailing.TailingService;
import java.util.concurrent.ExecutionException;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;


@Controller
public class MainController implements TailerServiceListener, MyMessageHandlerListener,
    PulseCheckerListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
  private static final int THREAD_SLEEP_DEFAULT_TIME = 10000;
  private final TailingService tailingService;
  private final MyMessageHandler myMessageHandler;
  private PulseCheckerService pulseChecker;

  private boolean isSocketOnline;
  @Getter
  private boolean isPulseServiceRunning;
  @Getter
  private boolean isTailingServerRunning;

  @Value("${logfile.url}")
  private String path;

  public MainController(
      TailingService tailingService, MyMessageHandler myMessageHandler,
      PulseCheckerService pulseChecker) {
    this.tailingService = tailingService;
    this.pulseChecker = pulseChecker;
    this.myMessageHandler = myMessageHandler;
  }

  public void startMainController() {
    myMessageHandler.setMyMessageHandlerListener(this);
    tailingService.setTailerServiceListener(this);
    pulseChecker.setListener(this);
    startSocket();

    Thread pulseCheckerThread = new Thread(pulseChecker);
    pulseCheckerThread.start();

  }

  private void startSocket() {
    LOGGER.info("STARTING SOCKET...");

    while (!isSocketOnline) {
      try {
        isSocketOnline = true;
        myMessageHandler.connect();
        LOGGER.info("SOCKET - ONLINE");
      } catch (ExecutionException | InterruptedException e1) {
        LOGGER.error(
            "SOCKET OFFLINE - RECONNECT IN " + (THREAD_SLEEP_DEFAULT_TIME / 1000)
                + " SECONDS");
        isSocketOnline = false;
        threadSleep();
      }
    }
  }

  protected void startTailingService() {
    LOGGER.info("START TAILING");

    if (isSocketOnline && !isTailingServerRunning) {
      isTailingServerRunning = true;
      tailingService.start();
    } else if (!isSocketOnline) {
      LOGGER.warn("START TAILING : SOCKET SERVER DOWN");
    }
  }

  protected void stopTailingService() {
    LOGGER.warn("STOPPING TAILING");
    isTailingServerRunning = false;
    tailingService.stop();
  }

  protected void startPulseService() {
    LOGGER.info("STARTING PULSE");

    if (!isPulseServiceRunning && isSocketOnline) {
      isPulseServiceRunning = true;
      pulseChecker.startPulse();
    } else if (!isSocketOnline) {
      LOGGER.warn("STARTING PULSE - SOCKET OFFLINE");
    }
  }

  public void stopPulseService() {
    LOGGER.warn("STOPPING PULSE");
    pulseChecker.stopPulse();
    isPulseServiceRunning = false;
  }

  private void threadSleep() {
    try {
      Thread.sleep(THREAD_SLEEP_DEFAULT_TIME);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(String s) {
    if (isSocketOnline) {
      LOGGER.info("DELIVERING MESSAGE : " + s);
      myMessageHandler.sendMessage(s);
    } else {
      LOGGER.warn("SOCKET OFFLINE - MESSAGE NOT DELIVERED : " + s);
    }
  }

  @Override
  public void status(String s) {

    if (s.equals("afterConnectionClosed")) {
      stopPulseService();
      stopTailingService();
      isSocketOnline = false;
      startSocket();
      startPulseService();
      startTailingService();
    }
  }

  @Override
  public void pulse(boolean pulse) {
    if (isSocketOnline) {
      LOGGER.info("DELIVERING : PULSE");
      myMessageHandler.sendMessage("PULSE");
    } else {
      LOGGER.warn("SOCKET OFFLINE PULSE NOT DELIVERED");
    }
  }
}
