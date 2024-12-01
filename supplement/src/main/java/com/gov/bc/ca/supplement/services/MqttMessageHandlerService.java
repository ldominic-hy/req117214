/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;

import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;
import com.gov.bc.ca.supplement.mqtt.MqttConfig.SupplementGateway;

/**
 * 
 */
@Service
public class MqttMessageHandlerService {

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	@Autowired
	private SupplementGateway gateway;
	
	@Autowired
    public DefaultMqttPahoClientFactory mqttClientFactory;

	@Value("${config.mqttsvr.outboundTopic}")
	private String outboundTopic;

	@Value("${config.mqttsvr.topicId}")
	private String topicId;

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
    	return new MessageHandler() {
    		@Override
    		public void handleMessage(Message<?> message) throws MessagingException {
				System.out.println(message.getPayload().toString());
				String jsonString = message.getPayload().toString();
				JSONObject jsonObj = new JSONObject(jsonString);

				InputData inputData = new InputData();
				inputData.setId(jsonObj.getString("topic_id"));
				inputData.setFamilyComposition(jsonObj.getString("familyComposition"));
				inputData.setFamilyUnitInPayForDecember(jsonObj.getBoolean("familyUnitInPayForDecember"));
				inputData.setNumberOfChildren(jsonObj.getInt("numberOfChildren"));
	
		        OutputData outputData = new OutputData();
		        SupplementService supplementService = new SupplementService(eventPublisher);
		        outputData = supplementService.calculateOutput(inputData);
		        gateway.sendToMqtt(outputData.toString());
		    }
    	};
    }

    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound(OutputData outputData) {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("testClient", mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic(outboundTopic + topicId);
        return messageHandler;
    }
}
