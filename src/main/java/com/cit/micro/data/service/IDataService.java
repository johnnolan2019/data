package com.cit.micro.data.service;

import com.cit.micro.data.LogData;

import java.util.List;

public interface IDataService {
    List<LogData> getAllLogData();
    List<LogData> getAllLogDataByUid(String uid);
    LogData getLogDataById(int id);
    int addLogData(LogData logData);
    void updateLogData(LogData logData);
    boolean deleteLogData(int id);
}
