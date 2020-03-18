package com.xyl3m.demo.parsec;

import static org.mockito.Mockito.mock;
import static org.powermock.api.support.membermodification.MemberMatcher.constructor;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

import com.xyl3m.demo.parsec.parsec_generated.ParsecExceptionMapper;
import javax.ws.rs.core.Configuration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DefaultExceptionMapper.class)
public class DefaultExceptionMapperTest {

  @Test
  public void testCode() {
    suppress(constructor(ParsecExceptionMapper.class));
    new DefaultExceptionMapper(mock(Configuration.class));
  }
}
