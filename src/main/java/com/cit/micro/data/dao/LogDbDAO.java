package com.cit.micro.data.dao;

import com.cit.micro.data.Channel;
import com.cit.micro.data.LogData;
import com.cit.micro.data.client.GrpcLoggerClient;
import com.cit.micro.data.entity.DataRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Transactional
@Repository
public class LogDbDAO implements ILogDbDAO {

    private JdbcTemplate jdbcTemplate;
    private GrpcLoggerClient logger = new GrpcLoggerClient();

    @Autowired
    public LogDbDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LogData> getAllLogData() {
        String sql = "SELECT uid, text, id FROM stored_values";
        RowMapper<LogData> rowMapper = new DataRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public LogData getLogDataById(int id) {
        String sql = "SELECT id, text, uid FROM stored_values WHERE id = ?";
        RowMapper<LogData> rowMapper = new BeanPropertyRowMapper<>(LogData.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public int addLogData(LogData logData) {
        String sql = "INSERT INTO stored_values (text, uid, channel) values (?, ?,?)";
        jdbcTemplate.update(sql, logData.getText(), logData.getUid(), logData.getChannel());

        //Fetch id
        sql = "SELECT id FROM stored_values WHERE uid = ? AND text LIKE ?";

        int id = 0;
        try{
            id = jdbcTemplate.queryForObject(sql, Integer.class, logData.getUid(), logData.getText());
        }catch (EmptyResultDataAccessException e){
            logger.error("Could not find ID for new entry");
        }catch (IncorrectResultSizeDataAccessException e){
            logger.error("Data added is not unique");
        }

        return id;
    }

    @Override
    public void updateLogData(LogData logData) {
        String sql = "UPDATE stored_values SET text=?, uid=? WHERE id=?";
        jdbcTemplate.update(sql, logData.getText(), logData.getUid(), logData.getId());
    }

    @Override
    public boolean deleteLogData(int id) {
        String sql = "DELETE FROM stored_values WHERE id=?";
        int result = jdbcTemplate.update(sql, id);
        return result > 0;
    }

    @Override
    public boolean logDataExists(String text, String uid) {
        String sql = "SELECT count(*) FROM stored_values WHERE text LIKE ? and uid=?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, text, uid);
        logger.info("Count of exists");
        logger.info(String.valueOf(count));
        return count != 0;
    }

    @Override
    public List<LogData> getAllLogDataByUid(String uid) {
        String sql = "SELECT uid, text, id FROM stored_values WHERE uid=?";
        RowMapper<LogData> rowMapper = new DataRowMapper();
        return this.jdbcTemplate.query(sql, rowMapper, uid);
    }

    public LogData getChannel(String uid){
        String sql = "SELECT channel FROM stored_values WHERE uid=?";
        RowMapper<LogData> rowMapper = new BeanPropertyRowMapper<>(LogData.class);
        return jdbcTemplate.queryForObject(sql, rowMapper, uid);
    }
}