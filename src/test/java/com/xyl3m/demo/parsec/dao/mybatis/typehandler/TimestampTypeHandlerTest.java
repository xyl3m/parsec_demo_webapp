package com.xyl3m.demo.parsec.dao.mybatis.typehandler;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import com.xyl3m.demo.parsec.mock.MockPreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TimestampTypeHandlerTest {

  @InjectMocks
  private TimestampTypeHandler handler;

  @Test
  public void testCode() throws SQLException {
    ResultSet rs = mock(ResultSet.class);
    CallableStatement cs = mock(CallableStatement.class);

    MockPreparedStatement mockPreparedStatement =
        spy(MockPreparedStatement.class);
    handler.setNonNullParameter(mockPreparedStatement, 0, null, null);
    handler.setNonNullParameter(mockPreparedStatement, 0, "2020-01-01T01:02:03+08:00", null);
    assertThat(mockPreparedStatement.getInt()).isEqualTo(0);

    try {
      handler.setNonNullParameter(mockPreparedStatement, 0, "invalid time format", null);
      fail();
    } catch (SQLException e) {
      assertThat(e.toString()).contains("java.sql.SQLException: Invalid time format");
    }

    Timestamp timestamp = new Timestamp(1577811723L);

    lenient().when(rs.getTimestamp(anyString())).thenReturn(timestamp);
    assertThat(handler.getNullableResult(rs, anyString())).isEqualTo("2020-01-01T01:02:03+08:00");
    when(rs.getTimestamp(anyString())).thenReturn(null);
    assertThat(handler.getNullableResult(rs, anyString())).isNull();

    lenient().when(rs.getTimestamp(anyInt())).thenReturn(timestamp);
    assertThat(handler.getNullableResult(rs, anyInt())).isEqualTo("2020-01-01T01:02:03+08:00");
    when(rs.getTimestamp(anyInt())).thenReturn(null);
    assertThat(handler.getNullableResult(rs, anyInt())).isNull();

    lenient().when(cs.getTimestamp(anyInt())).thenReturn(timestamp);
    assertThat(handler.getNullableResult(cs, anyInt())).isEqualTo("2020-01-01T01:02:03+08:00");
    when(cs.getTimestamp(anyInt())).thenReturn(null);
    assertThat(handler.getNullableResult(cs, anyInt())).isNull();
  }
}
