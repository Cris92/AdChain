package com.adchain.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;

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
	public byte[] signature;

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
}
