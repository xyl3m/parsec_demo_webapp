package com.xyl3m.demo.parsec;

import com.xyl3m.demo.parsec.parsec_generated.ParsecExceptionMapper;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Context;

/**
 * Default Exception Mapper.
 */
public class DefaultExceptionMapper extends ParsecExceptionMapper {

  /**
   * Default constructor.
   *
   * @param config the config injected by Jersey by default.
   */
  public DefaultExceptionMapper(@Context Configuration config) {
    super(config);
  }

}
