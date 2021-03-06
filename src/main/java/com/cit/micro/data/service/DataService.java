package com.cit.micro.data.service;

import com.cit.micro.data.Channel;
import com.cit.micro.data.LogData;
import com.cit.micro.data.dao.ILogDbDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataService implements IDataService {

    private ILogDbDAO logDbDAO;

    @Autowired
    public DataService(ILogDbDAO logDbDAO){
        this.logDbDAO = logDbDAO;
    }

    @Override
    public List<LogData> getAllLogData() {
        return logDbDAO.getAllLogData();
    }

    @Override
    public LogData getLogDataById(int id) {
        return logDbDAO.getLogDataById(id);
    }

    @Override
    public int addLogData(LogData logData) {
        if (logDbDAO.logDataExists(logData.getText(), logData.getUid())) {
            return 0;
        } else {
            return logDbDAO.addLogData(logData);
        }
    }

    @Override
    public void updateLogData(LogData logData) {
        logDbDAO.updateLogData(logData);
    }

    @Override
    public boolean deleteLogData(int id) {
        return logDbDAO.deleteLogData(id);
    }

    @Override
    public List<LogData> getAllLogDataByUid(String uid){
        return logDbDAO.getAllLogDataByUid(uid);
    }

    @Override
    public Channel getChannel(String uid){
        LogData logData = logDbDAO.getChannel(uid);
        return  Channel.newBuilder()
                .setChannel(logData.getChannel())
                .setUid(logData.getUid())
                .build();
    }
}