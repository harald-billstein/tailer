package com.woodtailer.tailer.server.rest.response;

public class MailResponse {

  private String registerdMail;
  private boolean success;
  private String message;

  public String getRegisterdMail() {
    return registerdMail;
  }

  public void setRegisterdMail(String registerdMail) {
    this.registerdMail = registerdMail;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
