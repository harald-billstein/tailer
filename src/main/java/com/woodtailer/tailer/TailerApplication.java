package com.woodtailer.tailer;

import com.woodtailer.tailer.controller.MainController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class TailerApplication {

  private final MainController mainController;

  public TailerApplication(MainController mainController) {
    this.mainController = mainController;
  }

  public static void main(String[] args) {
    SpringApplication.run(TailerApplication.class, args);
  }

  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    mainController.startMainController();
  }

}
