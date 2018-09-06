package com.woodtailer.tailer.server.rest.endpoints;

import lombok.Data;

@Data
public class ServiceResponse {

  private boolean ServiceRunning;
  private String serviceName;

}
