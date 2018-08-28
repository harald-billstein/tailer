package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.server.rest.PingServiceResponse;
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

  private HeartBeatChecker heartBeatChecker;

  public PingServiceEndpoint(HeartBeatChecker heartBeatChecker) {
    this.heartBeatChecker = heartBeatChecker;
  }

  @GetMapping(path = "/pingservice")
  public ResponseEntity<PingServiceResponse> checkStatusOfApplication() {

    PingServiceResponse pingServiceResponse = new PingServiceResponse();

    long start = System.currentTimeMillis();
    boolean status = heartBeatChecker.checkHeatBeat();
    long end = System.currentTimeMillis();

    pingServiceResponse.setResponseTimeInMs(end - start);
    pingServiceResponse.setSuccess(status);

    return ResponseEntity.ok(pingServiceResponse);
  }

}
