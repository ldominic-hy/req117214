/**
 * 
 */
package com.gov.bc.ca.supplement.listeners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.gov.bc.ca.supplement.events.SupplementEvent;

/**
 * 
 */
@Component
public class SupplementEventListener {
	
	@EventListener
	public void handleSupplementEvent(SupplementEvent event) {
		System.out.println("Supplement Event Listener created");
	}
}
