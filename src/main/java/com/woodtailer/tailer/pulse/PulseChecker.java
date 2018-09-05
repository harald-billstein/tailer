package com.woodtailer.tailer.pulse;

import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PulseChecker {


  @Value("${pulse.url}")
  private URL url;

  public boolean getPuls() {

    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");
      return connection.getResponseCode() == 200;

    } catch (Exception e) {
      return false;
    }
  }
}
