package com.sweethome.accountbook.common.handler.db;

import com.sweethome.accountbook.domain.LogState;
import com.sweethome.accountbook.domain.TransactionType;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogStateHandler extends BaseTypeHandler<LogState> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, LogState parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public LogState getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : LogState.fromValue(code);
    }

    @Override
    public LogState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : LogState.fromValue(code);
    }

    @Override
    public LogState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : LogState.fromValue(code);
    }
}
