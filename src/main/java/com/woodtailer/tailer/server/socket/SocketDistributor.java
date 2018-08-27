package com.woodtailer.tailer.server.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketDistributor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SocketDistributor.class);

  @MessageMapping("/logs")
  @SendTo("log/test")
  public String SendLog(String log) throws Exception {
    Thread.sleep(1000);
    LOGGER.info("Distributing to subscribers :" + log);
    return log;
  }

}
