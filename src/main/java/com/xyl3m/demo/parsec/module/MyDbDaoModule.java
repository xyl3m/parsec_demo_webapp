package com.xyl3m.demo.parsec.module;

import com.xyl3m.demo.parsec.dao.mybatis.DbProperties;
import org.mybatis.guice.XMLMyBatisModule;

public class MyDbDaoModule extends XMLMyBatisModule {

  private static final String MY_BATIS_CONF = "com/xyl3m/demo/parsec/dao/mybatis-mydb-config.xml";

  private transient DbProperties dbProperties;

  public MyDbDaoModule(DbProperties dbProperties) {
    this.dbProperties = dbProperties;
  }

  @Override
  protected void initialize() {
    setClassPathResource(MY_BATIS_CONF);
    addProperties(dbProperties.getProperties());
  }

}
