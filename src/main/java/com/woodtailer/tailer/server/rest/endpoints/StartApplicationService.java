package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.controller.TailingController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/v1")
public class StartApplicationService {

  private static final Logger LOGGER = LoggerFactory.getLogger(StartApplicationService.class);

  @PostMapping(path = "/application/starter")
  public String startApplicartion() {

    TailingController tailingController = TailingController.getInstance();
    tailingController.startTailingService();


    return "started";
  }

}
