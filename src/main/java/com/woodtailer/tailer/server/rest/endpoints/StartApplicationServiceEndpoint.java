package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.controller.TailingController;
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

  private TailingController tailingController;

  public StartApplicationServiceEndpoint(TailingController tailingController) {
    this.tailingController = tailingController;
  }

  @PostMapping(path = "/application/starter")
  public ResponseEntity<String> startApplicartion() {

    tailingController.startTailingService();

    return ResponseEntity.ok("started");
  }

}
