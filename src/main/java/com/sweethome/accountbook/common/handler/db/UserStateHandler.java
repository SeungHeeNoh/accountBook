package com.sweethome.accountbook.common.handler.db;

import com.sweethome.accountbook.domain.UserState;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserStateHandler extends BaseTypeHandler<UserState> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UserState parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getValue());
    }

    @Override
    public UserState getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        return rs.wasNull() ? null : UserState.fromValue(code);
    }

    @Override
    public UserState getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        return rs.wasNull() ? null : UserState.fromValue(code);
    }

    @Override
    public UserState getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        return cs.wasNull() ? null : UserState.fromValue(code);
    }
}
