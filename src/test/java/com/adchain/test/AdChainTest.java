package com.adchain.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;
import com.adchain.model.Transaction;
import com.adchain.model.Wallet;
import com.adchain.test.utils.TestUtils;
import com.adchain.utils.AdChainUtility;
import com.adchain.utils.WalletUtility;
import com.google.gson.GsonBuilder;

public class AdChainTest {

	@Test
	public void blockFunctionalityTest() {
		System.out.println("Blocks Functionality Test:");

		AdChain adChain = new AdChain();
		AdBlock a = new AdBlock();
		a.getTransaction().setAd(TestUtils.getImage1());
		a.setTimeStamp(new Date().getTime());
		a.setHash(a.calculateHash());
		a.getTransaction().setAmount(3);

		AdBlock b = new AdBlock();
		b.getTransaction().setAd(TestUtils.getImage1());
		b.setTimeStamp(new Date().getTime() + 500);
		b.setHash(b.calculateHash());
		b.getTransaction().setAmount(4);

		AdBlock c = new AdBlock();
		c.getTransaction().setAd(TestUtils.getImage1());
		c.setTimeStamp(new Date().getTime() + 1000);
		c.setHash(c.calculateHash());
		c.getTransaction().setAmount(16);

		adChain.add(a);
		adChain.get(0).mineBlock(3);
		adChain.add(b);
		adChain.get(1).mineBlock(3);
		adChain.add(c);
		adChain.get(2).mineBlock(3);
		assertTrue(AdChainUtility.validateChain(adChain));

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(adChain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		System.out.println("\n\n");

	}

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

}
