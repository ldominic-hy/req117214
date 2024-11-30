/**
 * 
 */
package com.gov.bc.ca.supplement.models;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 
 */
public class InputData {

	@NotEmpty(message = "Id is required")
	private String id;
	
	@NotNull(message = "numberOfChildren is required")
	@Min(value = 0, message = "Number of Children should not be less than 0")
	private Integer numberOfChildren;
	
	@NotEmpty(message = "familyComposition is required")
	private String familyComposition;
	
	@NotNull(message = "familyUnitInPayForDecember is required")
	private Boolean familyUnitInPayForDecember;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the numberOfChildren
	 */
	public int getNumberOfChildren() {
		return numberOfChildren;
	}
	/**
	 * @param numberOfChildren the numberOfChildren to set
	 */
	public void setNumberOfChildren(int numberOfChildren) {
		this.numberOfChildren = numberOfChildren;
	}
	/**
	 * @return the familyComposition
	 */
	public String getFamilyComposition() {
		return familyComposition;
	}
	/**
	 * @param familyComposition the familyComposition to set
	 */
	public void setFamilyComposition(String familyComposition) {
		this.familyComposition = familyComposition;
	}
	/**
	 * @return the familyUnitInPayForDecember
	 */
	public Boolean getFamilyUnitInPayForDecember() {
		return familyUnitInPayForDecember;
	}
	/**
	 * @param familyUnitInPayForDecember the familyUnitInPayForDecember to set
	 */
	public void setFamilyUnitInPayForDecember(Boolean familyUnitInPayForDecember) {
		this.familyUnitInPayForDecember = familyUnitInPayForDecember;
	}	
}
