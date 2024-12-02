/**
 * 
 */
package com.gov.bc.ca.supplement.controllers;

import javax.validation.Valid;

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
		OutputData outputData = supplementService.calculateOutput(inputData);
		return outputData;
	}
}
