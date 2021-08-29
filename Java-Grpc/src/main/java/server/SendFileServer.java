package server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protoFile.FileInfoRequest;
import protoFile.FileResultResponse;
import protoFile.MakeFileGrpc;

/**
 * @Classname SendFileServer
 * @Description TODO
 * @Date 2021/8/29 15:43
 * @Created by starsky
 */
public class SendFileServer extends MakeFileGrpc.MakeFileImplBase {

    Logger logger = LoggerFactory.getLogger(SendFileServer.class);

    @Override
    public void sendFileMessage(FileInfoRequest request, StreamObserver<FileResultResponse> responseObserver) {
        String filePath = request.getFilePath();
        logger.debug("filePath {}",filePath);

        FileResultResponse build = FileResultResponse.newBuilder().setFilePathResult("c:/").build();
        responseObserver.onNext(build);
        responseObserver.onCompleted();
    }
}
