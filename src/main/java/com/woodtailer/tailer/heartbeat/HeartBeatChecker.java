package com.woodtailer.tailer.heartbeat;

import com.woodtailer.tailer.enums.RestMethods;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.stereotype.Service;

@Service
public class HeartBeatChecker {

  private URL url = null;

  public boolean checkHeatBeat() {

    try {
      url = new URL("http://www.mocky.io/v2/5b7feb723400002a00dc068b");
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod(RestMethods.GET.method());
      connection.setRequestProperty("Accept", "application/json");
      return connection.getResponseCode() == 200;

    } catch (Exception e) {
      return false;
    }
  }
}
