package com.xyl3m.demo.parsec.dao;

import com.xyl3m.demo.parsec.parsec_generated.User;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.exceptions.PersistenceException;

@SuppressWarnings("PMD.AvoidDuplicateLiterals")
public interface MyDbMapper {

  /**
   * Get user by ID.
   *
   * @param userId user ID
   * @return User
   * @throws PersistenceException when access database failed
   */
  User getUser(@Param("userId") int userId) throws PersistenceException;

  /**
   * Query users by user name.
   *
   * @param userId   user ID
   * @param username user name
   * @return Users
   * @throws PersistenceException when access database failed
   */
  Users queryUsers(
      @Param("userId") Integer userId, @Param("username") String username,
      @Param("offset") int offset, @Param("limit") int limit
  ) throws PersistenceException;

  /**
   * Create user.
   *
   * @param user User object
   * @return the number of rows affected
   * @throws PersistenceException when access database failed
   */
  int createUser(@Param("user") User user) throws PersistenceException;

  /**
   * Update user by ID.
   *
   * @param userId user ID
   * @param user   User instance
   * @return the number of rows affected
   * @throws PersistenceException when access database failed
   */
  int updateUser(@Param("userId") int userId, @Param("user") User user) throws PersistenceException;

  /**
   * Delete user by ID.
   *
   * @param userId user ID
   * @return the number of rows affected
   * @throws PersistenceException when access database failed
   */
  int deleteUser(@Param("userId") int userId) throws PersistenceException;

}
