package simply;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protoFile.FileInfoRequest;
import protoFile.FileResultResponse;
import protoFile.MakeFileGrpc;
import server.SendFileServer;

import java.io.IOException;
import java.util.List;

/**
 * @Classname SimplyServer
 * @Description TODO
 * @Date 2021/8/29 16:40
 * @Created by starsky
 */
public class SimplyServer extends MakeFileGrpc.MakeFileImplBase{


    Logger logger = LoggerFactory.getLogger(SendFileServer.class);

    @Override
    public void sendFileMessage(FileInfoRequest request, StreamObserver<FileResultResponse> responseObserver) {
        String filePath = request.getFilePath();
        logger.debug("filePath {}",filePath);

        FileResultResponse build = FileResultResponse.newBuilder().setFilePathResult("c:/").build();
        responseObserver.onNext(build);
        responseObserver.onCompleted();
    }



    private final int port;
    private final Server server;

    public SimplyServer(int port) throws IOException {
        this.port = port;
        server = ServerBuilder.forPort(port).addService(new SendFileServer()).build();
//        this.server = (port).addService(new HelloStreamService(HelloUtil.parseFeatures())).build();
    }

    // 启动服务
    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                SimplyServer.this.stop();
                System.err.println("*** server shut down");
            }
        });
    }

    // 启动服务
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon
     * threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        java.util.logging.Logger.getGlobal().setLevel(java.util.logging.Level.OFF);
        SimplyServer server = new SimplyServer(8980);
        server.start();
        server.blockUntilShutdown();
    }

}
