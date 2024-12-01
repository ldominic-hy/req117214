/**
 * 
 */
package com.gov.bc.ca.supplement.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.test.context.TestPropertySource;

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
			{"single", true, 60.0f},
			{"couple", true, 120.0f},
			{"", true, 0.0f},
			{"SINGLE", true, 0.0f},
			{"Single", true, 0.0f},
			{"COUPLE", true, 0.0f},
			{"Couple", true, 0.0f},
			{"abcde", true, 0.0f},
			{null, true, 0.0f},
			{"single", false, 0.0f},
			{"couple", false, 0.0f},
			{"", false, 0.0f},
			{"SINGLE", false, 0.0f},
			{"Single", false, 0.0f},
			{"COUPLE", false, 0.0f},
			{"Couple", false, 0.0f},
			{"abcde", false, 0.0f},
			{null, false, 0.0f},
		};
		
		InputData inputData = new InputData();
		inputData.setId("someId");
		inputData.setNumberOfChildren(0);

		for (int i = 0; i < familyCompositionArray.length ; i++ ) {
			String familyComposition = familyCompositionArray[i][0] == null ? null : familyCompositionArray[i][0].toString();
			inputData.setFamilyComposition(familyComposition);
			Boolean familyUnitInPayForDecember = false;
			if (familyCompositionArray[i][1] instanceof Boolean) {
				familyUnitInPayForDecember = (Boolean) familyCompositionArray[i][1];
			}
			inputData.setFamilyUnitInPayForDecember(familyUnitInPayForDecember);
			OutputData outputData = supplementService.calculateOutput(inputData);
			assertEquals(familyCompositionArray[i][2], outputData.getBaseAmount());
		}
	}

	@Test
	public void testCalculateChildrenAmount() {
		Object[][] numberOfChildrenArray = {
			{0, true, 0.0f},
			{1, true, 20.0f},
			{2, true, 40.0f},
			{-1, true, 0.0f},
			{-2, true, 0.0f},
			{null, true, 0.0f},
			{0, false, 0.0f},
			{1, false, 0.0f},
			{2, false, 0.0f},
			{-1, false, 0.0f},
			{-2, false, 0.0f},
			{null, false, 0.0f},
		};
		
		InputData inputData = new InputData();
		inputData.setId("someId");
		inputData.setFamilyComposition("single");

		for (int i = 0; i < numberOfChildrenArray.length ; i++ ) {
			Integer numberOfChildren = 0;
			if (numberOfChildrenArray[i][0] instanceof Integer) {
				numberOfChildren = (Integer) numberOfChildrenArray[i][0];
			}
			inputData.setNumberOfChildren(numberOfChildren);
			Boolean familyUnitInPayForDecember = false;
			if (numberOfChildrenArray[i][1] instanceof Boolean) {
				familyUnitInPayForDecember = (Boolean) numberOfChildrenArray[i][1];
			}
			inputData.setFamilyUnitInPayForDecember(familyUnitInPayForDecember);
			OutputData outputData = supplementService.calculateOutput(inputData);
			assertEquals(numberOfChildrenArray[i][2], outputData.getChildrenAmount());
		}
	}
	
	@Test
	public void testCalculateSupplement() {
		Object[][] inputDataArray = {
			{"single", 0, true, 60.0f},
			{"single", 1, true, 80.0f},
			{"single", 0, false, 0.0f},
			{"single", 1, false, 0.0f},
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

			Boolean familyUnitInPayForDecember = false;
			if (inputDataArray[i][2] instanceof Boolean) {
				familyUnitInPayForDecember = (Boolean) inputDataArray[i][2];
			}
			inputData.setFamilyUnitInPayForDecember(familyUnitInPayForDecember);

			outputData = supplementService.calculateOutput(inputData);
			assertEquals(inputDataArray[i][3], outputData.getSupplementAmount());
			assertEquals("someId", outputData.getId());
			assertEquals(inputDataArray[i][2], outputData.getIsEligible());
		}
	}
}
