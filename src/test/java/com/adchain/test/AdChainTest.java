package com.adchain.test;

import static org.junit.Assert.assertTrue;

import java.util.Map.Entry;

import org.junit.Test;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;
import com.adchain.model.Transaction;
import com.adchain.model.TransactionOutput;
import com.adchain.model.Wallet;
import com.adchain.test.utils.TestUtils;
import com.adchain.utils.AdChainUtility;
import com.adchain.utils.TransactionUtility;
import com.adchain.utils.WalletUtility;

public class AdChainTest {

	@Test
	public void signatureFunctionalityTest() {
		System.out.println("Signature Functionality Test:");
		Wallet walletA = new Wallet("a", 1);
		Wallet walletB = new Wallet("b", 2);

		System.out.println("Private and public keys:");
		System.out.println(WalletUtility.getStringFromKey(walletA.getPrivateKey()));
		System.out.println(WalletUtility.getStringFromKey(walletB.getPublicKey()));
		Transaction transaction = new Transaction();
		transaction.setSender(walletA.getPublicKey());
		transaction.setReceiver(walletB.getPublicKey());
		transaction.setAmount(10);
		transaction.setAd(TestUtils.getImage1());
		transaction.generateSignature(walletA.getPrivateKey());
		// Verify the signature works and verify it from the public key
		assertTrue(transaction.verifiySignature());
		System.out.println("Is signature verified");
		System.out.println(transaction.verifiySignature());
		System.out.println("\n\n");

	}

	@Test
	public void fullFunctionalityTest() {
		System.out.println("Full Functionality Test:");
		AdChain chain = new AdChain();
		Wallet walletA = new Wallet("a", 1);
		Wallet walletB = new Wallet("b", 2);
		Wallet coinFactory = new Wallet("coinFactory", 3);
		// Creation of generation transaction
		Transaction generationCoinsTransaction = new Transaction();
		generationCoinsTransaction.setSender(coinFactory.getPublicKey());
		generationCoinsTransaction.setReceiver(walletA.getPublicKey());
		generationCoinsTransaction.setAmount(10000);
		generationCoinsTransaction.setAd(TestUtils.getImage1());
		generationCoinsTransaction.generateSignature(coinFactory.getPrivateKey());
		generationCoinsTransaction.setId(1);

		TransactionOutput generationUTXOs = new TransactionOutput();
		generationUTXOs.setAmount(generationCoinsTransaction.getAmount());
		generationUTXOs.setReceiver(generationCoinsTransaction.getReceiver());
		generationUTXOs.setTransactionId(generationCoinsTransaction.getId());
		generationUTXOs.setId();
		AdChainUtility.getUTXOs().put(String.valueOf(generationUTXOs.getId()), generationUTXOs);
		AdBlock generationBlock = new AdBlock();
		generationBlock.getTransactions().add(generationCoinsTransaction);
		chain.add(generationBlock);
		System.out.println("\nAfter Generation\n");
		System.out.println("Wallet A Balance: "+walletA.getWalletBalance()+"\n");
		System.out.println("Wallet B Balance: "+walletB.getWalletBalance()+"\n");
		for (Entry<String, TransactionOutput> o : AdChainUtility.getUTXOs().entrySet()) {
			
			System.out.println(o.getKey());
			System.out.println(o.getValue());
		}
		

		Transaction transaction = new Transaction();
		transaction.setTransactionInputs(walletA.getMyUTXOs());
		transaction.setSender(walletA.getPublicKey());
		transaction.setReceiver(walletB.getPublicKey());
		transaction.setAmount(10);
		transaction.setAd(TestUtils.getImage1());
		transaction.generateSignature(walletA.getPrivateKey());
		transaction.setId(2);
		Transaction result = TransactionUtility.executeTransaction(transaction, AdChainUtility.getUTXOs());
		if (result != null) {
			AdBlock firstBlock = new AdBlock();
			firstBlock.getTransactions().add(result);
			chain.add(firstBlock);
		}
		System.out.println("\nAfter Transaction\n");
		System.out.println("Wallet A Balance: "+walletA.getWalletBalance()+"\n");
		System.out.println("Wallet B Balance: "+walletB.getWalletBalance()+"\n");
		for (Entry<String, TransactionOutput> o : AdChainUtility.getUTXOs().entrySet()) {
			System.out.println(o.getKey());
			System.out.println(o.getValue());
		}
		
	}
}
