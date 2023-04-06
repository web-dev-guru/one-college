package org.college.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.college.utils.ApplicationConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {
	
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${fixture.topic.name}")
	private String fixtureTopicName;

	@Value("${kafka.partitions}")
	private int numPartitions;

	@Value("${kafka.replicas}")
	private int replicas;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}


	@Bean(name = ApplicationConstant.FIXTURE_TOPIC)
	public NewTopic fixtureTopic() {
		return new NewTopic(fixtureTopicName, numPartitions, (short) replicas);
	}

}
