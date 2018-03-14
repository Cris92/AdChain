package com.adchain.model;

import com.adchain.utils.AdChainUtility;

public class AdBlock {

	private String hash;
	private String previousHash;
	private String ad;
	private long timeStamp;
	private int proofOfWorkValue;

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setPreviousHash(String previousHash) {
		this.previousHash = previousHash;
	}

	public String calculateHash() {
		return AdChainUtility.getSHA(previousHash + Long.toString(timeStamp) + ad + proofOfWorkValue);
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getHash() {
		return this.hash;
	}

	public String getPreviousHash() {
		return previousHash;
	}

	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!this.getHash().substring(0, difficulty).equals(target)) {
			proofOfWorkValue++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

}
