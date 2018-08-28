package com.woodtailer.tailer.properties;

public class LogProperties {

  private static LogProperties logProperties = new LogProperties();
  private String url;

  private LogProperties() {
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public static LogProperties getInstance() {
    return logProperties;
  }
}
