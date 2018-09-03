package com.woodtailer.tailer.mailsender;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmailSubscribers {

  private List<String> addresses = new ArrayList<>();

  private EmailSubscribers() {
  }

  public List<String> getAddresses() {
    return addresses;
  }

}
