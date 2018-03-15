package com.adchain.utils;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.util.List;
import java.util.Map;

import com.adchain.model.Transaction;
import com.adchain.model.TransactionInput;
import com.adchain.model.TransactionOutput;

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

	public static Transaction executeTransaction(Transaction transaction, Map<String, TransactionOutput> uTXOs) {
		if (!transaction.verifiySignature()) {
			System.out.println("Signature not valid");
			return null;
		}
		for (TransactionInput input : transaction.getTransactionInputs()) {
			input.setUTXO(uTXOs.get(input.getTransactionOutputId()));
		}

		long leftOver = getInputsValue(transaction.getTransactionInputs()) - transaction.getAmount();

		if (leftOver < 0) {
			System.out.println("Not enough money in the wallet");
			return null;
		} else {
			TransactionOutput payment = new TransactionOutput();
			TransactionOutput leftOverPayment = new TransactionOutput();
			payment.setAmount(transaction.getAmount());
			payment.setTransactionId(transaction.getId());
			payment.setReceiver(transaction.getReceiver());
			payment.setId();
			leftOverPayment.setAmount(leftOver);
			leftOverPayment.setTransactionId(transaction.getId());
			leftOverPayment.setReceiver(transaction.getSender());
			leftOverPayment.setId();

			// Add to the UTXO map the new transactions created for payment and
			// leftover
			AdChainUtility.getUTXOs().put(payment.getId(), payment);
			AdChainUtility.getUTXOs().put(leftOverPayment.getId(), leftOverPayment);

			// Removing used UTXOs used for the creation of the leftover and for
			// the payment
			for (TransactionInput input : transaction.getTransactionInputs()) {
				AdChainUtility.getUTXOs().remove(input.getTransactionOutputId());
			}

		}

		return transaction;
	}

	private static Object getMaxId() {
		// TODO Auto-generated method stub
		return null;
	}

	private static long getInputsValue(List<TransactionInput> inputs) {
		long total = 0;
		for (TransactionInput i : inputs) {
			if (i.getUTXO() != null) {
				total += i.getUTXO().getAmount();
			}
		}
		return total;
	}
}
