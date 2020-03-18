package integration;

import static com.google.common.truth.Truth.assertWithMessage;
import static integration.World.GSON;

import com.xyl3m.demo.parsec.parsec_generated.User;
import com.xyl3m.demo.parsec.parsec_generated.Users;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.util.Map;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserStepDefs extends BaseStepDefs {

  private static final Logger LOG = LoggerFactory.getLogger(UserStepDefs.class);
  private static final String KEYWORD_USER_ID = "@userId";
  private static final String KEYWORD_USER_NAME = "@username";

  private transient Map<ContextKey, Object> context;
  private transient DemoWsClient client;

  public UserStepDefs(final World world) {
    context = world.context;
    client = World.CLIENT;
  }

  @When("I query Users with ID {string} and name {string}")
  public void iQueryUsersWithIDAndName(String userIdStr, String username) throws Throwable {
    userIdStr = parseUsersKeyword(userIdStr);
    Integer userId = StringUtils.isEmpty(userIdStr) ? null : Integer.parseInt(userIdStr);
    context.put(ContextKey.USER_ID, userId);

    username = parseUsersKeyword(username);
    context.put(ContextKey.USER_NAME, username);

    Response response = client.getUsers(userId, username, 0, 0).get();
    context.put(ContextKey.RESPONSE, response);
    if (response.hasEntity()) {
      LOG.debug("queryUsers.response: {}", response.getEntity().toString());
    }
    if (HttpStatus.SC_OK == response.getStatus()) {
      context.put(ContextKey.USERS, GSON.fromJson(response.getEntity().toString(), Users.class));
    }
  }

  @When("I get User by ID {string}")
  public void iGetUserByID(String userIdStr) throws Throwable {
    userIdStr = parseUsersKeyword(userIdStr);
    Integer userId = Integer.parseInt(userIdStr); // must be not null
    context.put(ContextKey.USER_ID, userId);

    Response response = client.getUser(userId).get();
    context.put(ContextKey.RESPONSE, response);
    if (response.hasEntity()) {
      LOG.debug("getUser.response: {}", response.getEntity().toString());
    }
    if (HttpStatus.SC_OK == response.getStatus()) {
      context.put(ContextKey.USER, GSON.fromJson(response.getEntity().toString(), User.class));
    }
  }

  @When("I create User with name {string}")
  public void iCreateUserWithName(String username) throws Throwable {
    username = parseUsersKeyword(username);
    context.put(ContextKey.USER_NAME, username);

    Response response = client.postUser(new User().setName(username)).get();
    context.put(ContextKey.RESPONSE, response);
    if (response.hasEntity()) {
      LOG.debug("createUser.response: {}", response.getEntity().toString());
    }
    if (HttpStatus.SC_CREATED == response.getStatus()) {
      context.put(ContextKey.USER, GSON.fromJson(response.getEntity().toString(), User.class));
    }
  }

  @When("I update User with name {string} by ID {string}")
  public void iUpdateUserWithNameByID(String username, String userIdStr) throws Throwable {
    username = parseUsersKeyword(username);
    context.put(ContextKey.USER_NAME, username);

    userIdStr = parseUsersKeyword(userIdStr);
    Integer userId = Integer.parseInt(userIdStr); // must be not null
    context.put(ContextKey.USER_ID, userId);

    User oldUser = (User) context.get(ContextKey.USER);

    Response response = client.putUser(userId, oldUser.setName(username)).get();
    context.put(ContextKey.RESPONSE, response);
    if (response.hasEntity()) {
      LOG.debug("updateUser.response: {}", response.getEntity().toString());
    }
    if (HttpStatus.SC_OK == response.getStatus()) {
      context.put(ContextKey.USER, GSON.fromJson(response.getEntity().toString(), User.class));
    }
  }

  @When("I delete User by ID {string}")
  public void iDeleteUserByID(String userIdStr) throws Throwable {
    userIdStr = parseUsersKeyword(userIdStr);
    Integer userId = Integer.parseInt(userIdStr); // must be not null
    context.put(ContextKey.USER_ID, userId);

    Response response = client.deleteUser(userId).get();
    context.put(ContextKey.RESPONSE, response);
    if (response.hasEntity()) {
      LOG.debug("deleteUser.response: {}", response.getEntity().toString());
    }
  }

  @And("the response User should be the same as provided")
  public void theResponseUserShouldBeTheSameAsProvided() {
    User user = (User) context.get(ContextKey.USER);
    Integer userId = (Integer) context.get(ContextKey.USER_ID);
    String username = (String) context.get(ContextKey.USER_NAME);

    if (null != userId) {
      assertWithMessage("Assert user ID %s should be equal to %s failed", user.getId(), userId)
          .that(user.getId()).isEqualTo(userId);
    }

    if (null != username) {
      assertWithMessage("Assert username %s should be equal to %s failed", user.getName(), username)
          .that(user.getName()).isEqualTo(username);
    }
  }

  @Then("the response status code should be <{int}>")
  public void theResponseStatusCodeShouldBe(int expectedStatusCode) throws Throwable {
    theResponseStatusCodeShouldBeAndDetailErrorCodeShouldBe(expectedStatusCode, null);
  }

  @Then("the response status code should be <{int}> and detail error code should be <{int}>")
  public void theResponseStatusCodeShouldBeAndDetailErrorCodeShouldBe(
      int expectedStatusCode,
      Integer expectedDetailErrorCode) throws Throwable {
    Response response = (Response) context.get(ContextKey.RESPONSE);
    String respBody = response.hasEntity() ? response.getEntity().toString() : null;
    assertWithMessage(
        "Assert http status code %s should be equal to %s failed, raw response: %s",
        response.getStatus(), expectedStatusCode, respBody
    ).that(response.getStatus()).isEqualTo(expectedStatusCode);

    if (expectedDetailErrorCode != null) {
      int actual = extractDetailErrorCode(respBody);
      assertWithMessage(
          "Assert detail error code %s should be equal to %s failed, raw response: %s",
          actual, expectedDetailErrorCode, respBody
      ).that(actual).isEqualTo(expectedDetailErrorCode);
    }
  }

  @And("the response should be contain results total of pagination <{int}>")
  public void theResponseShouldBeContainResultsTotalOfPagination(int expectedResultsTotal)
      throws Throwable {
    Response response = (Response) context.get(ContextKey.RESPONSE);
    int actual = extractPaginationResultsTotal(response.getEntity().toString());
    assertWithMessage(
        "Assert results total %s should be equal to %s failed, raw response: %s",
        actual, expectedResultsTotal, response.getEntity().toString()
    ).that(actual).isEqualTo(expectedResultsTotal);
  }

  private String parseUsersKeyword(final String word) {
    switch (word) {
      case KEYWORD_USER_ID:
        return Integer.toString(((User) context.get(ContextKey.USER)).getId());
      case KEYWORD_USER_NAME:
        return ((User) context.get(ContextKey.USER)).getName();
      default:
        return word;
    }
  }

}
