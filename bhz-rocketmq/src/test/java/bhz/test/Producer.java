/**
 * Copyright (C) 2010-2013 Alibaba Group Holding Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package bhz.test;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;


/**
 * Producer，发送消息
 * 
 */
public class Producer {
	
	public static final String ROCKETMQ_NAMESERVERS = "192.168.139.128:9876;192.168.139.129:9876";
	
    public static void main(String[] args) throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("test");
        //仅主机模式
        producer.setNamesrvAddr("192.168.139.128:9876;192.168.139.129:9876");
        producer.start();
        for (int i = 1; i <= 50; i++) {
                /*Message msg = new Message("topic2",// topic
                    "tag2",// tag
                    "keys_"+i,//keys
                    ("ccccc Tag2 " + i).getBytes()// body
                        );*/
                Message msg = new Message("TopicTest","Tag1","keys_"+i,("Hello RocketMQ " +i).getBytes() );

                SendResult sendResult = producer.send(msg);
               
                System.out.println(sendResult);

            
        }
//        for (int i = 1; i <= 50; i++) {
//            try {
//                Message msg = new Message("Topic1",// topic
//                    "Tag1",// tag
//                    ("ccccc Tag1 " + i).getBytes()// body
//                        );
//                SendResult sendResult = producer.send(msg);
//                System.out.println(sendResult);
//            }
//            catch (Exception e) {
//                e.printStackTrace();
//                Thread.sleep(1000);
//            }
//        }
       /* for (int i = 1; i <= 50; i++) {
            try {
                Message msg = new Message("topic1",// topic
                    "tag1",// tag
                    ("ccccc Tag1 " + i).getBytes()// body
                        );
                SendResult sendResult = producer.send(msg);
                System.out.println(sendResult);
            }
            catch (Exception e) {
                e.printStackTrace();
                Thread.sleep(1000);
            }
        }*/
        producer.shutdown();
    }
}
