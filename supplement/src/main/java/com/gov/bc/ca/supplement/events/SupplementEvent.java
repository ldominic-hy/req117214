/**
 * 
 */
package com.gov.bc.ca.supplement.events;

/**
 * 
 */
public class SupplementEvent {
	private float baseAmount;
	private float childrenAmount;
	private float supplementAmount;
	
	public SupplementEvent() {
		this.baseAmount = 0;
		this.childrenAmount = 0;
		this.supplementAmount = 0;
	}

	public SupplementEvent(float baseAmount, float childrenAmount, float supplementAmount) {
		this.baseAmount = baseAmount;
		this.childrenAmount = childrenAmount;
		this.supplementAmount = supplementAmount;
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
