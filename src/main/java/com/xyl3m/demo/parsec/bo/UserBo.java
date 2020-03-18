package com.xyl3m.demo.parsec.bo;

import com.google.inject.Inject;
import com.xyl3m.demo.parsec.dao.MyDbMapper;
import com.xyl3m.demo.parsec.enumtype.ResourceExceptionType;
import com.xyl3m.demo.parsec.module.annotation.Writer;
import com.xyl3m.demo.parsec.parsec_generated.User;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import com.xyl3m.demo.parsec.util.ResourceExceptionBuilder;
import java.util.Optional;
import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.guice.transactional.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserBo {

  private static final Logger LOG = LoggerFactory.getLogger(UserBo.class);

  @Inject
  @Writer
  private transient MyDbMapper mapper;

  /**
   * Get user by ID.
   *
   * @param userId user ID
   * @return User
   */
  public User getUser(int userId) {
    try {
      return Optional.ofNullable(mapper.getUser(userId))
          .orElseThrow(
              () -> new ResourceExceptionBuilder().build(ResourceExceptionType.USER_NOT_FOUND));
    } catch (PersistenceException e) {
      LOG.error(e.getMessage(), e.getCause());
      throw new ResourceExceptionBuilder()
          .build(ResourceExceptionType.ACCESS_MYDB_FAILED, e.getMessage());
    }
  }

  /**
   * Query users.
   *
   * @param userId   user ID
   * @param username user name
   * @return Users
   */
  public Users queryUsers(Integer userId, String username, int offset, int limit) {
    try {
      return mapper.queryUsers(userId, username, offset, limit);
    } catch (PersistenceException e) {
      LOG.error(e.getMessage(), e.getCause());
      throw new ResourceExceptionBuilder().build(ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  /**
   * Create user.
   *
   * @param user User instance
   * @return User
   */
  @Transactional
  public User createUser(User user) {
    try {
      mapper.createUser(user);
    } catch (PersistenceException e) {
      LOG.error(e.getMessage(), e.getCause());
      throw new ResourceExceptionBuilder().build(ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
    return getUser(user.getId());
  }

  /**
   * Update user by ID.
   *
   * @param userId user ID
   * @param user   User instance
   * @return User
   */
  @Transactional
  public User updateUser(int userId, User user) {
    try {
      if (0 == mapper.updateUser(userId, user)) {
        throw new ResourceExceptionBuilder().build(ResourceExceptionType.USER_NOT_FOUND);
      }
      return getUser(userId);
    } catch (PersistenceException e) {
      LOG.error(e.getMessage(), e.getCause());
      throw new ResourceExceptionBuilder().build(ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

  /**
   * Delete user by ID.
   *
   * @param userId user ID
   */
  @Transactional
  public void deleteUser(int userId) {
    try {
      mapper.deleteUser(userId);
    } catch (PersistenceException e) {
      LOG.error(e.getMessage(), e.getCause());
      throw new ResourceExceptionBuilder().build(ResourceExceptionType.ACCESS_MYDB_FAILED);
    }
  }

}
