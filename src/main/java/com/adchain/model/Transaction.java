package com.adchain.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import com.adchain.utils.AdChainUtility;
import com.adchain.utils.TransactionUtility;
import com.adchain.utils.WalletUtility;

public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3953979324195332340L;
	private PublicKey sender;
	private PublicKey receiver;
	private String ad;
	private long amount;
	private long id;
	private byte[] signature;
	private List<TransactionInput> transactionInputs = new ArrayList<TransactionInput>();

	public PublicKey getSender() {
		return sender;
	}

	public void setSender(PublicKey sender) {
		this.sender = sender;
	}

	public PublicKey getReceiver() {
		return receiver;
	}

	public void setReceiver(PublicKey receiver) {
		this.receiver = receiver;
	}

	public String getAd() {
		return ad;
	}

	public void setAd(String ad) {
		this.ad = ad;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public void generateSignature(PrivateKey privateKey) {
		String data = WalletUtility.getStringFromKey(sender) + WalletUtility.getStringFromKey(receiver) + ad;
		signature = TransactionUtility.createSignature(privateKey, data);
	}

	// Check absence of malicious attacks to transaction
	public boolean verifiySignature() {
		String data = WalletUtility.getStringFromKey(sender) + WalletUtility.getStringFromKey(receiver) + ad;
		return TransactionUtility.verifySignature(sender, data, signature);
	}

	public String calculateHash() {
		id++;
		return AdChainUtility
				.getSHA(WalletUtility.getStringFromKey(sender) + WalletUtility.getStringFromKey(receiver) + ad + id);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}

	public List<TransactionInput> getTransactionInputs() {
		return transactionInputs;
	}

	public void setTransactionInputs(List<TransactionInput> transactionInputs) {
		this.transactionInputs = transactionInputs;
	}
}
