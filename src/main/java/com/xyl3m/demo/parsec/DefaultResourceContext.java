package com.xyl3m.demo.parsec;

import com.xyl3m.demo.parsec.parsec_generated.ResourceContext;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 * ResourceContext default implementation.
 */
public class DefaultResourceContext implements ResourceContext {

  /**
   * Request.
   */
  private transient HttpServletRequest httpServletRequest;

  /**
   * Response.
   */
  private transient HttpServletResponse httpServletResponse;

  /**
   * Constructor.
   *
   * @param httpServletRequest  Request
   * @param httpServletResponse Response
   */
  public DefaultResourceContext(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse) {
    this.httpServletRequest = httpServletRequest;
    httpServletResponse.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
    this.httpServletResponse = httpServletResponse;
  }

  /**
   * Get request.
   *
   * @return Request
   */
  @Override
  public HttpServletRequest request() {
    return httpServletRequest;
  }

  /**
   * Get response.
   *
   * @return Response
   */
  @Override
  public HttpServletResponse response() {
    return httpServletResponse;
  }

  /**
   * Authenticate.
   */
  @Override
  public void authenticate() {
    // Unused
  }

  /**
   * Authorize.
   *
   * @param action        Action
   * @param resource      Resource
   * @param trustedDomain Trusted domain
   */
  @Override
  public void authorize(String action, String resource, String trustedDomain) {
    // Unused
  }

}
