package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.controller.EndpointHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/v1")
public class ApplicationEndpointService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationEndpointService.class);

  private final EndpointHandler endpointHandler;

  public ApplicationEndpointService(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @PostMapping(path = "/application/tailer/starter")
  public ResponseEntity<ServiceResponse> startTailer() {
    return endpointHandler.startTailing();
  }

  @PostMapping(path = "/application/pulse/starter")
  public ResponseEntity<ServiceResponse> startHeartbeat() {
    return endpointHandler.startPulse();
  }

  @PostMapping(path = "/application/tailer/stop")
  public ResponseEntity<ServiceResponse> stopTailer() {
    return endpointHandler.stopTailing();

  }

  @PostMapping(path = "/application/pulse/stop")
  public ResponseEntity<ServiceResponse> stopHeartbeat() {
    return endpointHandler.stopPulse();
  }

}
