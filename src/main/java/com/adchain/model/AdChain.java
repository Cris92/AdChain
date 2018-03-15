package com.adchain.model;

import java.util.ArrayList;

import com.adchain.utils.AdChainUtility;

public class AdChain extends ArrayList<AdBlock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439815299401016661L;

	public boolean add(AdBlock a,int difficulty) {
		if (this.size() >= 1) {
			String previousHash = this.get(this.size() - 1).getHash();
			a.setPreviousHash(previousHash);
		} else {
			a.setPreviousHash("0");
		}
		a.mineBlock(difficulty);
		return super.add(a);
	}
}
