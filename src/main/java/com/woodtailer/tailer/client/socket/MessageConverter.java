package com.woodtailer.tailer.client.socket;

import com.google.gson.Gson;

public class MessageConverter {

  public LogMessage convertHandleFrameMessage(Object object) {
    return new Gson().fromJson(new String((byte[]) object), LogMessage.class);
  }
}
