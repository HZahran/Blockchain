import java.security.*;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

public class Wallet {
	private PublicKey publicKey;
	private PrivateKey privateKey;
	private final int keySize = 1024;

	public PublicKey getPublicKey() {
		return publicKey;
	}

	public Wallet() throws NoSuchAlgorithmException, NoSuchProviderException{

		// Generating Public and Private keys
		KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
		keyGen.initialize(keySize, new SecureRandom());
		KeyPair pair = keyGen.generateKeyPair();

		// Setting the public and private keys generated
		this.publicKey = pair.getPublic();
		this.privateKey = pair.getPrivate();

	}

	// Adding a digital signature to any transaction
	public byte [] sign(String msg) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
		Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");
		dsa.initSign(privateKey);
		return dsa.sign();
	}

}
