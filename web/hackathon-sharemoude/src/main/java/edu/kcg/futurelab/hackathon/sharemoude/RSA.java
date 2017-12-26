package edu.kcg.futurelab.hackathon.sharemoude;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class RSA {
	public static void main(String[] args) throws Throwable{
		// 新しいキーの生成
		KeyPair keyPair = createKeyPair();
		System.out.println("-- new public key --");
		System.out.println(serializeToBase64String(keyPair.getPublic()));
		System.out.println("-- new private key --");
		System.out.println(serializeToBase64String(keyPair.getPrivate()));
		System.out.println();

		// /resources/ にあるキーで暗号化/復号化
		String m = "こんにちは";
		String e = encrypt(m);
		String d = decrypt(e);
		System.out.println(m);
		System.out.println(e);
		System.out.println(d);
	}

	public static String encrypt(String message) throws ClassNotFoundException, IOException, NoSuchAlgorithmException,
		NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		PublicKey key = deserializeResource("/id_rsa.pub");
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		cipher.init(Cipher.ENCRYPT_MODE, key, new SecureRandom());
		return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
	}

	public static String decrypt(String decrypted) throws ClassNotFoundException, IOException, NoSuchAlgorithmException,
		NoSuchProviderException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
		PrivateKey key = deserializeResource("/id_rsa");
		Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		cipher.init(Cipher.DECRYPT_MODE, key, new SecureRandom());
		return new String(cipher.doFinal(Base64.getDecoder().decode(decrypted)));
	}

	static{
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	}

	private static <T> T deserializeResource(String resource) throws IOException, ClassNotFoundException{
		try(InputStream is = RSA.class.getResourceAsStream(resource)){
			String keys = new BufferedReader(new InputStreamReader(is, "UTF-8")).readLine();
			return deserializeFromBase64String(keys);
		}
	}

	private static KeyPair createKeyPair()
	throws NoSuchAlgorithmException, NoSuchProviderException, IOException{
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
		SecureRandom random = new SecureRandom();
		generator.initialize(2048, random);
		return generator.generateKeyPair();
	}

	private static String serializeToBase64String(Object o) throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try(ObjectOutputStream objos = new ObjectOutputStream(baos)){
			objos.writeObject(o);
		}
		return Base64.getEncoder().encodeToString(baos.toByteArray());
	}

	@SuppressWarnings("unchecked")
	private static <T> T deserializeFromBase64String(String s) throws ClassNotFoundException, IOException{
		byte[] bytes = Base64.getDecoder().decode(s);
		return (T)new ObjectInputStream(new ByteArrayInputStream(bytes)).readObject();
	}
}
