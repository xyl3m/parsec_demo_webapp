package com.xyl3m.demo.parsec;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.google.common.truth.Truth;
import com.xyl3m.demo.parsec.bo.UserBo;
import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.mock.MockResourceContext;
import com.xyl3m.demo.parsec.parsec_generated.Pagination;
import com.xyl3m.demo.parsec.parsec_generated.ResourceException;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import com.xyl3m.demo.parsec.parsec_generated.User;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DemoHandlerImplTest {

  private static final String DEFAULT_TS = "2020-01-01T00:00:00Z";

  @Mock
  private HttpServletRequest httpServletRequest;
  @Mock
  private HttpServletResponse httpServletResponse;
  @Mock
  private transient UserBo userBo;
  @InjectMocks
  private transient DemoHandlerImpl demoHandler;

  @Test
  public void testCreateUser_happyPath() {
    User user = new User().setId(1).setName("username").setCreatedTs(DEFAULT_TS)
        .setModifiedTs(DEFAULT_TS);
    when(userBo.createUser(any(User.class))).thenReturn(user);
    assertThat(demoHandler.createUser(new MockResourceContext(), new User())).isEqualTo(user);
  }

  @Test
  public void testGetUser_happyPath() {
    User user = new User().setId(1).setName("username").setCreatedTs(DEFAULT_TS)
        .setModifiedTs(DEFAULT_TS);
    when(userBo.getUser(anyInt())).thenReturn(user);
    assertThat(demoHandler.getUser(new MockResourceContext(), anyInt())).isEqualTo(user);
  }

  @Test
  public void testQueryUsers_happyPath() {
    Users users = new Users()
        .setUsers(Collections.singletonList(
            new User().setId(1).setName("username")
                .setCreatedTs(DEFAULT_TS).setModifiedTs(DEFAULT_TS)
        ))
        .setPagination(new Pagination().setResultsTotal(1));

    when(userBo.queryUsers(anyInt(), anyString(), anyInt(), anyInt())).thenReturn(users);
    Truth.assertThat(demoHandler.queryUsers(
        new MockResourceContext(), anyInt(), anyString(), anyInt(), anyInt())
    ).isEqualTo(users);
  }

  @Test
  public void testUpdateUser_happyPath() {
    User user = new User().setId(1).setName("username").setCreatedTs(DEFAULT_TS)
        .setModifiedTs(DEFAULT_TS);
    when(userBo.updateUser(anyInt(), any(User.class))).thenReturn(user);
    assertThat(demoHandler.updateUser(new MockResourceContext(), 1, user))
        .isEqualTo(user);
  }

  @Test
  public void testUpdateUser_userIdNotMatch_shouldThrowResourceException() {
    try {
      demoHandler.updateUser(new MockResourceContext(), null, new User().setId(5566));
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.INVALID_USER_ID);
    }
    try {
      demoHandler.updateUser(new MockResourceContext(), 7788, new User().setId(5566));
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.INVALID_USER_ID);
    }
    try {
      demoHandler.updateUser(new MockResourceContext(), 7788, new User());
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.INVALID_USER_ID);
    }
    try {
      demoHandler.updateUser(new MockResourceContext(), null, new User());
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.INVALID_USER_ID);
    }
  }

  @Test
  public void testDeleteUser_happyPath() {
    doNothing().when(userBo).deleteUser(anyInt());
    demoHandler.deleteUser(new MockResourceContext().setResponse(httpServletResponse), anyInt());
  }

  @Test
  public void testNewResourceContext() {
    Truth.assertThat(demoHandler.newResourceContext(httpServletRequest, httpServletResponse)).isNotNull();
  }
}
