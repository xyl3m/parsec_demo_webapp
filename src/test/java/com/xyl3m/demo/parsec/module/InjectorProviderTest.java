package com.xyl3m.demo.parsec.module;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.yahoo.parsec.config.ParsecConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Guice.class)
public class InjectorProviderTest {

  @InjectMocks
  private InjectorProvider provider;

  @Before
  public void setUp() {
    PowerMockito.mockStatic(Guice.class);
  }

  @Test
  public void testCode() {
    ParsecConfig config = mock(ParsecConfig.class);
    Injector injector = mock(Injector.class);

    when(config.getConfig(anyString())).thenReturn(config);
    when(config.getString(anyString())).thenReturn("test");
    when(Guice.createInjector(any(Module[].class))).thenReturn(injector);
    assertThat(provider.getInjector(config)).isNull();
  }
}
