/**
 * 
 */
package com.gov.bc.ca.supplement.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * 
 */
@EnableIntegration
@IntegrationComponentScan(basePackages = "com.gov.bc")
@Configuration
public class MqttConfig {

	@Value("${config.mqttsvr.url}")
	private String mqttsvrUrl;
	
	@Value("${config.mqttsvr.port}")
	private String mqttsvrPort;
	
	@Value("${config.mqttsvr.timeout}")
	private Long mqttsvrTimeout;
	
	@Value("${config.mqttsvr.inboundTopic}")
	private String inboundTopic;

	@Value("${config.mqttsvr.topicId}")
	private String topicId;

    @Bean
    public DefaultMqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://" + mqttsvrUrl + ":" + mqttsvrPort});
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public MessageProducer mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter("tcp://" + mqttsvrUrl + ":" + mqttsvrPort, "mqttClient", mqttClientFactory(), inboundTopic + topicId);
        adapter.setCompletionTimeout(mqttsvrTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }
}

