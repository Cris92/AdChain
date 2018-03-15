package com.adchain.model;

import java.security.Key;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.adchain.utils.AdChainUtility;
import com.adchain.utils.TransactionUtility;
import com.adchain.utils.WalletUtility;

public class Wallet {

	private PublicKey publicKey;
	private PrivateKey privateKey;
	private String ownerName;
	private String transactionHistory;
	private long id;
	private List<TransactionInput> myUTXOs = new ArrayList<TransactionInput>();

	public List<TransactionInput> getMyUTXOs() {
		return myUTXOs;
	}

	public void setMyUTXOs(List<TransactionInput> myUTXOs) {
		this.myUTXOs = myUTXOs;
	}

	// At the creation of the wallet we create both private and public keys
	public Wallet(String ownerName, long id) {
		List<Key> keys = WalletUtility.createKeys();
		this.publicKey = (PublicKey) keys.get(0);
		this.privateKey = (PrivateKey) keys.get(1);
		setOwnerName(ownerName);
	}

	public long getWalletBalance() {
		long total = 0;
		for (Map.Entry<String, TransactionOutput> item : AdChainUtility.getUTXOs().entrySet()) {
			TransactionOutput UTXO = item.getValue();
			if (UTXO.isMine(publicKey)) {
				TransactionInput ti = new TransactionInput();
				ti.setUTXO(UTXO);
				ti.setTransactionOutputId(UTXO.getId());
				myUTXOs.add(ti);
				total += UTXO.getAmount();
			}
		}
		return total;
	}

	public Transaction createTransactionForPayment(PublicKey receiver, long amount, String ad) {
		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setAd(ad);
		transaction.setSender(publicKey);
		transaction.setReceiver(receiver);
		transaction.setSignature(TransactionUtility.createSignature(privateKey, ad));
		return transaction;
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
