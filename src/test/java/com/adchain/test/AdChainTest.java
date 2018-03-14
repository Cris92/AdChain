package com.adchain.test;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;
import com.adchain.test.utils.TestUtils;
import com.adchain.utils.AdChainUtility;
import com.google.gson.GsonBuilder;

public class AdChainTest {

	@Test
	public void mainTest() {
		AdChain adChain = new AdChain();
		AdBlock a = new AdBlock();
		a.setAd(TestUtils.getImage1());
		a.setTimeStamp(new Date().getTime());
		a.setHash(a.calculateHash());
		a.setAmount(3);

		AdBlock b = new AdBlock();
		b.setAd(TestUtils.getImage1());
		b.setTimeStamp(new Date().getTime() + 500);
		b.setHash(b.calculateHash());
		b.setAmount(4);

		AdBlock c = new AdBlock();
		c.setAd(TestUtils.getImage1());
		c.setTimeStamp(new Date().getTime() + 1000);
		c.setHash(c.calculateHash());
		c.setAmount(16);

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
	}

}
