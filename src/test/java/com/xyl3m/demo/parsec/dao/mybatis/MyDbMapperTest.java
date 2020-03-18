package com.xyl3m.demo.parsec.dao.mybatis;

import static com.google.common.truth.Truth.assertThat;

import com.xyl3m.demo.parsec.ValidationUtil;
import com.xyl3m.demo.parsec.dao.MyDbMapper;
import com.xyl3m.demo.parsec.parsec_generated.User;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.containers.PostgreSQLContainer;

public class MyDbMapperTest {

  private static final User EXPECTED_USER1 =
      new User().setId(1).setName("user1").setCreatedTs("2020-01-02T03:04:05+08:00")
          .setModifiedTs("2020-01-02T03:04:05+08:00");
  private static final User EXPECTED_USER2 =
      new User().setId(2).setName("user2").setCreatedTs("2020-01-02T06:07:08+08:00")
          .setModifiedTs("2020-01-02T06:07:08+08:00");

  private static final PostgreSQLContainer<?> DB = new PostgreSQLContainer<>()
      .withDatabaseName("test").withUsername("u").withPassword("p");
  private static SqlSession sqlSession;
  private static MyDbMapper mapper;

  @BeforeClass
  public static void setUpBeforeClass() throws IOException {
    DB.start();

    Reader reader = Resources.getResourceAsReader(
        (MyDbMapperTest.class).getClassLoader(),
        "com/xyl3m/demo/parsec/dao/mybatis-mydb-config.xml"
    );
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader, "unitTest");
    reader.close();

    sqlSession = sqlSessionFactory.openSession();

    ScriptRunner runner = new ScriptRunner(sqlSession.getConnection());
    reader = Resources.getResourceAsReader("com/xyl3m/demo/parsec/dao/MyDb_Create.sql");
    runner.runScript(reader);
    reader.close();

    mapper = sqlSession.getMapper(MyDbMapper.class);
  }

  @AfterClass
  public static void tearDownAfterClass() {
    sqlSession.close();
    DB.stop();
  }

  @Before
  public void setUp() throws IOException, SQLException {
    Connection conn = sqlSession.getConnection();
    ScriptRunner runner = new ScriptRunner(conn);
    Reader reader = Resources.getResourceAsReader("com/xyl3m/demo/parsec/dao/MyDb_Insert.sql");
    runner.runScript(reader);
    reader.close();
  }

  @Test
  public void testGetUser() {
    assertThat(mapper.getUser(1)).isEqualTo(EXPECTED_USER1);
    assertThat(mapper.getUser(2)).isEqualTo(EXPECTED_USER2);
    assertThat(mapper.getUser(5566)).isNull();
  }

  @Test
  public void testQueryUsers() {
    Users users = mapper.queryUsers(null, null, 0, 10);
    assertThat(users.getPagination().getNextOffset()).isNull();
    assertThat(users.getPagination().getResultsTotal()).isEqualTo(2);
    assertThat(users.getUsers().get(0)).isEqualTo(EXPECTED_USER2);
    assertThat(users.getUsers().get(1)).isEqualTo(EXPECTED_USER1);

    users = mapper.queryUsers(null, "handsome boy", 0, 10);
    ValidationUtil.validatePagination(users.getPagination(), 0, null);
    assertThat(users.getUsers()).isNotNull();
    assertThat(users.getUsers()).isEmpty();
  }

  @Test
  public void testCreateUser() {
    User newUser = new User().setName("newUser_" + RandomStringUtils.random(6));
    assertThat(newUser.getId()).isNull();
    assertThat(mapper.createUser(newUser)).isEqualTo(1);
    assertThat(newUser.getId()).isNotNull();
  }

  @Test
  public void testUpdateUser() {
    User newUser = new User().setName("newUser_" + RandomStringUtils.random(6));
    User updatedUser = new User().setName("updatedUser");
    assertThat(mapper.updateUser(5566, updatedUser)).isEqualTo(0);

    assertThat(mapper.createUser(newUser)).isEqualTo(1);
    assertThat(mapper.updateUser(newUser.getId(), updatedUser)).isEqualTo(1);
    assertThat(mapper.getUser(newUser.getId()).getName()).isEqualTo(updatedUser.getName());
  }

  @Test
  public void testDeleteUser() {
    User newUser = new User().setName("newUser_" + RandomStringUtils.random(6));
    assertThat(mapper.createUser(newUser)).isEqualTo(1);
    assertThat(mapper.getUser(newUser.getId())).isNotNull();
    mapper.deleteUser(newUser.getId());
    assertThat(mapper.getUser(newUser.getId())).isNull();
  }
}
