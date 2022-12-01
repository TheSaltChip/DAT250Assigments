package no.hvl.dat250.assignment.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class MessagingClient {
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
    public MessagingClient() {
        client.connectWith()
                .keepAlive(10)
                .simpleAuth()
                .username(username)
                .password(UTF_8.encode(password))
                .applySimpleAuth()
                .send();

        System.out.println("Connected successfully");

    }

    // disconnect the client after a message was received
    public void disconnect() {
        client.disconnect();
    }

    // publish a message to the topic "my/test/topic"
    public void publishMessage(PollAnalytic payload) {
        try {
            client.publishWith()
                    .topic("/poll/" + payload.getTheme())
                    .payload(new ObjectMapper().writeValueAsString(payload).getBytes(UTF_8))
                    .retain(true)
                    .messageExpiryInterval(120)
                    .send()
                    .getPublish();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
