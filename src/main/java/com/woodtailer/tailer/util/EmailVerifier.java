package com.woodtailer.tailer.util;

import com.woodtailer.tailer.mailsender.EmailSubscribers;

public class EmailVerifier {

  public static boolean validateMailadress(String mailaddress) {

    if (mailaddress.contains("@")) {
      // MAKE BETTER CHECK
      return true;
    } else {
      return false;
    }
  }

  public static boolean isDuplicate(String mailaddress, EmailSubscribers emailSubscribers) {

    if (emailSubscribers.getAddresses().contains(mailaddress.toLowerCase())) {
      return true;

    } else {
      return false;
    }

  }

}
