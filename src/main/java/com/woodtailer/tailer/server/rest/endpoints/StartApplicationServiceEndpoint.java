package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.controller.TailingController;
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

  private TailingController tailingController;

  public StartApplicationServiceEndpoint(TailingController tailingController) {
    this.tailingController = tailingController;
  }

  @PostMapping(path = "/application/starter")
  public ResponseEntity<StartApplicationResonse> startApplicartion() {
    tailingController.startTailingService();
    //TODO CHECK SO APP STARTED
    StartApplicationResonse startApplicationResonse = new StartApplicationResonse();
    startApplicationResonse.setApplicationStarted(true);
    return ResponseEntity.ok(startApplicationResonse);
  }

}
