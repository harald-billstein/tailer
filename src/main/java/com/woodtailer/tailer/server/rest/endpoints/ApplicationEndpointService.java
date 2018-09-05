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
  public ResponseEntity<ServiceResonse> startTailer() {
    return endpointHandler.startTailing();
  }

  @PostMapping(path = "/application/heartbeat/starter")
  public ResponseEntity<ServiceResonse> startHeartbeat() {
    return endpointHandler.startHeartbeat();
  }

  @PostMapping(path = "/application/tailer/stop")
  public ResponseEntity<ServiceResonse> stopTailer() {
    return endpointHandler.stopTailing();

  }

  @PostMapping(path = "/application/heartbeat/stop")
  public ResponseEntity<ServiceResonse> stopHeartbeat() {
    return endpointHandler.stopHeartBeat();
  }

}
