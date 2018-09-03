package com.woodtailer.tailer.client.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.PongMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;


@Service
public class MyMessageHandler extends TextWebSocketHandler {

  private static final Logger LOGGER = LoggerFactory.getLogger(MyMessageHandler.class);

  private WebSocketSession session;
  private String URL = "http://localhost:8080/gs-guide-websocket";

  public MyMessageHandler() {
  }

  public WebSocketSession connect() throws ExecutionException, InterruptedException {
    List<Transport> transports = new ArrayList<>(2);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    transports.add(new RestTemplateXhrTransport());

    SockJsClient sockJsClient = new SockJsClient(transports);
    ListenableFuture<WebSocketSession> session = sockJsClient.doHandshake(this, URL);

    return session.get();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.session = session;
    LOGGER.info("Sending hello from Tailer");
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
      throws Exception {
    //LOGGER.info("handleMessage");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    LOGGER.info("handleTextMessage");
  }

  @Override
  protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
    LOGGER.info("handlePongMessage");
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
    LOGGER.info("handleTransportError");
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    LOGGER.info("afterConnectionClosed");
  }

  @Override
  public boolean supportsPartialMessages() {
    LOGGER.info("supportsPartialMessages");
    return super.supportsPartialMessages();
  }

  public boolean sendMessage(String message) {
    boolean success;

    try {
      if (session.isOpen()) {
        session.sendMessage(new TextMessage(message));
        success = true;
      } else {
        success = false;
      }
    } catch (IOException e) {
      e.printStackTrace();
      success = false;
    }
    return success;
  }

}
