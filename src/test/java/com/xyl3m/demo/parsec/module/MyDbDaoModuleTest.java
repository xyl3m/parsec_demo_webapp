package com.xyl3m.demo.parsec.module;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.mock;

import com.xyl3m.demo.parsec.dao.mybatis.DbProperties;
import org.junit.Test;

public class MyDbDaoModuleTest {

  @Test
  public void testCode() {
    DbProperties prop = mock(DbProperties.class);
    MyDbDaoModule module = new MyDbDaoModule(prop);
    assertThat(module).isNotNull();
    module.initialize();
  }
}
