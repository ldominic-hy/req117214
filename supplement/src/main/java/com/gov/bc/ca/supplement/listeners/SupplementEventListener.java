/**
 * 
 */
package com.gov.bc.ca.supplement.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.gov.bc.ca.supplement.events.SupplementEvent;

/**
 * 
 */
@Component
public class SupplementEventListener {
	private static final Logger logger = LoggerFactory.getLogger(SupplementEventListener.class);

	@EventListener
	public void handleSupplementEvent(SupplementEvent event) {
		logger.info("Supplement Event Listener created");
	}
}
