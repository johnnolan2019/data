package com.cit.micro.data.service;

import com.cit.micro.data.*;
import com.cit.micro.data.client.GrpcLoggerClient;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class GrpcData extends AccessDBGrpc.AccessDBImplBase {
    private GrpcLoggerClient logger = new GrpcLoggerClient();
    private static Logger LOGGER = LoggerFactory.getLogger(GrpcData.class);

    @Autowired
    public GrpcData(IDataService dataService) {
        this.dataService = dataService;
    }

    private IDataService dataService;

    @Override
    public StreamObserver<LogData> add(StreamObserver<Id> responseObserver) {
        logger.info("Adding ");
        return new StreamObserver<LogData>() {
            @Override
            public void onNext(LogData logData) {
                int tableId = dataService.addLogData(logData);
                Id id = Id.newBuilder().setId(tableId).build();
                responseObserver.onNext(id);
                //responseObserver.onCompleted();
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error("Something went wrong adding data");
            }

            @Override
            public void onCompleted() {
                logger.info("Completed add");
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void delete(Id id, StreamObserver<Result> responseObserver) {
        dataService.deleteLogData(id.getId());
        //todo implement delete
        logger.info("Deleting ");
        responseObserver.onCompleted();
    }

    @Override
    public void getEntry(Id id, StreamObserver<LogData> responseObserver) {
        dataService.getLogDataById(id.getId());
        //todo implement get entry
        logger.info("Finding ");
        responseObserver.onCompleted();
    }

    @Override
    public void getAll(Id id, StreamObserver<LogData> responseObserver) {
        logger.info("getting everything ");
        Iterator<LogData> iterator  =  dataService.getAllLogData().listIterator();
        while (iterator.hasNext())
            responseObserver.onNext(iterator.next());
        responseObserver.onCompleted();
    }

    @Override
    public void update(LogData logData, StreamObserver<Id> responseObserver) {
        dataService.updateLogData(logData);
        //todo implement update
        logger.info("updating ");
        responseObserver.onCompleted();
    }

    @Override
    public void getSystemLogs(Uid uid, StreamObserver<LogData> responseObserver) {
        logger.info("getting everything for a system...");
        Iterator<LogData> iterator =  dataService.getAllLogDataByUid(uid.getUid()).listIterator();
        while (iterator.hasNext())
            responseObserver.onNext(iterator.next());
        responseObserver.onCompleted();
    }

}
