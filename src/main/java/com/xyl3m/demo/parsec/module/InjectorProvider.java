package com.xyl3m.demo.parsec.module;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.xyl3m.demo.parsec.module.annotation.Writer;
import com.yahoo.parsec.config.ParsecConfig;

public class InjectorProvider {

  /**
   * Get the Injector.
   *
   * @param config ParsecConfig instance
   * @return Injector
   */
  public Injector getInjector(ParsecConfig config) {
    final MyDbModule myDbDaoModule = new MyDbModule(Writer.class, config.getConfig("my_db")) {
    };

    return Guice.createInjector(myDbDaoModule);
  }

}
