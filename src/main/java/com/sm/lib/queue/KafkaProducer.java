//package com.sm.lib.queue;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import lombok.CustomLog;
//import org.apache.kafka.clients.producer.ProducerRecord;
//
//@ApplicationScoped
//@CustomLog
//public class KafkaProducer {
//    io.smallrye.reactive.messaging.kafka.KafkaProducer<String, String> producer;
//
//    public void sendMessage(String topic, String message) {
//        // String key = UUID.randomUUID().toString();
//        ProducerRecord<String, String> record = new ProducerRecord<>(topic, message);
//
//        producer.send(record).subscribe().with(
//                metadata -> log.info("Sent to partition: " + metadata.partition()),
//                failure -> log.info("Failed to send message: " + failure.getMessage())
//        );
//    }
//}
