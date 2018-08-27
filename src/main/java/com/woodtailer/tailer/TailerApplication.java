package com.woodtailer.tailer;

import com.woodtailer.tailer.controller.TailingController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TailerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TailerApplication.class, args);
    TailingController tailingController = new TailingController();
    tailingController.startTailingService();
  }
}
