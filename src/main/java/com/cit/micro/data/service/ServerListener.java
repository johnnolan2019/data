package com.cit.micro.data.service;

import com.cit.micro.data.client.GrpcLoggerClient;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ServerListener {
    private static final GrpcLoggerClient logger = new GrpcLoggerClient();
    private static GrpcData grpcData;
    private static int port = 6568;

    @Autowired
    public ServerListener(GrpcData grpcDataInjected){
        grpcData = grpcDataInjected;
    }

    public static void serverRun(){
        Server server = ServerBuilder
                .forPort(port)
                .addService(grpcData).build();
        logger.info("Data service now running ");
        try{
            server.start();
            server.awaitTermination();
        }catch (
                IOException e){
            logger.error("Data Service threw IO exception");
        }catch (InterruptedException e){
            logger.error("Data Service threw Interrupted exception");
        }
    }

}
