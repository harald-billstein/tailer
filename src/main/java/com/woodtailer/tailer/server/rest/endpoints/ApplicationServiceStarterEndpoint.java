package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.server.rest.endpoints.handler.EndpointHandler;
import com.woodtailer.tailer.server.rest.response.StartApplicationResonse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/v1")
public class ApplicationServiceStarterEndpoint {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationServiceStarterEndpoint.class);

  private EndpointHandler endpointHandler;

  public ApplicationServiceStarterEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @PostMapping(path = "/application/tailer/starter")
  public ResponseEntity<StartApplicationResonse> startTailer() {
    return ResponseEntity.ok(endpointHandler.startTaling());
  }

  @PostMapping(path = "/application/heartbeat/starter")
  public ResponseEntity<StartApplicationResonse> startHeartbeat() {
    return ResponseEntity.ok(endpointHandler.startHeartbeat());
  }

}
