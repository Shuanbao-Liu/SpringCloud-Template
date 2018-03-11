package com.siemens.mindsphere.maa.stream;

import com.siemens.mindsphere.maa.StreamHelloApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * Created by wsdgc4 on 3/11/2018.
 */

@EnableBinding(Sink.class)
public class SinkReceiver {

    private static Logger logger = LoggerFactory.getLogger(StreamHelloApplication.class);

    @StreamListener(Sink.INPUT)
    public void receiver (Object payload){
        logger.info("Received:" + payload);
    }

}
