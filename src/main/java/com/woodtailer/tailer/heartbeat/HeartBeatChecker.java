package com.woodtailer.tailer.heartbeat;

import com.woodtailer.tailer.enums.RestMethods;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class HeartBeatChecker {


  @Value("${heartbeat.url}")
  private URL url;

  public boolean getPuls() {

    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(RestMethods.GET.method());
      connection.setRequestProperty("Accept", "application/json");
      return connection.getResponseCode() == 200;

    } catch (Exception e) {
      return false;
    }
  }
}
