package com.adchain.utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;

//This class contains all utils for Transaction management as signature checker

public class TransactionUtility {
	public static boolean verifySignature(PublicKey publicKey, String ad, byte[] signature) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Signature ecdsaVerify = Signature.getInstance("ECDSA", "BC");
			ecdsaVerify.initVerify(publicKey);
			ecdsaVerify.update(ad.getBytes());
			return ecdsaVerify.verify(signature);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] createSignature(PrivateKey privateKey, String ad) {
		Signature signature;
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			signature = Signature.getInstance("ECDSA", "BC");
			signature.initSign(privateKey);
			byte[] strByte = ad.getBytes();
			signature.update(strByte);
			return signature.sign();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
