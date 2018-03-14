package com.adchain.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;
import com.adchain.test.utils.TestUtils;
import com.google.gson.GsonBuilder;

public class AdChainTest {

	@Test
	public void mainTest() {
		AdChain adChain = new AdChain();
		AdBlock a = new AdBlock();
		a.setAd(TestUtils.getImage1());
		a.setTimeStamp(new Date().getTime());
		a.setHash(a.calculateHash());

		AdBlock b = new AdBlock();
		b.setAd(TestUtils.getImage1());
		b.setTimeStamp(new Date().getTime() + 500);
		b.setHash(b.calculateHash());

		AdBlock c = new AdBlock();
		c.setAd(TestUtils.getImage1());
		c.setTimeStamp(new Date().getTime() + 1000);
		c.setHash(c.calculateHash());

		adChain.add(a);
		adChain.get(0).mineBlock(2);
		adChain.add(b);
		adChain.get(1).mineBlock(2);
		adChain.add(c);
		adChain.get(2).mineBlock(2);
		assertTrue(adChain.validateChain());

		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(adChain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
	}

}
