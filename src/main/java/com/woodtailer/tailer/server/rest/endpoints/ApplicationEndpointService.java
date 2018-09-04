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
public class ApplicationEndpointService {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(ApplicationEndpointService.class);

  private EndpointHandler endpointHandler;

  public ApplicationEndpointService(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @PostMapping(path = "/application/tailer/starter")
  public ResponseEntity<StartApplicationResonse> startTailer() {
    return ResponseEntity.ok(endpointHandler.startTailing());
  }

  @PostMapping(path = "/application/heartbeat/starter")
  public ResponseEntity<StartApplicationResonse> startHeartbeat() {
    return ResponseEntity.ok(endpointHandler.startHeartbeat());
  }


  @PostMapping(path = "/application/tailer/stop")
  public ResponseEntity<String> stopTailer() {
    endpointHandler.stopTailing();
    return ResponseEntity.ok("done");
  }


  @PostMapping(path = "/application/heartbeat/stop")
  public ResponseEntity<String> stopHeartbeat() {
    endpointHandler.stopHeartBeat();
    return ResponseEntity.ok("done");
  }

}
