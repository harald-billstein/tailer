package com.woodtailer.tailer.enums;

public enum RestMethods {
  GET("GET");

  private String method;

  RestMethods(String method) {
    this.method = method;
  }

  public String method() {
    return method;
  }
}
