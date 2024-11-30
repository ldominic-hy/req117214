/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.gov.bc.ca.supplement.events.SupplementEvent;
import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;

/**
 * 
 */
@Service
public class SupplementService {

	private final ApplicationEventPublisher eventPublisher;
	
	public SupplementService(ApplicationEventPublisher eventPublisher) {
		this.eventPublisher = eventPublisher;
	}
	
	public OutputData calculateOutput(InputData inputData) {
		OutputData outputData = new OutputData();
		outputData.setId(inputData.getId());
		outputData.setIsEligible(inputData.getFamilyUnitInPayForDecember());
		float baseAmount = calculateBaseAmount(inputData.getFamilyComposition());
		float childrenAmount = calculateChildrenAmount(inputData.getNumberOfChildren());
		float supplementAmount = (baseAmount > 0) ? baseAmount + childrenAmount : 0;
		outputData.setBaseAmount(baseAmount);
		outputData.setChildrenAmount(childrenAmount);
		outputData.setSupplementAmount(supplementAmount);
		SupplementEvent event = new SupplementEvent(baseAmount, childrenAmount, supplementAmount);
		eventPublisher.publishEvent(event);
		return outputData;
	}
	
	private float calculateBaseAmount(String familyComposition) {
		if (familyComposition == null) {
			return 0;
		} else {
			return familyComposition.equals("single") ? 60 : familyComposition.equals("couple") ? 120 : 0;
		}
	}
	
	private float calculateChildrenAmount(Integer numberOfChildren) {
		if (numberOfChildren == null || numberOfChildren < 0) return 0;
		return 20 * numberOfChildren;
	};
}