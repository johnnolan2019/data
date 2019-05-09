package com.cit.micro.data.service;

import com.cit.micro.data.Channel;
import com.cit.micro.data.LogData;
import io.grpc.stub.StreamObserver;

import java.util.List;

public interface IDataService {
    List<LogData> getAllLogData();
    List<LogData> getAllLogDataByUid(String uid);
    LogData getLogDataById(int id);
    int addLogData(LogData logData);
    void updateLogData(LogData logData);
    boolean deleteLogData(int id);
    Channel getChannel(String uid);
}
