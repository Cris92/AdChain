package com.adchain.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.adchain.model.AdBlock;
import com.adchain.model.AdChain;
import com.adchain.model.Transaction;
import com.adchain.model.TransactionOutput;

//This class contains all utils for AdChain management as validation

public class AdChainUtility {

	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>();

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

	public static Map<String, TransactionOutput> getUTXOs() {
		return UTXOs;
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

	public static String getMerkleRoot(ArrayList<Transaction> transactions) {
		int count = transactions.size();
		ArrayList<String> previousTreeLayer = new ArrayList<String>();
		for (Transaction transaction : transactions) {
			previousTreeLayer.add(String.valueOf(transaction.getId()));
		}
		ArrayList<String> treeLayer = previousTreeLayer;
		while (count > 1) {
			treeLayer = new ArrayList<String>();
			for (int i = 1; i < previousTreeLayer.size(); i++) {
				treeLayer.add(getSHA(previousTreeLayer.get(i - 1) + previousTreeLayer.get(i)));
			}
			count = treeLayer.size();
			previousTreeLayer = treeLayer;
		}
		String merkleRoot = (treeLayer.size() == 1) ? treeLayer.get(0) : "";
		return merkleRoot;
	}

}
