package com.adchain.model;

import java.security.PublicKey;

import com.adchain.utils.AdChainUtility;
import com.adchain.utils.WalletUtility;

public class TransactionOutput {

	private String id;
	private PublicKey receiver;
	private long amount;
	private long transactionId;

	public void setId() {
		this.id = AdChainUtility.getSHA(WalletUtility.getStringFromKey(receiver) + amount + transactionId);
	}

	public String getId() {
		return this.id;
	}

	public boolean isMine(PublicKey publicKey) {
		return (publicKey == receiver);
	}

	public PublicKey getReceiver() {
		return receiver;
	}

	public void setReceiver(PublicKey receiver) {
		this.receiver = receiver;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "TransactionOutput [\nid=" + id + "\nreceiver=" + receiver + "\namount=" + amount + "\ntransactionId="
				+ transactionId + "\n]";
	}
}
