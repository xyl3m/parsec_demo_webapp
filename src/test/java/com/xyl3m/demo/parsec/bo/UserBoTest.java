package com.xyl3m.demo.parsec.bo;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.ValidationUtil;
import com.xyl3m.demo.parsec.dao.MyDbMapper;
import com.xyl3m.demo.parsec.parsec_generated.Pagination;
import com.xyl3m.demo.parsec.parsec_generated.ResourceException;
import com.xyl3m.demo.parsec.parsec_generated.User;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import java.util.Collections;
import org.apache.ibatis.exceptions.PersistenceException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserBoTest {

  private static final User EXPECTED_USER = new User().setId(5566).setName("user")
      .setCreatedTs("2020-01-01T00:00:00Z").setModifiedTs("2020-01-01T00:00:00Z");

  @Mock
  private MyDbMapper mapper;
  @InjectMocks
  private UserBo userBo;

  @Test
  public void testGetUser_happyPath() {
    when(mapper.getUser(anyInt())).thenReturn(EXPECTED_USER);
    assertThat(userBo.getUser(anyInt())).isEqualTo(EXPECTED_USER);
  }

  @Test
  public void testGetUser_userNotFound_shouldThrowResourceException() {
    when(mapper.getUser(anyInt())).thenReturn(null);
    try {
      userBo.getUser(anyInt());
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.USER_NOT_FOUND);
    }
  }

  @Test
  public void testGetUser_accessDbFailed_shouldThrowResourceException() {
    when(mapper.getUser(anyInt())).thenThrow(new PersistenceException());
    try {
      userBo.getUser(anyInt());
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  @Test
  public void testQueryUsers_happyPath() {
    when(mapper.queryUsers(anyInt(), anyString(), anyInt(), anyInt())).thenReturn(
        new Users()
            .setPagination(new Pagination().setResultsTotal(0))
            .setUsers(Collections.EMPTY_LIST)
    );
    Users actual = userBo.queryUsers(anyInt(), anyString(), anyInt(), anyInt());
    assertThat(actual.getPagination().getResultsTotal()).isEqualTo(0);
    assertThat(actual.getUsers()).isEmpty();
  }

  @Test
  public void testQueryUsers_accessDbFailed_shouldThrowResourceException() {
    when(mapper.queryUsers(anyInt(), anyString(), anyInt(), anyInt()))
        .thenThrow(new PersistenceException());
    try {
      userBo.queryUsers(anyInt(), anyString(), anyInt(), anyInt());
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  @Test
  public void testCreateUser_happyPath() {
    when(mapper.getUser(5566)).thenReturn(EXPECTED_USER);
    when(mapper.createUser(any(User.class))).thenReturn(1);
    assertThat(userBo.createUser(new User().setId(5566))).isEqualTo(EXPECTED_USER);
  }

  @Test
  public void testCreateUser_accessDbFailed_shouldThrowResourceException() {
    when(mapper.createUser(any(User.class))).thenThrow(new PersistenceException());
    try {
      userBo.createUser(new User().setId(5566));
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  @Test
  public void testUpdateUser_happyPath() {
    when(mapper.updateUser(anyInt(), any(User.class))).thenReturn(1);
    when(mapper.getUser(anyInt())).thenReturn(EXPECTED_USER);
    assertThat(userBo.updateUser(5566, new User())).isEqualTo(EXPECTED_USER);
  }

  @Test
  public void testUpdateUser_userNotExisted_shouldThrowResourceException() {
    when(mapper.updateUser(anyInt(), any(User.class))).thenReturn(0);
    try {
      userBo.updateUser(5566, EXPECTED_USER);
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.USER_NOT_FOUND);
    }
  }

  @Test
  public void testUpdateUser_accessDbFailed_shouldThrowResourceException() {
    when(mapper.updateUser(anyInt(), any(User.class))).thenThrow(new PersistenceException());
    try {
      userBo.updateUser(5566, EXPECTED_USER);
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  @Test
  public void testDeleteUser_happyPath() {
    when(mapper.deleteUser(anyInt())).thenReturn(1);
    userBo.deleteUser(5566);
  }

  @Test
  public void testDeleteUser_accessDbFailed_shouldThrowResourceException() {
    when(mapper.deleteUser(anyInt())).thenThrow(new PersistenceException());
    try {
      userBo.deleteUser(5566);
      fail();
    } catch (ResourceException e) {
      ValidationUtil.validateResourceException(e, ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }
}
