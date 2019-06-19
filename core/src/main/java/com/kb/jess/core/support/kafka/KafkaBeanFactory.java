package com.kb.jess.core.support.kafka;

import com.kb.jess.core.service.AccountSaveService;
import com.kb.jess.core.service.CustomerSaveService;
import com.kb.jess.core.support.PropertiesHelper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

public class KafkaBeanFactory {

    public static KafkaTransactionProducer createTransactionProducer() {
        return new KafkaTransactionProducer(createKafkaProducer());
    }

    public static KafkaTransactionConsumer createTransactionConsumer(final AccountSaveService accountSaveService, final CustomerSaveService customerSaveService) {
        final String[] topics = PropertiesHelper.getProperty("kafka.consumer.topics", "").split(",");
        return new KafkaTransactionConsumer(createKafkaConsumer(), Arrays.asList(topics), accountSaveService, customerSaveService);
    }

    private static <K, V> KafkaProducer<K, V> createKafkaProducer(final Properties properties) {
        return new KafkaProducer<>(properties);
    }

    private static <K, V> KafkaProducer<K, V> createKafkaProducer() {
        final Properties props = new Properties();
        props.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertiesHelper.getProperty("kafka.producer.bootstrap.servers"));
        props.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, PropertiesHelper.getProperty("kafka.producer.key.serializer"));
        props.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PropertiesHelper.getProperty("kafka.producer.value.serializer"));
        props.setProperty(ProducerConfig.ACKS_CONFIG, PropertiesHelper.getProperty("kafka.producer.acks", "1"));
        props.setProperty(ProducerConfig.BUFFER_MEMORY_CONFIG, PropertiesHelper.getProperty("kafka.producer.buffer.memory"));
        props.setProperty(ProducerConfig.COMPRESSION_TYPE_CONFIG, PropertiesHelper.getProperty("kafka.producer.compression.type"));
        props.setProperty(ProducerConfig.RETRIES_CONFIG, PropertiesHelper.getProperty("kafka.producer.retries"));
        props.setProperty(ProducerConfig.BATCH_SIZE_CONFIG, PropertiesHelper.getProperty("kafka.producer.batch.size"));
        props.setProperty(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, PropertiesHelper.getProperty("kafka.producer.delivery.timeout.ms"));
        props.setProperty(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, PropertiesHelper.getProperty("kafka.producer.request.timeout.ms"));
        props.setProperty(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, PropertiesHelper.getProperty("kafka.producer.enable.idempotence", "false"));
        props.setProperty(ProducerConfig.TRANSACTIONAL_ID_CONFIG, PropertiesHelper.getProperty("kafka.producer.transactional.id.prefix", "") + ThreadLocalRandom.current().nextInt(100));

        return createKafkaProducer(props);
    }

    private static <K, V> KafkaConsumer<K, V> createKafkaConsumer(final Properties properties) {
        return new KafkaConsumer<>(properties);
    }

    private static <K, V> KafkaConsumer<K, V> createKafkaConsumer() {
        final Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.bootstrap.servers"));
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, PropertiesHelper.getProperty("kafka.consumer.group.id"));
        props.setProperty(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.heartbeat.interval.ms"));
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.key.deserializer"));
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.value.deserializer"));
        props.setProperty(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, PropertiesHelper.getProperty("kafka.consumer.fetch.min.bytes"));
        props.setProperty(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, PropertiesHelper.getProperty("kafka.consumer.fetch.max.bytes"));
        props.setProperty(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.session.timeout.ms"));
        props.setProperty(ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.request.timeout.ms"));
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, PropertiesHelper.getProperty("kafka.consumer.auto.offset.reset"));
        props.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, PropertiesHelper.getProperty("kafka.consumer.enable.auto.commit", "false"));
        props.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, PropertiesHelper.getProperty("kafka.consumer.isolation.level", "read_committed"));
        props.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.max.poll.interval.ms"));
        props.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, PropertiesHelper.getProperty("kafka.consumer.max.poll.records"));

        return createKafkaConsumer(props);
    }
}
