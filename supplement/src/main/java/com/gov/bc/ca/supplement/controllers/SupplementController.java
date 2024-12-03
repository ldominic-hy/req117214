/**
 * 
 */
package com.gov.bc.ca.supplement.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;
import com.gov.bc.ca.supplement.services.SupplementService;

/**
 * 
 */
@RestController
@RequestMapping("/api/submit")
public class SupplementController {

	private static final Logger logger = LoggerFactory.getLogger(SupplementController.class);

	@Autowired
	private SupplementService supplementService;
	
	// GET: Testing from URL
	@GetMapping
	public String displayString() {
		return "Status: OK";
	}
	
	// POST: Submit information for supplement amount
	@PostMapping
	public OutputData calculate(@Valid @RequestBody InputData inputData) {
		logger.debug("inputData (id): " + inputData.getId());
		logger.debug("inputData (family composition): " + inputData.getFamilyComposition());
		logger.debug("inputData (number of children): " + inputData.getNumberOfChildren());
		logger.debug("inputData (family unit in pay for December): " + inputData.getFamilyUnitInPayForDecember());

		OutputData outputData = supplementService.calculateOutput(inputData);
		logger.debug("outputData (id): " + outputData.getId());
		logger.debug("outputData (is eligible): " + outputData.getIsEligible());
		logger.debug("outputData (baseAmount): " + outputData.getBaseAmount());
		logger.debug("outputData (children amount): " + outputData.getChildrenAmount());
		logger.debug("outputData (supplement amount): " + outputData.getSupplementAmount());

		return outputData;
	}
}
