import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;


public class Block {
    private String prevHash;
    private ArrayList<Transaction> transactions;
    private int nonce;
    private String hash;

    public Block() {
        this.transactions = new ArrayList<Transaction>();
        this.nonce = 0;
    }

    public static String applySha256(String input) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hash);
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public String calculateHash() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return applySha256(prevHash + transactions.toString() + nonce);
    }

    public void mineBlock(int difficulty) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        String nonceKey = "";
        while(difficulty -- > 0){
            nonceKey += "0";
        }

        do {
            this.hash = calculateHash();
            nonce++;
        }
        while (!hash.startsWith(nonceKey));
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public boolean equals(Object obj) {
        return this.hash == ((Block) obj).hash;
    }
}
