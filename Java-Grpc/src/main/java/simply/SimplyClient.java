package simply;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protoFile.FileInfoRequest;
import protoFile.FileResultResponse;
import protoFile.MakeFileGrpc;

import java.util.concurrent.TimeUnit;


/**
 * 简单 RPC
 * @Classname SimplyClient
 * @Description TODO
 * @Date 2021/8/26 0:09
 * @Created by starsky
 */
public class SimplyClient {


    private static final Logger logger =  LoggerFactory.getLogger(SimplyClient.class);


    private static final String host="127.0.0.1";
    private static final int port=50051;
    private final ManagedChannel channel;
    private final MakeFileGrpc.MakeFileBlockingStub makeFileBlockingStub;

    public SimplyClient() throws InterruptedException {

        channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        makeFileBlockingStub = MakeFileGrpc.newBlockingStub(channel);

    }


    public void sendFileMessage(){
        FileInfoRequest request = FileInfoRequest.newBuilder().setFilePath("C:/").buildPartial();
        FileResultResponse fileResultResponse = makeFileBlockingStub.sendFileMessage(request);
        String filePathResult = fileResultResponse.getFilePathResult();
        logger.debug("filePathResult:{}",filePathResult);
    }

    public static void main(String[] args) throws InterruptedException {
        SimplyClient simplyClient = new SimplyClient();
        simplyClient.sendFileMessage();
    }
}
