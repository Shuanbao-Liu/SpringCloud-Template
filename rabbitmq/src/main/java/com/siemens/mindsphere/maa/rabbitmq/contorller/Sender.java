package com.siemens.mindsphere.maa.rabbitmq.contorller;

import java.util.Date;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by wsdgc4 on 3/8/2018.
 */
@Component
public class Sender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(){
        String context = "hello" + new Date();
        System.out.println("Sender:" + context);

        this.rabbitTemplate.convertAndSend("hello",context);
    }
}
