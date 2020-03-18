package com.xyl3m.demo.parsec.dao;

import static com.google.common.truth.Truth.assertThat;

import com.xyl3m.demo.parsec.dao.mybatis.HikariDataSourceFactory;
import org.junit.Test;

public class HikariDataSourceFactoryTest {

  @Test
  public void testCode() {
    HikariDataSourceFactory factory = new HikariDataSourceFactory();
    assertThat(factory.getDataSource()).isNotNull();
  }
}
