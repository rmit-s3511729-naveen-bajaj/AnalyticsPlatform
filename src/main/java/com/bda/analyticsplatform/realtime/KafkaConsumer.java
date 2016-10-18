package com.bda.analyticsplatform.realtime;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;



public class KafkaConsumer extends Thread {
	final static String clientId = "SimpleConsumerDemoClient";
    final static String TOPIC = "TutorialTopic";
    ConsumerConnector consumerConnector;


//    public static void main(String[] argv) throws UnsupportedEncodingException {
//    	KafkaConsumer kafkaConsumer = new KafkaConsumer();
//    	kafkaConsumer.start();
//    }

    public KafkaConsumer(){
        Properties properties = new Properties();
        properties.put("zookeeper.connect","130.56.250.165:2181");
        properties.put("metadata.broker.list", "130.56.250.165:9092");
        properties.put("group.id","test-consumer-group");
        properties.put("auto.offset.reset", "smallest"); 

        ConsumerConfig consumerConfig = new ConsumerConfig(properties);
        consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

    }

    @Override
    public void run() {
    	// long readOffset = getLastOffset(kafka.api.OffsetRequest.EarliestTime(), clientName);
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(TOPIC, new Integer(1));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicCountMap);
//        System.out.println(consumerMap);
        System.out.println(consumerMap.get(TOPIC).get(0).size());
         
        System.out.println("111111");
        KafkaStream<byte[], byte[]> stream =  consumerMap.get(TOPIC).get(0);
        ConsumerIterator<byte[], byte[]> it = stream.iterator();
        System.out.println(it.size());
        while(it.hasNext())
            System.out.println(new String(it.next().message()));

    }

    private static void printMessages(ByteBufferMessageSet messageSet) throws UnsupportedEncodingException {
        for(MessageAndOffset messageAndOffset: messageSet) {
            ByteBuffer payload = messageAndOffset.message().payload();
            byte[] bytes = new byte[payload.limit()];
            payload.get(bytes);
            System.out.println(new String(bytes, "UTF-8"));
        }
    }

}
