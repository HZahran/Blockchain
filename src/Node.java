import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class Node {
    private static int UID = 0;
    private int id;
    private String name;
    private Wallet wallet;
    private BlockChain blockChain;
    private ArrayList<Node> peers;
    private Block currentBlock;
    private HashMap<String, LinkedList<Block>> cache;
    private HashSet<String> hashes;
    private static final int MAX_TRANSACTIONS = 2;
    private static final int DIFFICULTY = 2;

    public Node(String name) throws NoSuchAlgorithmException,
            NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
        this.id = UID++;
        this.name = name;
        this.wallet = new Wallet();
        this.peers = new ArrayList<Node>();
        this.cache = new HashMap<String, LinkedList<Block>>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Node> getPeers() {
        return peers;
    }

    public void setPeers(ArrayList<Node> peers) {
        this.peers = peers;
    }

    // Creating a transaction and start announcing it to the peers
    public void sendTransaction(Node reciever, int amount) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException, SignatureException, UnsupportedEncodingException {
        // Creating new transaction
        Transaction trans = new Transaction(this, reciever, amount);

        // Creating the digital signature
        byte[] signature = this.wallet.sign(trans.getId() + "#" + trans.getSender().getName() + "#" + trans.getReciever().getName() + "#" + trans.getAmount());

        // Add the signature to the transaction
        trans.setSignature(signature);
        if (this.currentBlock == null)
            this.currentBlock = new Block();

        // Printing the created transaction and who created it
        System.out.println(this.name + " initiated new transaction..");
        System.out.println(trans.toString());
        System.out.println();

        // Start announcing the transaction
        announceTransaction(trans);
    }

    // Announcing the received or the created transaction
    public void announceTransaction(Transaction trans) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        int n = (int) ((Math.random() * (peers.size() - 1)) + 1);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> idxs = new ArrayList<Integer>();
            while (true) {
                int idx = (int) (Math.random() * peers.size());
                if (!idxs.contains(idx)) {
                    idxs.add(idx);

                    // Printing the announcing procedure
                    System.out.println(this.name + " announcing " + trans.getId() + " to " + peers.get(idx).getName());
                    System.out.println();

                    // Announce the transaction to the peers
                    peers.get(idx).getTransactionAnnouncement(trans);
                    break;
                }
            }
        }
    }

    // Receiving the transactions
    public void getTransactionAnnouncement(Transaction trans) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if (this.currentBlock == null)
            this.currentBlock = new Block();

        // Check if I already have the received transaction or not
        if (!this.currentBlock.getTransactions().contains(trans)) {

            // Add it to the current block
            this.currentBlock.getTransactions().add(trans);

            // Check if reached the maximum
            if(this.currentBlock.getTransactions().size() >= MAX_TRANSACTIONS){
                createBlock();
            }
            // And then announce it to my peers
            announceTransaction(trans);
        }
    }

    public void printCurrentBlock() {
        if (currentBlock != null) {
            for (int i = 0; i < currentBlock.getTransactions().size(); i++)
                System.out.println(currentBlock.getTransactions().get(i));
        } else
            System.out.println("null");
    }

    public void createBlock() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        // Mine block
        this.currentBlock.mineBlock(DIFFICULTY);

        // Announce it
        announceBlock(this.currentBlock);

    }

    // Announcing the received or the created block
    public void announceBlock(Block block) {
        int n = (int) ((Math.random() * (peers.size() - 1)) + 1);
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> idxs = new ArrayList<Integer>();
            while (true) {
                int idx = (int) (Math.random() * peers.size());
                if (!idxs.contains(idx)) {
                    idxs.add(idx);

                    // Printing the announcing procedure
                    System.out.println(this.name + " announcing " + block.getHash() + " to " + peers.get(idx).getName());
                    System.out.println();

                    // Announce the transaction to the peers
                    peers.get(idx).getBlockAnnouncement(block);
                    break;
                }
            }
        }
    }

    // Receiving the blocks
    public void getBlockAnnouncement(Block block) {

        if (this.currentBlock == null)
            this.currentBlock = new Block();

        // Check if I already have the received transaction or not
        if (!this.hashes.contains(block.getHash())) {

            // Add hash value
            this.hashes.add(block.getHash());

            // And then announce it to my peers
            announceBlock(block);
        }
    }

    @Override
    public String toString() {
        return name;
    }

}
