package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.server.rest.endpoints.handler.EndpointHandler;
import com.woodtailer.tailer.server.rest.response.MailResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class MailServiceEndpoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceEndpoint.class);
  private EndpointHandler endpointHandler;

  public MailServiceEndpoint(EndpointHandler endpointHandler) {
    this.endpointHandler = endpointHandler;
  }

  @PutMapping(path = "/mailservice/emailaddress/")
  public ResponseEntity<MailResponse> addEmailAddress(@RequestParam String email) {
    return ResponseEntity.ok(endpointHandler.addEmailToSubscribers(email));
  }
}
