package integration;

import static integration.World.MAPPER;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;

public class BaseStepDefs {

  /**
   * Extract detail error code from response body.
   *
   * @param respBody response body
   * @return detail error code
   * @throws IOException when parsing response body failed
   */
  protected int extractDetailErrorCode(final String respBody) throws IOException {
    final JsonNode jsonNode = MAPPER.readTree(respBody);
    return jsonNode.get("error").get("code").asInt();
  }

  /**
   * Extract results total of pagination from response body.
   *
   * @param respBody response body
   * @return results total
   * @throws IOException when parsing response body failed
   */
  protected int extractPaginationResultsTotal(final String respBody) throws IOException {
    final JsonNode jsonNode = MAPPER.readTree(respBody);
    return jsonNode.get("pagination").get("resultsTotal").asInt();
  }

}
