package com.xyl3m.demo.parsec.mock;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public abstract class MockPreparedStatement implements PreparedStatement {

  private int intVal;
  private Timestamp timestampVal;

  @Override
  public void setInt(int parameterIndex, int x) throws SQLException {
    this.intVal = x;
  }

  public int getInt() {
    return intVal;
  }

  @Override
  public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
    this.timestampVal = x;
  }

  public Timestamp getTimestamp() {
    return timestampVal;
  }
}
