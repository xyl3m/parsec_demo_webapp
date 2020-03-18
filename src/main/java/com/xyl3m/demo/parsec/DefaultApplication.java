package com.xyl3m.demo.parsec;

import com.google.inject.Injector;
import com.xyl3m.demo.parsec.module.InjectorProvider;
import com.xyl3m.demo.parsec.parsec_generated.DemoHandler;
import com.xyl3m.demo.parsec.parsec_generated.ParsecApplication;
import com.yahoo.parsec.config.ParsecConfig;
import com.yahoo.parsec.config.ParsecConfigFactory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

/**
 * This is the entry point of Jersey, which is defined in web.xml.
 */
@SuppressWarnings("unused")
public class DefaultApplication extends ParsecApplication {

  private static final int HIGH_PRIORITY_BINDING_RANK = 100;

  /**
   * Default constructor.
   */
  @SuppressWarnings("ConstructorInvokesOverridable")
  public DefaultApplication() {
    // Parsec default bindings and registers
    super();

    register(new AbstractBinder() {
      @Override
      protected void configure() {
        // Add additional binding here
        // bind(<implementation>.class).to(<interface>.class)
        final ParsecConfig config = ParsecConfigFactory.load();
        final Injector injector = new InjectorProvider().getInjector(config);

        final DemoHandler demoHandler = new DemoHandlerImpl();
        injector.injectMembers(demoHandler);
        bind(demoHandler).to(DemoHandler.class).ranked(HIGH_PRIORITY_BINDING_RANK);
      }
    });

    // Add additional register here
    // register(<resource>.class)
  }

}
