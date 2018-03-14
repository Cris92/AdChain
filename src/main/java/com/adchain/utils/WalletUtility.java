package com.adchain.utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


//This class contains all utils for Wallet management as keys generator
public class WalletUtility {



	public static String getStringFromKey(Key key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}



	public static List<Key> createKeys() {
		try {
			// We use pretty standard parameters for encryption
			KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA", "BC");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");
			keyGen.initialize(ecSpec, random);
			KeyPair keyPair = keyGen.generateKeyPair();
			// We create a List to return, with 0 position Public key, 1
			// position Private Key
			List<Key> returnList = new ArrayList<Key>();
			returnList.add(keyPair.getPublic());
			returnList.add(keyPair.getPrivate());
			return returnList;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
