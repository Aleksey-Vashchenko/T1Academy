package com.vashchenko.education.t1.task_4.producer_service.kafka;

import com.vashchenko.education.t1.task_4.producer_service.web.dto.MetricDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.mapping.DefaultJackson2JavaTypeMapper;
import org.springframework.kafka.support.mapping.Jackson2JavaTypeMapper;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfiguration {

    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object,Object> kafkaOperations){
        return new DefaultErrorHandler(new DeadLetterPublishingRecoverer(kafkaOperations),new FixedBackOff(1000L,2));
    }

    @Bean
    RecordMessageConverter converter(){
        JsonMessageConverter converter = new JsonMessageConverter();
        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
        typeMapper.addTrustedPackages("*");
        Map<String,Class<?>> mappings = new HashMap<>();
        mappings.put("Metric", MetricDto.class);
        typeMapper.setIdClassMapping(mappings);
        converter.setTypeMapper(typeMapper);
        return converter;
    }



    @Bean
    public NewTopic metricTopic(){
        return new NewTopic("metricTopic",1,(short) 1);
    }

    @Bean
    public NewTopic deletedTopic(){
        return new NewTopic("deadMetricTopic",1,(short) 1);
    }
}
