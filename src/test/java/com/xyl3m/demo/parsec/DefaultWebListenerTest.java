package com.xyl3m.demo.parsec;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DefaultWebListenerTest {

  @InjectMocks
  private DefaultWebListener listener;

  @Test
  public void testCode() {
    FilterRegistration.Dynamic bouncerFilter = mock(FilterRegistration.Dynamic.class);
    lenient().when(bouncerFilter.setInitParameter(anyString(), any())).thenReturn(true);
    ServletContext context = mock(ServletContext.class);
    ServletContextEvent sce = mock(ServletContextEvent.class);
    when(sce.getServletContext()).thenReturn(context);
    listener.contextInitialized(sce);
  }
}
