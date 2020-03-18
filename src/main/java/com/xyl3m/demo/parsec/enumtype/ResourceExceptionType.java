package com.xyl3m.demo.parsec.enumtype;

import com.xyl3m.demo.parsec.util.AppConfig;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public enum ResourceExceptionType {
  INVALID_USER_ID(40001001),
  USER_NOT_FOUND(40401001),
  INTERNAL_SERVER_ERROR(50000000),
  ACCESS_MYDB_FAILED(50001001);

  private static final Map<Integer, ResourceExceptionType> MAPPER = new HashMap<>();
  private static final int DIVIDE_NUMBER = 100000;

  static {
    Arrays.asList(ResourceExceptionType.values()).forEach(type -> MAPPER.put(type.code(), type));
  }

  private final int code;

  ResourceExceptionType(final int code) {
    this.code = code;
  }

  public static ResourceExceptionType type(final int code) {
    return MAPPER.get(code);
  }

  public int code() {
    return this.code;
  }

  public String message() {
    return AppConfig.getErrorMessage(code);
  }

  /**
   * Get detail error message with format {@code [code] message}.
   *
   * @return String of the detail error message
   */
  public String detailMessage() {
    return String.format("[%d] %s", code, message());
  }

  /**
   * Get exception status code.
   *
   * @return Integer of the status code
   */
  public int statusCode() {
    return code / DIVIDE_NUMBER;
  }

}
