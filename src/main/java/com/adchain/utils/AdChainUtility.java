package com.adchain.utils;

import java.security.MessageDigest;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;

//This class contains all utils for AdChain management as validation

public class AdChainUtility {

	public static boolean validateChain(AdChain chain) {
		AdBlock currentBlock;
		AdBlock previousBlock;

		for (int i = 1; i < chain.size(); i++) {
			currentBlock = chain.get(i);
			previousBlock = chain.get(i - 1);
			// Hashes validity check
			if (!previousBlock.getHash().equals(currentBlock.getPreviousHash())) {
				System.out.println("Catena non coerente");
				return false;
			}
		}
		return true;
	}

	public static String getSHA(String dataToConvert) {
		try {
			// We applies sha256 to our input
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(dataToConvert.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < hash.length; i++) {
				String hex = Integer.toHexString(0xff & hash[i]);
				if (hex.length() == 1)
					hexString.append('0');
				hexString.append(hex);
			}
			return hexString.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
