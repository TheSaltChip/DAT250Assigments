package com.example.messagesubscriber;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.SUBSCRIBED;
import static java.nio.charset.StandardCharsets.UTF_8;

@SpringBootApplication
public class MessageSubscriberApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(MessageSubscriberApplication.class, args);
    }

    @Override
    public void run(String... args) {

        final String host = "e6d1f8e633d748bd8affc00a4181c8bc.s2.eu.hivemq.cloud";
        final String username = "listener";
        final String password = "listener";

        // create an MQTT client
        final Mqtt5BlockingClient client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        // connect to HiveMQ Cloud with TLS and username/pw
        client.connectWith()
                .keepAlive(30)
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");

        client.subscribeWith()
                .topicFilter("/Kids")
                .topicFilter("/Tricky")
                .topicFilter("/Work")
                .send();

        // set a callback that is called when a message is received (using the async API style)
        client.toAsync().publishes(ALL, publish -> {
            System.out.println("Received message: " +
                    publish.getTopic() + " -> " +
                    UTF_8.decode(publish.getPayload().get()));
        });
    }
}
