package com.xyl3m.demo.parsec.enumtype;

import static com.google.common.truth.Truth.assertThat;

import org.apache.http.HttpStatus;
import org.junit.Test;

public class ResourceExceptionTypeTest {

  @Test
  public void testCode() {
    ResourceExceptionType type = ResourceExceptionType.INTERNAL_SERVER_ERROR;
    assertThat(type.statusCode()).isEqualTo(HttpStatus.SC_INTERNAL_SERVER_ERROR);
    assertThat(type.code()).isEqualTo(50000000);
    assertThat(type.message()).isEqualTo("Internal server error");
    assertThat(type.detailMessage()).isEqualTo("[50000000] Internal server error");
    assertThat(type).isEqualTo(ResourceExceptionType.type(50000000));
  }
}
