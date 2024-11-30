/**
 * 
 */
package com.gov.bc.ca.supplement.models;

/**
 * 
 */
public class OutputData {

	private String id;
	private Boolean isEligible;
	private float baseAmount;
	private float childrenAmount;
	private float supplementAmount;

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
	 * @return the isEligible
	 */
	public Boolean getIsEligible() {
		return isEligible;
	}
	/**
	 * @param isEligible the isEligible to set
	 */
	public void setIsEligible(Boolean isEligible) {
		this.isEligible = isEligible;
	}
	/**
	 * @return the baseAmount
	 */
	public float getBaseAmount() {
		return baseAmount;
	}
	/**
	 * @param baseAmount the baseAmount to set
	 */
	public void setBaseAmount(float baseAmount) {
		this.baseAmount = baseAmount;
	}
	/**
	 * @return the childrenAmount
	 */
	public float getChildrenAmount() {
		return childrenAmount;
	}
	/**
	 * @param childrenAmount the childrenAmount to set
	 */
	public void setChildrenAmount(float childrenAmount) {
		this.childrenAmount = childrenAmount;
	}
	/**
	 * @return the supplementAmount
	 */
	public float getSupplementAmount() {
		return supplementAmount;
	}
	/**
	 * @param supplementAmount the supplementAmount to set
	 */
	public void setSupplementAmount(float supplementAmount) {
		this.supplementAmount = supplementAmount;
	}
}
