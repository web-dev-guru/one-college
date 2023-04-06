package org.college.management.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.college.management.controllers.domain.FixtureDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducerService {
    private NewTopic fixtureTopic;
    @Autowired
    private KafkaTemplate<String, FixtureDTO> fixturesKafkaTemplate;
    @Autowired
    public void setFixtureTopic(@Value("#{fixtureTopic}") NewTopic fixtureTopic) {
        this.fixtureTopic = fixtureTopic;
    }

    @Async
    public void produceFixtures(FixtureDTO fixture){
        log.info("Sending message with ID: '{}' to topic='{}'", fixture.getId(), fixtureTopic.toString());


        fixturesKafkaTemplate.send(fixtureTopic.name(), fixture.getId().toString(), fixture)
                .addCallback(result -> {
                    final RecordMetadata m;
                    if (result != null) {
                        m = result.getRecordMetadata();
                        log.info("Sent record to topic {} partition {} @ offset {}", m.topic(), m.partition(),
                                m.offset());
                    }
                }, exception -> log.error("Failed to send to kafka", exception));
    }
}
