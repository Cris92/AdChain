package com.adchain.model;

import java.util.ArrayList;

public class AdChain extends ArrayList<AdBlock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439815299401016661L;

	@Override
	public boolean add(AdBlock a) {
		if (this.size() >= 1) {
			String previousHash = this.get(this.size() - 1).getHash();
			a.setPreviousHash(previousHash);
		} else {
			a.setPreviousHash("0");
		}
		return super.add(a);
	}
}
