package com.cit.micro.data.entity;

import com.cit.micro.data.LogData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRowMapper implements RowMapper<LogData> {
    @Override
    //todo fix this...
    public LogData mapRow(ResultSet row, int rowNum) throws SQLException {
        return LogData.newBuilder().setId(row.getInt("id")).setText(row.getString("text")).setUid(row.getString("uid")).build();
    }
}
