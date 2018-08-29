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
public class StartApplicationServiceEndpoint {

  private static final Logger LOGGER = LoggerFactory
      .getLogger(StartApplicationServiceEndpoint.class);

  private EndpointHandler endpointHandler;

  public StartApplicationServiceEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @PostMapping(path = "/application/starter")
  public ResponseEntity<StartApplicationResonse> startApplicartion() {
    return ResponseEntity.ok(endpointHandler.startApplication());
  }

}
