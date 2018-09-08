package com.woodtailer.tailer.pulse;

import java.net.HttpURLConnection;
import java.net.URL;
import lombok.Data;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Data
public class PulseCheckerService implements Runnable {

  private static final Logger LOGGER = LoggerFactory.getLogger(PulseCheckerService.class);

  private PulseCheckerListener listener;
  private boolean run;

  @Value("${pulse.url}")
  private URL url;

  private String getPulse() {
    String response = null;

    try {
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Accept", "application/json");

      if (connection.getResponseCode() == 200) {
        response = IOUtils.toString(connection.getInputStream(), "UTF-8");
      }

    } catch (Exception e) {
      return e.getMessage();
    }
    return response;
  }


  public void startPulse() {
    run = true;
  }

  public void stopPulse() {
    run = false;
  }

  private void threadSleep(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void run() {

    LOGGER.info("PULSESERVICE - ONLINE");

    while (true) {
      while (run) {
        listener.pulse(getPulse());
        threadSleep(10000);
      }
      threadSleep(10000);
    }

  }
}








