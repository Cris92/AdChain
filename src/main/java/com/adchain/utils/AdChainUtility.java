package com.adchain.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AdChainUtility {

	public static String getSHA(String dataToConvert) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			// Applies sha256 to our input,
			byte[] hash = digest.digest(dataToConvert.getBytes("UTF-8"));
			StringBuffer hexString = new StringBuffer(); // This will contain
															// hash as
															// hexidecimal
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
