package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.properties.LogProperties;
import com.woodtailer.tailer.util.LogUrlVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class LogService {

  private static final Logger LOGGER = LoggerFactory.getLogger(LogService.class);

  @PutMapping(path = "logurl/log")
  public ResponseEntity<String> addLogUrl(@RequestParam String url) {
    LOGGER.info(url);

    boolean isValiedUrl = LogUrlVerifier.verifyLogUrl(url);

    if (isValiedUrl) {
      LogProperties logProperties = LogProperties.getInstance();
      logProperties.setUrl(url);
    }

    return ResponseEntity.ok("Found url : " + isValiedUrl);
  }

}
