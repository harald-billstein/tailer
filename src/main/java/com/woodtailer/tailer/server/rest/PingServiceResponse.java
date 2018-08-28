package com.woodtailer.tailer.server.rest;

public class PingServiceResponse {

  private boolean success;
  private long responseTimeInMs;


  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public long getResponseTimeInMs() {
    return responseTimeInMs;
  }

  public void setResponseTimeInMs(long responseTimeInMs) {
    this.responseTimeInMs = responseTimeInMs;
  }
}
