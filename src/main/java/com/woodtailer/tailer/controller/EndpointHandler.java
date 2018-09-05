package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.server.rest.endpoints.ServiceResonse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private final MainController mainController;

  public EndpointHandler(MainController mainController) {
    this.mainController = mainController;
  }

  public ResponseEntity<ServiceResonse> startHeartbeat() {

    mainController.startPulseService();
    ServiceResonse serviceResonse = new ServiceResonse();
    serviceResonse.setServiceStarted(mainController.isPulseServiceRunning());
    serviceResonse.setServiceName("Pulse service");
    return ResponseEntity.ok(serviceResonse);
  }

  public ResponseEntity<ServiceResonse> startTailing() {
    mainController.startTailingService();
    ServiceResonse serviceResonse = new ServiceResonse();
    serviceResonse.setServiceStarted(mainController.isTailingServerRunning());
    serviceResonse.setServiceName("Tailing service");
    return ResponseEntity.ok(serviceResonse);
  }

  public ResponseEntity<ServiceResonse> stopHeartBeat() {
    ServiceResonse serviceResonse = new ServiceResonse();
    serviceResonse.setServiceStarted(mainController.isPulseServiceRunning());
    serviceResonse.setServiceName("Pulse service");
    mainController.stopPulseService();
    return ResponseEntity.ok(serviceResonse);
  }

  public ResponseEntity<ServiceResonse> stopTailing() {
    ServiceResonse serviceResonse = new ServiceResonse();
    serviceResonse.setServiceStarted(mainController.isTailingServerRunning());
    serviceResonse.setServiceName("Tailing service");
    mainController.stopTailingService();
    return ResponseEntity.ok(serviceResonse);
  }
}
