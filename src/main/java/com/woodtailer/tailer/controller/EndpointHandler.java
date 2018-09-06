package com.woodtailer.tailer.controller;

import com.woodtailer.tailer.server.rest.endpoints.ServiceResponse;
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

  public ResponseEntity<ServiceResponse> startPulse() {
    mainController.startPulseService();

    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.setServiceRunning(mainController.isPulseServiceRunning());
    serviceResponse.setServiceName("Pulse service");
    return ResponseEntity.ok(serviceResponse);
  }

  public ResponseEntity<ServiceResponse> startTailing() {
    mainController.startTailingService();

    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.setServiceRunning(mainController.isTailingServerRunning());
    serviceResponse.setServiceName("Tailing service");
    return ResponseEntity.ok(serviceResponse);
  }

  public ResponseEntity<ServiceResponse> stopPulse() {
    mainController.stopPulseService();

    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.setServiceRunning(mainController.isPulseServiceRunning());
    serviceResponse.setServiceName("Pulse service");
    return ResponseEntity.ok(serviceResponse);
  }

  public ResponseEntity<ServiceResponse> stopTailing() {
    mainController.stopTailingService();

    ServiceResponse serviceResponse = new ServiceResponse();
    serviceResponse.setServiceRunning(mainController.isTailingServerRunning());
    serviceResponse.setServiceName("Tailing service");
    return ResponseEntity.ok(serviceResponse);
  }
}
