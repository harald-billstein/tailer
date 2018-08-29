package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.server.rest.endpoints.handler.EndpointHandler;
import com.woodtailer.tailer.server.rest.response.PingServiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/v1")
public class PingServiceEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(PingServiceEndpoint.class);

  private EndpointHandler endpointHandler;

  public PingServiceEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @GetMapping(path = "/pingservice")
  public ResponseEntity<PingServiceResponse> checkStatusOfApplication() {

    return ResponseEntity.ok(endpointHandler.heartBeatCheck());
  }

}
