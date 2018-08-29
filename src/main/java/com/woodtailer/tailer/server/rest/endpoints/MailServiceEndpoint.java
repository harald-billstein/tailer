package com.woodtailer.tailer.server.rest.endpoints;

import com.woodtailer.tailer.mailsender.EmailSubscribers;
import com.woodtailer.tailer.server.rest.response.MailResponse;
import com.woodtailer.tailer.util.EmailVerifier;
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
  private EmailSubscribers emailSubscribers;

  public MailServiceEndpoint(EmailSubscribers emailSubscribers) {
    this.emailSubscribers = emailSubscribers;
  }

  @PutMapping(path = "/mailservice/emailaddress/")
  public ResponseEntity<MailResponse> addEmailAddress(@RequestParam String mail) {
    MailResponse mailResponse = new MailResponse();

    if (EmailVerifier.validateMailadress(mail) && !EmailVerifier
        .isDuplicate(mail, emailSubscribers)) {
      emailSubscribers.getAddresses().add(mail.toLowerCase());
      mailResponse.setSuccess(true);
      mailResponse.setMessage("Mail to subscribers list");
      mailResponse.setRegisterdMail(mail);
    } else {
      mailResponse.setSuccess(false);
      mailResponse.setMessage("Duplicate or not valid email");
      mailResponse.setRegisterdMail(mail);
    }

    return ResponseEntity.ok(mailResponse);
  }
}
