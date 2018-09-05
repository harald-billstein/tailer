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
  private final String URL = "http://localhost:8080/websocket";
  private MyMessageHandlerListener myMessageHandlerListener;
  private WebSocketSession session;

  public MyMessageHandler() {
  }

  public void connect() throws ExecutionException, InterruptedException {
    List<Transport> transports = new ArrayList<>(2);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    transports.add(new RestTemplateXhrTransport());

    SockJsClient sockJsClient = new SockJsClient(transports);
    ListenableFuture<WebSocketSession> session = sockJsClient.doHandshake(this, URL);
    this.session = session.get();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    this.session = session;
    LOGGER.info("TAILER CONNECTED");
    myMessageHandlerListener.status("afterConnectionEstablished");
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
    session.close();
    myMessageHandlerListener.status("afterConnectionClosed");
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

  public boolean isSessionOpen() {
    return session.isOpen();
  }

  public void setMyMessageHandlerListener(
      MyMessageHandlerListener myMessageHandlerListener) {
    this.myMessageHandlerListener = myMessageHandlerListener;
  }
}
