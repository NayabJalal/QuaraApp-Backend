package com.quoraBackend.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    //define the Server--
    @Value(value = "${spring.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServer;

    @Value(value = "${spring.kafka.consumer-group-id:view-count-consumer}")
    private String groupId;

    public static final String TOPIC_NAME = "view-count-topic";

    //create a producer factory.
    // Factory-is Referred to a class which has a responsible for creation of the Object,(new - naive way)
    @Bean
    public ProducerFactory<String,Object> producerFactory(){ //String to (JSON)
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, //SERIALIZER for dumping into kafka
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ConsumerFactory<String,Object> consumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer);
        configProps.put(
                ConsumerConfig.GROUP_ID_CONFIG,
                groupId);
        configProps.put(
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        configProps.put(
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES,"com.quoraBackend.events"); //or * for all packages
        return new DefaultKafkaConsumerFactory<>(configProps);
    }

    //Kafka Template---
    @Bean
    public KafkaTemplate<String,Object> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);
        return factory;
    }
}
