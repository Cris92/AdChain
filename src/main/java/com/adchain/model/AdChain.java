package com.adchain.model;

import java.util.ArrayList;

public class AdChain extends ArrayList<AdBlock> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2439815299401016661L;

	public boolean validateChain() {
		AdBlock currentBlock;
		AdBlock previousBlock;

		for (int i = 1; i < this.size(); i++) {
			currentBlock = this.get(i);
			previousBlock = this.get(i - 1);
			// Controllo su validità hashes
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Catena non coerente");
				return false;
			}
		}
		return true;
	}

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
