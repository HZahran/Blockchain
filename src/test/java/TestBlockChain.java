import org.junit.BeforeClass;
import org.junit.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TestBlockChain {

    private static Network network;

    @BeforeClass
    public static void setup() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchProviderException, SignatureException, InvalidKeyException {
        // Small Network
        String[] ourTeam = {"Rania", "Mariam", "H", "Baher"};

        network = new Network(ourTeam);

        // Printing the network
        System.out.println(network);
        System.out.println("------------------------");
        System.out.println();
    }

    @Test
    public void testBlockAdditionNoConflict() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException {
        // Small Network
        String[] ourTeam = {"Rania", "Mariam", "H", "Baher"};

        network = new Network(ourTeam);

        // Creating hard coded transactions
        Block block0 = new Block();
        Block block1 = new Block();
        Block block2 = new Block();

        block0.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block0.addTransaction(network.users.get(2).createTransaction(network.users.get(3), 100));

        block1.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block1.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block2.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block2.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block0.setPrevHash("");
        block0.mineBlock(2);

        block1.setPrevHash(block0.getHash());
        block1.mineBlock(2);

        block2.setPrevHash(block1.getHash());
        block2.mineBlock(2);

        BlockChain expectedBlockChain = new BlockChain();

        expectedBlockChain.addBlock(block0);
        expectedBlockChain.addBlock(block1);
        expectedBlockChain.addBlock(block2);

        network.users.get(0).getBlockAnnouncement(block0);
        network.users.get(0).getBlockAnnouncement(block1);
        network.users.get(0).getBlockAnnouncement(block2);

        BlockChain actualBlockChain = network.users.get(0).getBlockChain();

        System.out.println(expectedBlockChain);
        System.out.println(actualBlockChain);

        boolean isBlockChainsEqual = expectedBlockChain.isEqual(actualBlockChain);
        assertEquals("Blocks received should be added to the original blockchain", true, isBlockChainsEqual);
    }

    @Test
    public void testBlockAdditionWithConflict() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException {
        // Small Network
        String[] ourTeam = {"Rania", "Mariam", "H", "Baher"};

        network = new Network(ourTeam);

        Node curNode = network.users.get(1);

        Block block0 = new Block();
        Block block1 = new Block();
        Block block1_1 = new Block();
        Block block2 = new Block();
        Block block2_1 = new Block();
        Block block3 = new Block();
        Block block3_1 = new Block();

        block0.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block0.addTransaction(network.users.get(2).createTransaction(network.users.get(3), 100));

        block1.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block1.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block1_1.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block1_1.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block2.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block2.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block2_1.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block2_1.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block3.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block3.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));

        block3_1.addTransaction(network.users.get(0).createTransaction(network.users.get(1), 50));
        block3_1.addTransaction(network.users.get(1).createTransaction(network.users.get(2), 100));
        block0.setPrevHash("");
        block0.mineBlock(2);

        block1.setPrevHash(block0.getHash());
        block1.mineBlock(2);

        block2.setPrevHash(block1.getHash());
        block2.mineBlock(2);

        block3.setPrevHash(block2.getHash());
        block3.mineBlock(2);

        block1_1.setPrevHash(block0.getHash());
        block1_1.mineBlock(2);

        block2_1.setPrevHash(block1_1.getHash());
        block2_1.mineBlock(2);

        block3_1.setPrevHash(block2_1.getHash());
        block3_1.mineBlock(2);

        curNode.getBlockAnnouncement(block0);
        System.out.println(curNode.getBlockChain().getLedger());
        assertEquals("Block chain index 0 should be block0", block0.getHash(),
                curNode.getBlockChain().getLedger().get(0).getHash());

        curNode.getBlockAnnouncement(block1);
        assertEquals("Block chain index 1 should be block1", block1.getHash(),
                curNode.getBlockChain().getLedger().get(1).getHash());


        curNode.getBlockAnnouncement(block1_1);
        assertEquals("Block chain index 1 should be block1", block1.getHash(),
                curNode.getBlockChain().getLedger().get(1).getHash());
        assertEquals("Cache should contain block1-1", true,
                curNode.getBlockCache().containsKey(block1_1.getHash()));

        curNode.getBlockAnnouncement(block2);
        assertEquals("Block chain index 2 should be block2", block2.getHash(),
                curNode.getBlockChain().getLedger().get(2).getHash());


        curNode.getBlockAnnouncement(block2_1);
        assertEquals("Block chain index 2 should be block2", block2.getHash(),
                curNode.getBlockChain().getLedger().get(2).getHash());
        assertEquals("Cache should contain block2-1", true,
                curNode.getBlockCache().containsKey(block2_1.getHash()));


        curNode.getBlockAnnouncement(block3_1);
        assertEquals("Block chain index 3 should be block3_1", block3_1.getHash(),
                curNode.getBlockChain().getLedger().get(3).getHash());
        assertEquals("Block chain index 2 should be block2-1", block2_1.getHash(),
                curNode.getBlockChain().getLedger().get(2).getHash());
        assertEquals("Block chain index 1 should be block1_1", block1_1.getHash(),
                curNode.getBlockChain().getLedger().get(1).getHash());
        assertEquals("Cache should contain block1", true,
                curNode.getBlockCache().containsKey(block1.getHash()));
        assertEquals("Cache should contain block2", true,
                curNode.getBlockCache().containsKey(block2.getHash()));
    }

    @Test
    public void testMiningBlock() throws NoSuchPaddingException, UnsupportedEncodingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, SignatureException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException {
        // Small Network
        String[] ourTeam = {"Rania", "Mariam", "H", "Baher"};

        network = new Network(ourTeam);

        Node curNode = network.users.get(1);
        Transaction t1 = network.users.get(2).createTransaction(network.users.get(0), 50);
        Transaction t2 = network.users.get(0).createTransaction(network.users.get(1), 50);

        curNode.getTransactionAnnouncement(t1);
        curNode.getTransactionAnnouncement(t2);

        boolean isBlockSent = false;

        for (int i = 0; i < curNode.getPeers().size(); i++) {
            ArrayList<Block> currUserLedger = curNode.getPeers().get(i).getBlockChain().getLedger();
            if (currUserLedger.size() > 0) {
                if (currUserLedger.get(0).getTransactions().get(0).equals(t1)
                        && currUserLedger.get(0).getTransactions().get(1).equals(t2)) {
                    isBlockSent = true;
                    break;
                }
            }
        }
        assertEquals("Block should be mined and sent to at least one peer after 2 transactions", true, isBlockSent);
    }
}
