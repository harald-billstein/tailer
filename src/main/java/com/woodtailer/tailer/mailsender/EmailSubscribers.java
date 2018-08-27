package com.woodtailer.tailer.mailsender;

import java.util.ArrayList;
import java.util.List;

public class EmailSubscribers {

  private static final EmailSubscribers emailSubscribers = new EmailSubscribers();
  private List<String> addresses = new ArrayList<>();

  private EmailSubscribers() {

  }

  public static EmailSubscribers getInstance() {
    return emailSubscribers;
  }

  public List<String> getAddresses() {
    return addresses;
  }

}
