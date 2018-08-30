package com.woodtailer.tailer.server.rest.endpoints.handler;

import com.woodtailer.tailer.controller.TailingController;
import com.woodtailer.tailer.heartbeat.HeartBeatChecker;
import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.server.rest.response.MailResponse;
import com.woodtailer.tailer.server.rest.response.PingServiceResponse;
import com.woodtailer.tailer.server.rest.response.StartApplicationResonse;
import com.woodtailer.tailer.util.EmailVerifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EndpointHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(EndpointHandler.class);

  private EmailSubscribers emailSubscribers;
  private HeartBeatChecker heartBeatChecker;
  private TailingController tailingController;

  public EndpointHandler(EmailSubscribers emailSubscribers, HeartBeatChecker heartBeatChecker,
      TailingController tailingController) {
    this.emailSubscribers = emailSubscribers;
    this.heartBeatChecker = heartBeatChecker;
    this.tailingController = tailingController;
  }

  public MailResponse addEmailToSubscribers(String email) {
    LOGGER.info("ADDING MAIL TO SUBSCRIBERS...");

    MailResponse mailResponse = new MailResponse();

    if (EmailVerifier.validateMailadress(email) && !EmailVerifier
        .isDuplicate(email, emailSubscribers)) {
      emailSubscribers.getAddresses().add(email.toLowerCase());
      mailResponse.setSuccess(true);
      mailResponse.setMessage("Mail to subscribers list");
      mailResponse.setRegisterdMail(email);
    } else {
      mailResponse.setSuccess(false);
      mailResponse.setMessage("Duplicate or not valid email");
      mailResponse.setRegisterdMail(email);
    }
    return mailResponse;
  }

  public PingServiceResponse heartBeatCheck() {
    LOGGER.info("HEART BEAT CHECKER ACTIVATED...");

    PingServiceResponse pingServiceResponse = new PingServiceResponse();

    long start = System.currentTimeMillis();
    boolean status = heartBeatChecker.getPuls();
    long end = System.currentTimeMillis();

    pingServiceResponse.setResponseTimeInMs(end - start);
    pingServiceResponse.setSuccess(status);

    return pingServiceResponse;


  }


public StartApplicationResonse startHeartbeat() {

    tailingController.startPingService();
    StartApplicationResonse startApplicationResonse = new StartApplicationResonse();
    startApplicationResonse.setApplicationStarted(true);
    return startApplicationResonse;
}

  public StartApplicationResonse startTaling() {
    LOGGER.info("APPLICATION STARTED...");
    //TODO CHECK SO APP STARTED
    tailingController.startTailingService();
    StartApplicationResonse startApplicationResonse = new StartApplicationResonse();
    startApplicationResonse.setApplicationStarted(true);
    return startApplicationResonse;
  }
}
