package com.xyl3m.demo.parsec.util;

import static com.google.common.truth.Truth.assertThat;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import org.junit.Test;

public class AppConfigTest {

  @Test
  public void testPrivateConstructor()
      throws NoSuchMethodException, IllegalAccessException, InvocationTargetException,
      InstantiationException {
    Constructor<AppConfig> constructor = AppConfig.class.getDeclaredConstructor();
    assertThat(Modifier.isPrivate(constructor.getModifiers())).isTrue();
    constructor.setAccessible(true);
    constructor.newInstance();
  }

  @Test
  public void testGetAppTimeZone() {
    assertThat(AppConfig.getAppTimeZone().getID()).isEqualTo("Asia/Taipei");
  }

  @Test
  public void testGetErrorMessage() {
    assertThat(AppConfig.getErrorMessage(50000000)).isEqualTo("Internal server error");
  }

}
