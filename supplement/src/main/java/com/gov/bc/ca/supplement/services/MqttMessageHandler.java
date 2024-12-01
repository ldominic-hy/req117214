/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

/**
 * 
 */
@Service
public class MqttMessageHandler {

    @ServiceActivator(inputChannel = "mqttInputChannel")
    public void handleMessage(String payload) {
        System.out.println("==================================================================================");
        System.out.println("Received message: " + payload);
        System.out.println("==================================================================================");
    }
}
