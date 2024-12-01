package com.gov.bc.ca.supplement;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
/* @TestPropertySource(locations = "classpath:application-test.yml") */
@TestPropertySource(properties = {
	"config.mqttsvr.url=test.mosquitto.org",
	"config.mqttsvr.port=1883",
	"config.mqttsvr.timeout=5000"
})
class SupplementApplicationTests {

	@Test
	void contextLoads() {
	}

}
