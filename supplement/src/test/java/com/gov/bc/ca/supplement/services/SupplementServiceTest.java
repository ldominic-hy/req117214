/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.gov.bc.ca.supplement.models.InputData;
import com.gov.bc.ca.supplement.models.OutputData;

/**
 * 
 */
public class SupplementServiceTest {

	@Mock
	private ApplicationEventPublisher eventPublisher;
	
	@InjectMocks
	private SupplementService supplementService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testCalculateBaseAmount() {
		Object[][] familyCompositionArray = {
			{"single", 60.0f},
			{"couple", 120.0f},
			{"", 0.0f},
			{"SINGLE", 0.0f},
			{"Single", 0.0f},
			{"COUPLE", 0.0f},
			{"Couple", 0.0f},
			{"abcde", 0.0f},
			{null, 0.0f},
		};
		
		InputData inputData = new InputData();
		inputData.setId("someId");
		inputData.setNumberOfChildren(0);
		inputData.setFamilyUnitInPayForDecember(true);

		for (int i = 0; i < familyCompositionArray.length ; i++ ) {
			String familyComposition = familyCompositionArray[i][0] == null ? null : familyCompositionArray[i][0].toString();
			inputData.setFamilyComposition(familyComposition);
			OutputData outputData = supplementService.calculateOutput(inputData);
			assertEquals(familyCompositionArray[i][1], outputData.getBaseAmount());
		}
	}

	@Test
	public void testCalculateChildrenAmount() {
		Object[][] numberOfChildrenArray = {
			{0, 0.0f},
			{1, 20.0f},
			{2, 40.0f},
			{-1, 0.0f},
			{-2, 0.0f},
			{null, 0.0f},
		};
		
		InputData inputData = new InputData();
		inputData.setId("someId");
		inputData.setFamilyComposition("single");
		inputData.setFamilyUnitInPayForDecember(true);

		for (int i = 0; i < numberOfChildrenArray.length ; i++ ) {
			Integer numberOfChildren = 0;
			if (numberOfChildrenArray[i][0] instanceof Integer) {
				numberOfChildren = (Integer) numberOfChildrenArray[i][0];
			}
			inputData.setNumberOfChildren(numberOfChildren);
			OutputData outputData = supplementService.calculateOutput(inputData);
			assertEquals(numberOfChildrenArray[i][1], outputData.getChildrenAmount());
		}
	}
	
	@Test
	public void testCalculateSupplement() {
		Object[][] inputDataArray = {
			{"single", 0, 60.0f},
			{"single", 1, 80.0f},
		};
		
		InputData inputData = new InputData();
		OutputData outputData = new OutputData();

		inputData.setId("someId");
		inputData.setFamilyUnitInPayForDecember(true);

		inputData.setFamilyComposition("single");

		for (int i = 0; i < inputDataArray.length ; i++ ) {
			String familyComposition = inputDataArray[i][0] == null ? null : inputDataArray[i][0].toString();
			inputData.setFamilyComposition(familyComposition);
			
			Integer numberOfChildren = 0;
			if (inputDataArray[i][1] instanceof Integer) {
				numberOfChildren = (Integer) inputDataArray[i][1];
			}
			inputData.setNumberOfChildren(numberOfChildren);
			
			outputData = supplementService.calculateOutput(inputData);
			assertEquals(inputDataArray[i][2], outputData.getSupplementAmount());
			assertEquals("someId", outputData.getId());
			assertEquals(true, outputData.getIsEligible());
		}
	}
}
