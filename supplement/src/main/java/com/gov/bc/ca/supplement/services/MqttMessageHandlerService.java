/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;

/**
 * 
 */
@Service
public class MqttMessageHandlerService {

	private static final Logger logger = LoggerFactory.getLogger(MqttMessageHandlerService.class);

	@Autowired
	private SupplementService supplementService;

	@Autowired
    public DefaultMqttPahoClientFactory mqttClientFactory;

	@Autowired
	private MessageChannel mqttOutboundChannel;

	@Value("${config.mqttsvr.outboundTopic}")
	private String outboundTopic;

	@Value("${config.mqttsvr.topicId}")
	private String topicId;

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
    	return message -> {
    		String payload = message.getPayload().toString();
    		logger.debug("inbound payload: " + payload);

    		JSONObject jsonObj = new JSONObject(payload);
    		InputData inputData = new InputData();
			inputData.setId(jsonObj.getString("id"));
			inputData.setFamilyComposition(jsonObj.getString("familyComposition"));
			inputData.setFamilyUnitInPayForDecember(jsonObj.getBoolean("familyUnitInPayForDecember"));
			inputData.setNumberOfChildren((Integer) jsonObj.getInt("numberOfChildren"));

	        OutputData outputData = supplementService.calculateOutput(inputData);

	        JSONObject outputJson = new JSONObject();
	        outputJson.put("id", outputData.getId());
	        outputJson.put("isEligible", outputData.getIsEligible());
	        outputJson.put("baseAmount", outputData.getBaseAmount());
	        outputJson.put("childrenAmount", outputData.getChildrenAmount());
	        outputJson.put("supplementAmount", outputData.getSupplementAmount());

    		logger.debug("outbound payload: " + outputJson.toString());
	        mqttOutboundChannel.send(new GenericMessage<>(outputJson.toString()));
	        outputJson.clear();
    	};
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler("mqttClient", mqttClientFactory);
        messageHandler.setAsync(true);
        messageHandler.setDefaultQos(0);
        messageHandler.setDefaultTopic(outboundTopic + topicId);
        return messageHandler;
    }
}
