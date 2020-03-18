package com.xyl3m.demo.parsec.module;

import com.google.inject.PrivateModule;
import com.xyl3m.demo.parsec.dao.MyDbMapper;
import com.xyl3m.demo.parsec.dao.mybatis.DbProperties;
import com.yahoo.parsec.config.ParsecConfig;
import java.lang.annotation.Annotation;

public abstract class MyDbModule extends PrivateModule {

  private transient Class<? extends Annotation> annotation;

  private transient MyDbDaoModule myDbDaoModule;

  public MyDbModule(Class<? extends Annotation> annotation, ParsecConfig config) {
    this.annotation = annotation;
    this.myDbDaoModule = new MyDbDaoModule(new DbProperties(config));
  }

  @Override
  protected void configure() {
    install(myDbDaoModule);

    bind(MyDbMapper.class).annotatedWith(annotation).to(MyDbMapper.class);
    expose(MyDbMapper.class).annotatedWith(annotation);
  }

}
