package integration;

import static integration.World.GSON;

import com.xyl3m.demo.parsec.parsec_generated.User;
import com.yahoo.parsec.clients.ParsecAsyncHttpClient;
import com.yahoo.parsec.clients.ParsecAsyncHttpRequest;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.validation.constraints.NotNull;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;

public class DemoWsClient {

  private static final String RESOURCE_USERS = "users";

  private static final int MAX_RETRIES = 5;
  private static final String SERVICE_URL;
  private static ParsecAsyncHttpClient client;

  static {
    SERVICE_URL = System.getProperty("serviceUrl");
    if (StringUtils.isEmpty(SERVICE_URL)) {
      throw new RuntimeException("Please set system property serviceUrl (-DserviceUrl) first");
    } else {
      client = new ParsecAsyncHttpClient.Builder().setAcceptAnyCertificate(true).build();
    }
  }

  /**
   * GET users.
   *
   * @param userId user ID
   * @return CompletableFuture of Response
   * @throws ExecutionException when task aborted by throwing an exception.
   */
  public CompletableFuture<Response> getUser(final Integer userId) throws ExecutionException {
    UriBuilder builder =
        UriBuilder.fromUri(SERVICE_URL).path(RESOURCE_USERS).path(userId.toString());
    return client.criticalExecute(buildRequest(HttpMethod.GET, builder.build(), null));
  }

  /**
   * QUERY users.
   *
   * @param userId   user ID
   * @param username username
   * @param offset   pagination offset
   * @param limit    pagination limit
   * @return CompletableFuture of Response
   * @throws ExecutionException when task aborted by throwing an exception.
   */
  public CompletableFuture<Response> getUsers(final Integer userId,
      final String username,
      final Integer offset,
      final Integer limit) throws ExecutionException {
    UriBuilder builder = UriBuilder.fromUri(SERVICE_URL).path(RESOURCE_USERS);
    if (null != userId) {
      builder.queryParam("id", userId);
    }
    if (null != username) {
      builder.queryParam("name", username);
    }
    if (null != offset) {
      builder.queryParam("offset", offset);
    }
    if (null != limit) {
      builder.queryParam("limit", limit);
    }
    return client.criticalExecute(buildRequest(HttpMethod.GET, builder.build(), null));
  }

  /**
   * POST users.
   *
   * @param user User instance
   * @return CompletableFuture of Response
   * @throws ExecutionException when task aborted by throwing an exception.
   */
  public CompletableFuture<Response> postUser(final User user) throws ExecutionException {
    UriBuilder builder = UriBuilder.fromUri(SERVICE_URL).path(RESOURCE_USERS);
    return client
        .criticalExecute(buildRequest(HttpMethod.POST, builder.build(), GSON.toJson(user)));
  }

  /**
   * PUT users.
   *
   * @param userId user ID
   * @param user   User instance
   * @return CompletableFuture of Response
   * @throws ExecutionException when task aborted by throwing an exception.
   */
  public CompletableFuture<Response> putUser(final Integer userId,
      final User user) throws ExecutionException {
    UriBuilder builder =
        UriBuilder.fromUri(SERVICE_URL).path(RESOURCE_USERS).path(userId.toString());
    return client.criticalExecute(buildRequest(HttpMethod.PUT, builder.build(), GSON.toJson(user)));
  }

  /**
   * DELETE users.
   *
   * @param userId user ID
   * @return CompletableFuture of Response
   * @throws ExecutionException when task aborted by throwing an exception.
   */
  public CompletableFuture<Response> deleteUser(final Integer userId) throws ExecutionException {
    UriBuilder builder =
        UriBuilder.fromUri(SERVICE_URL).path(RESOURCE_USERS).path(userId.toString());
    return client.criticalExecute(buildRequest(HttpMethod.DELETE, builder.build(), null));
  }

  /**
   * Build ParsecAsyncHttpRequest with mathod, URI and payload.
   *
   * @param method  HTTP method string
   * @param uri     URI instance
   * @param payload request payload
   * @return ParsecAsyncHttpRequest
   */
  private ParsecAsyncHttpRequest buildRequest(@NotNull final String method,
      @NotNull final URI uri,
      final String payload) {
    return new ParsecAsyncHttpRequest.Builder()
        .setMethod(method)
        .setUri(uri)
        .setBody(payload)
        .setBodyEncoding(StandardCharsets.UTF_8.name())
        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
        .addRetryStatusCode(HttpStatus.SC_REQUEST_TIMEOUT)
        .addRetryStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR)
        .addRetryStatusCode(HttpStatus.SC_BAD_GATEWAY)
        .setMaxRetries(MAX_RETRIES).build();
  }

}
