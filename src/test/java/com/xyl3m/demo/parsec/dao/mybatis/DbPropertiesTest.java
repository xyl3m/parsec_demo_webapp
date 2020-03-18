package com.xyl3m.demo.parsec.dao.mybatis;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.yahoo.parsec.config.ParsecConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DbPropertiesTest {

  @Mock
  private ParsecConfig config;

  @Test
  public void testCode_notMsSqlAndNotUseSsl_happyPath() {
    when(config.getString(anyString())).thenReturn("stringVal");
    when(config.getString(eq("scheme"))).thenReturn("jdbc:mysql");
    when(config.getBoolean(anyString())).thenReturn(false);
    DbProperties props = new DbProperties(config);
    assertThat(props).isNotNull();
    assertThat(props.getProperties()).isNotNull();
  }

  @Test
  public void testCode_notMsSqlAndUseSsl_happyPath() {
    when(config.getString(anyString())).thenReturn("stringVal");
    when(config.getString(eq("scheme"))).thenReturn("jdbc:mysql");
    when(config.getBoolean(anyString())).thenReturn(true);
    DbProperties props = new DbProperties(config);
    assertThat(props).isNotNull();
    assertThat(props.getProperties()).isNotNull();
  }

  @Test
  public void testCode_msSql_happyPath() {
    when(config.getString(anyString())).thenReturn("stringVal");
    when(config.getString(eq("scheme"))).thenReturn("jdbc:sqlserver");
    DbProperties props = new DbProperties(config);
    assertThat(props).isNotNull();
    assertThat(props.getProperties()).isNotNull();
  }
}
