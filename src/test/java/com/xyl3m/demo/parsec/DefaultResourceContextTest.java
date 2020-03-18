package com.xyl3m.demo.parsec;

import static com.google.common.truth.Truth.assertThat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultResourceContextTest {

  @Mock
  private HttpServletRequest httpServletRequest;
  @Mock
  private HttpServletResponse httpServletResponse;

  @Test
  public void testCode() {
    DefaultResourceContext context =
        new DefaultResourceContext(httpServletRequest, httpServletResponse);
    assertThat(context.request()).isEqualTo(httpServletRequest);
    assertThat(context.response()).isEqualTo(httpServletResponse);
    context.authenticate();
    context.authorize(null, null, null);
  }

}
