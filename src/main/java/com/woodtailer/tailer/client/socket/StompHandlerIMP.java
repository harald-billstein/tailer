package com.woodtailer.tailer.client.socket;

import java.lang.reflect.Type;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Service;

@Service
public class StompHandlerIMP implements StompSessionHandler {

  private StompSession stompSession;
  private MessageConverter messageConverter;



  @Override
  public void afterConnected(StompSession stompSession, StompHeaders stompHeaders) {
    System.out.println("afterConnected");
    //stompSession.subscribe("/topic/greetings", this);
    //stompSession.send("/app/hello", "string".getBytes());
   this.stompSession = stompSession;
  }

  @Override
  public void handleException(StompSession stompSession, StompCommand stompCommand,
      StompHeaders stompHeaders, byte[] bytes, Throwable throwable) {
    System.out.println("handleException : " + throwable.getMessage());
  }

  @Override
  public void handleTransportError(StompSession stompSession, Throwable throwable) {
    System.out.println("handleTransportError : " + throwable.getMessage());
  }

  @Override
  public Type getPayloadType(StompHeaders stompHeaders) {
    System.out.println("getPayloadType : " + stompHeaders.getContentType());
    return null;
  }

  @Override
  public void handleFrame(StompHeaders stompHeaders, Object o) {
    System.out.println("handleFrame : " + o.toString());
    MessageConverter messageConverter = new MessageConverter();
    System.out.println("message: " + messageConverter.convertHandleFrameMessage(o).getContent());
  }

  public void sendLog(String log){
    //System.out.println("sending from stomphandlerIMP");
    //System.out.println(stompSession);
    stompSession.send("/app/hello", log.getBytes());

  }

}
