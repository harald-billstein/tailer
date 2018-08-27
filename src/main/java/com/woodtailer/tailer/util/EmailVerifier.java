package com.woodtailer.tailer.util;

public class EmailVerifier {

  public static boolean validateMailadress(String mailaddress) {

    if (mailaddress.contains("@")) {
      // MAKE BETTER CHECK
      return true;
    } else {
      return false;
    }
  }

}
