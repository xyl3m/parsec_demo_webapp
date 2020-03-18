package com.xyl3m.demo.parsec.dao.mybatis.typehandler;

import com.github.longhorn.fastball.time.Clock;
import com.xyl3m.demo.parsec.util.AppConfig;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class TimestampTypeHandler extends BaseTypeHandler<String> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType)
      throws SQLException {
    if (null != parameter) {
      try {
        ps.setTimestamp(i, new Timestamp(Clock.fromIso8601(parameter).toEpochMilli()));
      } catch (DateTimeParseException | ParseException e) {
        throw new SQLException("Invalid time format");
      }
    }
  }

  @Override
  public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
    return parse(rs.getTimestamp(columnName));
  }

  @Override
  public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
    return parse(rs.getTimestamp(columnIndex));
  }

  @Override
  public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
    return parse(cs.getTimestamp(columnIndex));
  }

  private String parse(Timestamp timestamp) {
    if (null == timestamp) {
      return null;
    } else {
      return Clock.fromUnixTs(timestamp.getTime()).getIso8601(AppConfig.getAppTimeZone());
    }
  }

}
