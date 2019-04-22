package com.cit.micro.data.dao;

import com.cit.micro.data.LogData;

import java.util.List;

public interface ILogDbDAO {
    List<LogData> getAllLogData();
    List<LogData> getAllLogDataByUid(String uid);
    LogData getLogDataById(int id);
    int addLogData(LogData logData);
    void updateLogData(LogData logData);
    void deleteLogData(int id);
    boolean logDataExists(String text, String uid);
}