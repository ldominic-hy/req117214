/**
 * 
 */
package com.gov.bc.ca.supplement.mqtt;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

/**
 * 
 */
@Configuration
public class MqttConfig {

	@Value("${config.mqttsvr.url}")
	private String mqttsvrUrl;
	
	@Value("${config.mqttsvr.port}")
	private String mqttsvrPort;
	
	@Value("${config.mqttsvr.timeout}")
	private Long mqttsvrTimeout;
	
	public static final String topicId = "4d1918a3-d07a-4a7b-a054-be43d3b397e7";

    @Bean
    public DefaultMqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{"tcp://" + mqttsvrUrl + ":" + mqttsvrPort});
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoMessageDrivenChannelAdapter mqttInbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter("mqttClient", mqttClientFactory(), "BRE/calculateWinterSupplementInput/" + topicId);
        adapter.setCompletionTimeout(mqttsvrTimeout);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = message.getPayload().toString();
            System.out.println("===================================================================");
            System.out.println("Received message: " + payload);
            System.out.println("===================================================================");
        };
    }
}

