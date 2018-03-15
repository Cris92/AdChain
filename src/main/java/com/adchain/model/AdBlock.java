package com.adchain.model;

import java.util.ArrayList;
import java.util.List;

import com.adchain.utils.AdChainUtility;

public class AdBlock {

	private String hash;
	private String previousHash;
	private ArrayList<Transaction> transaction = new ArrayList<Transaction>();
	private long timeStamp;
	private int proofOfWorkValue;
	private String merkleRoot;

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
		return AdChainUtility.getSHA(previousHash + Long.toString(timeStamp) + proofOfWorkValue + merkleRoot);
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
		merkleRoot = AdChainUtility.getMerkleRoot(this.getTransactions());
		String target = new String(new char[difficulty]).replace('\0', '0');
		while (!this.getHash().substring(0, difficulty).equals(target)) {
			proofOfWorkValue++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}

	public ArrayList<Transaction> getTransactions() {
		return this.transaction;
	}

	public void setTransactions(ArrayList<Transaction> transaction) {
		this.transaction = transaction;
	}

	@Override
	public String toString() {
		return "AdBlock [hash=" + hash + "\n previousHash=" + previousHash + "\n transaction=" + transaction
				+ "\n timeStamp=" + timeStamp + "\n proofOfWorkValue=" + proofOfWorkValue + "\n merkleRoot=" + merkleRoot
				+ "]";
	}

}
