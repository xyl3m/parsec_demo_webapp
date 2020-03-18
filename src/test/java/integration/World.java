package integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yahoo.parsec.config.ParsecConfig;
import com.yahoo.parsec.config.ParsecConfigFactory;
import java.util.HashMap;
import java.util.Map;

public class World {

  public static final ParsecConfig CONFIG = ParsecConfigFactory.load();
  public static final DemoWsClient CLIENT = new DemoWsClient();
  public static final Gson GSON = new Gson();
  public static final ObjectMapper MAPPER = new ObjectMapper();

  public Map<ContextKey, Object> context = new HashMap<>();

}
