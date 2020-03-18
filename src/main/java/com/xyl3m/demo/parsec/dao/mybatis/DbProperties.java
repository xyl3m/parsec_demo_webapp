package com.xyl3m.demo.parsec.dao.mybatis;

import com.yahoo.parsec.config.ParsecConfig;
import java.util.Properties;

public class DbProperties {

  private static final String DRIVER_NAME = "driver";
  private static final String DB_SCHEME = "scheme";
  private static final String DB_HOST = "host";
  private static final String DB_PORT = "port";
  private static final String DB_NAME = "db";
  private static final String DB_CONN_ATTR = "connAttr";
  private static final String DB_USER = "user";
  private static final String DB_KEYPASS = "pwd";
  private static final String DB_READ_ONLY = "readOnly";
  private transient Properties props;

  /**
   * Constructor.
   *
   * @param config ParsecConfig instance
   */
  public DbProperties(ParsecConfig config) {
    props = new Properties();
    props.put("driver", config.getString(DRIVER_NAME));
    props.put("username", config.getString(DB_USER));

    final String scheme = config.getString(DB_SCHEME);
    String url;
    if ("jdbc:sqlserver".equals(scheme)) {
      url = String.format(
          "%s://%s:%s;DatabaseName=%s%s",
          config.getString(DB_SCHEME),
          config.getString(DB_HOST),
          config.getString(DB_PORT),
          config.getString(DB_NAME),
          config.getString(DB_CONN_ATTR)
      );
    } else {
      // performance related settings https://github.com/brettwooldridge/HikariCP/wiki/MySQL-Configuration
      StringBuilder connAttr = new StringBuilder(config.getString(DB_CONN_ATTR));
      if (config.getBoolean("useSSL")) {
        connAttr = new StringBuilder(connAttr).append("&useSSL=true&enabledTLSProtocols=TLSv1.2")
            .append("&verifyServerCertificate=")
            .append(config.getString("verifyServerCertificate"))
            .append("&clientCertificateKeyStoreUrl=")
            .append(config.getString("clientCertificateKeyStoreUrl"))
            .append("&clientCertificateKeyStorePassword=")
            .append(config.getString("clientCertificateKeyStorePasswordKey"))
            .append("&clientCertificateKeyStoreType=")
            .append(config.getString("clientCertificateKeyStoreType"));
      } else {
        connAttr = new StringBuilder(connAttr).append("&useSSL=false");
      }
      url = String.format(
          "%s://%s:%s/%s%s",
          config.getString(DB_SCHEME),
          config.getString(DB_HOST),
          config.getString(DB_PORT),
          config.getString(DB_NAME),
          connAttr.toString()
      );
    }
    props.put("url", url);
    props.put("password", config.getString(DB_KEYPASS));
    props.put("env.encoding", "UTF8");
    props.put("maxPoolSize", config.getString("maxPoolSize"));
    props.put("maxLifetime", config.getString("maxLifetime"));
    props.put(DB_READ_ONLY, config.getString(DB_READ_ONLY));
  }

  public Properties getProperties() {
    return props;
  }

}
