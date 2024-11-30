/**
 * 
 */
package com.gov.bc.ca.supplement.controllers;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;

/**
 * 
 */
@RestController
@RequestMapping("/api/submit")
public class InformationController {

	// GET: Testing from URL
	@GetMapping
	public String displayString() {
		return "Testing";
	}
	
	// POST: Submit information for supplement amount
	@PostMapping
	public OutputData calculate(@Valid @RequestBody InputData inputData) {
//	public String calculate(@RequestBody InputData inputData) {
		OutputData outputData = new OutputData();
		outputData.setId(inputData.getId());
		outputData.setIsEligible(true);
		outputData.setBaseAmount(100);
		outputData.setChildrenAmount(10);
		outputData.setSupplementAmount(1000);
//		return "100";
		return outputData;
	}
}
