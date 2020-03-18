package com.xyl3m.demo.parsec;

import com.xyl3m.demo.parsec.parsec_generated.ParsecWebListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 * Default Web listener, replace web.xml.
 */
@WebListener
public class DefaultWebListener extends ParsecWebListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    // Parsec default servlet registrations
    super.contextInitialized(sce);

    // Add additional servlet filter here
  }

}

