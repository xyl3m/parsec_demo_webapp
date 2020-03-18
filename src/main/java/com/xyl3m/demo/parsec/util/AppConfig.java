package com.xyl3m.demo.parsec.util;

import com.google.gson.Gson;
import com.yahoo.parsec.config.ParsecConfig;
import com.yahoo.parsec.config.ParsecConfigFactory;
import java.util.TimeZone;

public final class AppConfig {

  public static final Gson GSON = new Gson();
  private static final ParsecConfig CONFIG = ParsecConfigFactory.load();
  private static final TimeZone APP_TIMEZONE = TimeZone.getTimeZone("Asia/Taipei");

  private AppConfig() {
  }

  public static TimeZone getAppTimeZone() {
    return APP_TIMEZONE;
  }

  public static String getErrorMessage(int code) {
    return CONFIG.getString(String.format("error.%d", code));
  }

}
