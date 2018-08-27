package com.woodtailer.tailer.tailing;

public interface TailerServiceListener {

  void update(String s);

  void sendMail(Exception e);

}