package com.woodtailer.tailer.util;

import java.io.File;

public class LogUrlVerifier {

  public static boolean verifyLogUrl(String url) {
    File file = new File(url);
    return file.exists();
  }

}
