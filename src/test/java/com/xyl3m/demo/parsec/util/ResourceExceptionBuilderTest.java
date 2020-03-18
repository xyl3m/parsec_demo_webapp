package com.xyl3m.demo.parsec.util;


import static com.google.common.truth.Truth.assertThat;

import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.parsec_generated.ParsecErrorBody;
import com.xyl3m.demo.parsec.parsec_generated.ParsecResourceError;
import com.xyl3m.demo.parsec.parsec_generated.ResourceException;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;

public class ResourceExceptionBuilderTest {

  @Test
  public void testBuild_With_Type_Should_Pass() throws Exception {
    ResourceException e = new ResourceExceptionBuilder()
        .build(ResourceExceptionType.INTERNAL_SERVER_ERROR);
    assertThat(e.getCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    ParsecErrorBody parsecErrorBody = e.getData(ParsecResourceError.class).getError();
    assertThat(parsecErrorBody.getCode()).isEqualTo(50000000);
    assertThat(parsecErrorBody.getMessage()).isEqualTo("Internal server error");
    assertThat(parsecErrorBody.getDetail()).isNull();
  }

  @Test
  public void testBuild_With_Msg_Should_Pass() throws Exception {
    ResourceException e = new ResourceExceptionBuilder()
        .build(ResourceExceptionType.INTERNAL_SERVER_ERROR, "foo");
    assertThat(e.getCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    ParsecErrorBody parsecErrorBody = e.getData(ParsecResourceError.class).getError();
    assertThat(parsecErrorBody.getCode()).isEqualTo(50000000);
    assertThat(parsecErrorBody.getMessage()).isEqualTo("Internal server error");
    assertThat(parsecErrorBody.getDetail().get(0).getMessage()).isEqualTo("foo");
    assertThat(parsecErrorBody.getDetail().get(0).getInvalidValue()).isNull();
  }

  @Test
  public void testBuild_With_Value_Should_Pass() throws Exception {
    ResourceException e = new ResourceExceptionBuilder()
        .build(ResourceExceptionType.INTERNAL_SERVER_ERROR, null, "bar");
    assertThat(e.getCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    ParsecErrorBody parsecErrorBody = e.getData(ParsecResourceError.class).getError();
    assertThat(parsecErrorBody.getCode()).isEqualTo(50000000);
    assertThat(parsecErrorBody.getMessage()).isEqualTo("Internal server error");
    assertThat(parsecErrorBody.getDetail().get(0).getMessage()).isNull();
    assertThat(parsecErrorBody.getDetail().get(0).getInvalidValue()).isEqualTo("bar");
  }

  @Test
  public void testBuild_With_Msg_And_Value_Should_Pass() throws Exception {
    ResourceException e = new ResourceExceptionBuilder()
        .build(ResourceExceptionType.INTERNAL_SERVER_ERROR, "foo", "bar");
    assertThat(e.getCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    ParsecErrorBody parsecErrorBody = e.getData(ParsecResourceError.class).getError();
    assertThat(parsecErrorBody.getCode()).isEqualTo(50000000);
    assertThat(parsecErrorBody.getMessage()).isEqualTo("Internal server error");
    assertThat(parsecErrorBody.getDetail().get(0).getMessage()).isEqualTo("foo");
    assertThat(parsecErrorBody.getDetail().get(0).getInvalidValue()).isEqualTo("bar");
  }

  @Test
  public void testAddErrors_With_Msg_Should_Pass() throws Exception {
    ResourceException e = new ResourceExceptionBuilder()
        .addErrors("foo")
        .build(ResourceExceptionType.INTERNAL_SERVER_ERROR);
    assertThat(e.getCode()).isEqualTo(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    ParsecErrorBody parsecErrorBody = e.getData(ParsecResourceError.class).getError();
    assertThat(parsecErrorBody.getCode()).isEqualTo(50000000);
    assertThat(parsecErrorBody.getMessage()).isEqualTo("Internal server error");
    assertThat(parsecErrorBody.getDetail().get(0).getMessage()).isEqualTo("foo");
    assertThat(parsecErrorBody.getDetail().get(0).getInvalidValue()).isNull();
  }

  @Test
  public void testHasError_Should_Pass() throws Exception {
    ResourceExceptionBuilder builder = new ResourceExceptionBuilder();
    assertThat(builder.hasError()).isFalse();
    builder = new ResourceExceptionBuilder().addErrors("foo");
    assertThat(builder.hasError()).isTrue();
  }

}
