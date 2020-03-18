package com.xyl3m.demo.parsec.util;

import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.parsec_generated.ParsecErrorBody;
import com.xyl3m.demo.parsec.parsec_generated.ParsecErrorDetail;
import com.xyl3m.demo.parsec.parsec_generated.ParsecResourceError;
import com.xyl3m.demo.parsec.parsec_generated.ResourceException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceExceptionBuilder {

  private static final Logger LOG = LoggerFactory.getLogger(ResourceExceptionBuilder.class);
  private static final int DIVIDE_NUMBER = 100000;

  private transient List<ParsecErrorDetail> details = new ArrayList<>();

  /**
   * Build resource exception by just ResourceExceptionType.
   *
   * @param type ResourceExceptionType
   * @return ResourceException
   */
  public ResourceException build(final ResourceExceptionType type) {
    return build(type, null, null);
  }

  /**
   * Build resource exception by ResourceExceptionType and error message.
   *
   * @param type ResourceExceptionType
   * @param msg  error message
   * @return ResourceException
   */
  public ResourceException build(final ResourceExceptionType type, final String msg) {
    return build(type, msg, null);
  }

  /**
   * Build resource exception by ResourceExceptionType, error message and invalid value.
   *
   * @param type         ResourceExceptionType
   * @param msg          error message
   * @param invalidValue invalid value
   * @return ResourceException
   */
  public ResourceException build(final ResourceExceptionType type, final String msg,
      final String invalidValue) {
    final int httpStatusCode = type.code() / DIVIDE_NUMBER;
    if (null != msg || null != invalidValue) {
      details.add(
          new ParsecErrorDetail()
              .setInvalidValue(invalidValue)
              .setMessage(msg)
      );
    }

    final ParsecErrorBody body = new ParsecErrorBody()
        .setCode(type.code())
        .setMessage(type.message());
    if (!details.isEmpty()) {
      body.setDetail(details);
    }
    final ParsecResourceError error = new ParsecResourceError()
        .setError(body);

    LOG.error(AppConfig.GSON.toJson(error));
    return new ResourceException(httpStatusCode, error);
  }

  /**
   * Add error with error message.
   *
   * @param msg error message
   * @return this
   */
  public ResourceExceptionBuilder addErrors(final String msg) {
    return addErrors(msg, null);
  }

  /**
   * Add error with error message and invalid value.
   *
   * @param msg          error message
   * @param invalidValue invalid value
   * @return this
   */
  public ResourceExceptionBuilder addErrors(final String msg, final String invalidValue) {
    details.add(new ParsecErrorDetail().setInvalidValue(invalidValue).setMessage(msg));
    return this;
  }

  /**
   * Return has any error be added to this builder before.
   *
   * @return boolean
   */
  public boolean hasError() {
    return !details.isEmpty();
  }

}
