package com.woodtailer.tailer.server.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketDistributor {

  private static final Logger LOGGER = LoggerFactory.getLogger(SocketDistributor.class);

  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public String SendLog(String log) {
    LOGGER.info("SENDING LOG : " + log);
    return log;
  }

}
