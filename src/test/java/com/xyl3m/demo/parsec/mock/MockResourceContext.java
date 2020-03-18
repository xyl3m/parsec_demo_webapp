package com.xyl3m.demo.parsec.mock;

import com.xyl3m.demo.parsec.parsec_generated.ResourceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MockResourceContext implements ResourceContext {

  private transient HttpServletRequest httpServletRequest;
  private transient HttpServletResponse httpServletResponse;

  public MockResourceContext() {
  }

  public MockResourceContext setRequest(HttpServletRequest request) {
    this.httpServletRequest = request;
    return this;
  }

  public MockResourceContext setResponse(HttpServletResponse response) {
    this.httpServletResponse = response;
    return this;
  }

  @Override
  public HttpServletRequest request() {
    return httpServletRequest;
  }

  @Override
  public HttpServletResponse response() {
    return httpServletResponse;
  }

  @Override
  public void authenticate() {
  }

  @Override
  public void authorize(String action, String resource, String trustedDomain) {
  }

}
