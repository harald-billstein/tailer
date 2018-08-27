package com.woodtailer.tailer.server.rest;

import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.util.EmailVerifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(path = "/v1")
public class Endpoints {

  private HeartBeatChecker heartBeatChecker;
  private EmailSubscribers emailSubscribers = EmailSubscribers.getInstance();

  public Endpoints(HeartBeatChecker heartBeatChecker) {
    this.heartBeatChecker = heartBeatChecker;
  }

  @GetMapping(path = "/pingservice")
  public ResponseEntity<Response> checkStatusOfApplication() {

    Response response = new Response();

    long start = System.currentTimeMillis();
    boolean status = heartBeatChecker.checkHeatBeat();
    long end = System.currentTimeMillis();

    response.setResponseTimeInMs(end - start);
    response.setSuccess(status);

    return ResponseEntity.ok(response);
  }

  @PutMapping(path = "/mailservice/emailaddress/")
  public ResponseEntity<String> addEmailAddress(@RequestParam String mail) {

    if (EmailVerifier.validateMailadress(mail)) {
      emailSubscribers.getAddresses().add(mail);
    }
    return ResponseEntity.ok(mail);
  }

}
