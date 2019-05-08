package com.cit.micro.data.entity;

import com.cit.micro.data.LogData;
import com.cit.micro.data.client.GrpcLoggerClient;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRowMapper implements RowMapper<LogData> {
    private GrpcLoggerClient logger = new GrpcLoggerClient();

    @Override
    public LogData mapRow(ResultSet row, int rowNum) throws SQLException {

        return LogData.newBuilder()
                .setId(safeGetint(row,"id"))
                .setText(safeGetString(row,"text"))
                .setUid(safeGetString(row,"uid"))
                .setChannel(safeGetString(row,"channel"))
                .build();
    }

    private String safeGetString(ResultSet row, String header) {
        String result;
        try{
            result = row.getString(header);
        }catch (NullPointerException | SQLException e)
        {
            result = "";
        }
        return result;
    }

    private int safeGetint(ResultSet row, String header) {
        int result;
        try{
            result = row.getInt(header);
        }catch (NullPointerException | SQLException e)
        {
            result =0;
        }
        return result;
    }
}
