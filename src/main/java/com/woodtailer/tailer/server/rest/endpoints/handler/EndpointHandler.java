package com.woodtailer.tailer.server.rest.endpoints.handler;

import com.woodtailer.tailer.controller.MainController;
import com.woodtailer.tailer.server.rest.response.StartApplicationResonse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private MainController mainController;

  public EndpointHandler(MainController mainController) {
    this.mainController = mainController;
  }

  public StartApplicationResonse startHeartbeat() {

    mainController.startPingService();
    StartApplicationResonse startApplicationResonse = new StartApplicationResonse();
    startApplicationResonse.setApplicationStarted(true);
    return startApplicationResonse;
  }

  public StartApplicationResonse startTaling() {
    LOGGER.info("APPLICATION STARTED...");
    //TODO CHECK SO APP STARTED

    if (mainController.isPingServiceRunning()) {

    }

    mainController.startTailingService();
    StartApplicationResonse startApplicationResonse = new StartApplicationResonse();
    startApplicationResonse.setApplicationStarted(true);
    return startApplicationResonse;
  }

  public void stopHeartBet() {
    mainController.stopPingService();
  }

  public void stopTailing() {
    mainController.stopTalingService();

  }
}
