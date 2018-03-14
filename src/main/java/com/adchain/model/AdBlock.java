package com.adchain.model;

import com.adchain.utils.AdChainUtility;

public class AdBlock {

	private String hash;
	private String previousHash;
	private Transaction transaction;
	private long timeStamp;
	private int proofOfWorkValue;

	public AdBlock() {
		this.transaction = new Transaction();
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
		return AdChainUtility.getSHA(previousHash + this.transaction.getAmount() + Long.toString(timeStamp)
				+ this.transaction.getAd() + proofOfWorkValue);
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

	public Transaction getTransaction() {
		return this.transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

}
