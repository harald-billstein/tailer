package com.woodtailer.tailer.tailing;

import java.io.File;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TailingService implements TailerListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingService.class);

  private Tailer tailer;

  @Value("${logfile.url}")
  private String path;

  private TailerServiceListener tailerServiceListener;

  public void setTailerServiceListener(TailerServiceListener tailerServiceListener) {
    this.tailerServiceListener = tailerServiceListener;
  }

  public void start() {

    Thread tailerService = new Thread(() -> {
      tailer = new Tailer(new File(path), this);
      tailer.run();
      File file = tailer.getFile();
      if (!file.exists()) {
        LOGGER.error("FILE NOT FOUND");
      }
    });

    tailerService.start();
  }

  public void stop() {
    LOGGER.info("STOPPED!");
    if (tailer != null) {
      tailer.stop();
    }
  }

  @Override
  public void init(Tailer tailer) {
  }

  @Override
  public void fileNotFound() {
    tailerServiceListener.update("File not found");
  }

  @Override
  public void fileRotated() {
    tailerServiceListener.update("File rotated");
  }

  @Override
  public synchronized void handle(String s) {
    tailerServiceListener.update(s);
  }

  @Override
  public void handle(Exception e) {
    tailerServiceListener.update(e.getMessage());
  }
}














