package com.adchain.model;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.List;

import com.adchain.utils.WalletUtility;

public class Wallet {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private String ownerName;
	private String transactionHistory;
	private long id;

	
	//At the creation of the wallet we create both private and public keys
	public Wallet(String ownerName, long id) {
		List<Key> keys = WalletUtility.createKeys();
		this.publicKey = (PublicKey) keys.get(0);
		this.privateKey = (PrivateKey) keys.get(1);
		setOwnerName(ownerName);
	}

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(PublicKey publicKey) {
		this.publicKey = publicKey;
	}

	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getTransactionHistory() {
		return transactionHistory;
	}

	public void setTransactionHistory(String transactionHistory) {
		this.transactionHistory = transactionHistory;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
