package com.woodtailer.tailer.client.socket;

import java.util.ArrayList;
import java.util.List;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

@Controller
public class SocketController {

  private StompSessionHandler sessionHandler;


  public void start() {

    System.out.println("Clientsocket");
    String URL = "http://localhost:8080/gs-guide-websocket";

    List<Transport> transports = new ArrayList<>(1);
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));

    WebSocketClient transport = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(transport);

    sessionHandler = new StompHandlerIMP();

    stompClient.connect(URL, sessionHandler);

  }

  public void sendLog(String log){
    StompHandlerIMP stompHandlerIMP = (StompHandlerIMP) sessionHandler;
    stompHandlerIMP.sendLog(log);

  }

}
