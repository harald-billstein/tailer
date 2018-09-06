package com.woodtailer.tailer.client.socket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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

  @Value("${socket.url}")
  private String path;
  private MyMessageHandlerListener myMessageHandlerListener;
  private WebSocketSession session;

  public void connect() throws ExecutionException, InterruptedException {
    List<Transport> transports = new ArrayList<>(2);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));
    transports.add(new RestTemplateXhrTransport());
    SockJsClient sockJsClient = new SockJsClient(transports);
    ListenableFuture<WebSocketSession> session = sockJsClient.doHandshake(this, path);
    this.session = session.get();
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) {
    this.session = session;
    LOGGER.info("TAILER CONNECTED");
    myMessageHandlerListener.status("afterConnectionEstablished");
  }

  @Override
  public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
    //LOGGER.info("handleMessage");
  }

  @Override
  protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    LOGGER.info("handleTextMessage");
  }

  @Override
  protected void handlePongMessage(WebSocketSession session, PongMessage message) {
    LOGGER.info("handlePongMessage");
  }

  @Override
  public void handleTransportError(WebSocketSession session, Throwable exception) {
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

  public void sendMessage(String message) {

    try {
      session.sendMessage(new TextMessage(message));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setMyMessageHandlerListener(
      MyMessageHandlerListener myMessageHandlerListener) {
    this.myMessageHandlerListener = myMessageHandlerListener;
  }
}
