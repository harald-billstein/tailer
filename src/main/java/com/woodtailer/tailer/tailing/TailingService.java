package com.woodtailer.tailer.tailing;

import java.io.File;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TailingService implements TailerListener {

  private static final Logger LOGGER = LoggerFactory.getLogger(TailingService.class);

  private TailerServiceListener tailerServiceListener;

  public TailingService() {
    LOGGER.info("TailingService starting...");
  }

  public void setTailerServiceListener(TailerServiceListener tailerServiceListener) {
    this.tailerServiceListener = tailerServiceListener;
  }

  public void run(String filePath) {

    Thread tailerService = new Thread(() -> {
      Tailer tailer = new Tailer(new File(filePath), this);
      File file = tailer.getFile();

      if (file.exists()) {
        tailer.run();
      } else {
        LOGGER.error("File not found!");
      }

    });

    tailerService.start();
    LOGGER.info("TailingService started");
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














