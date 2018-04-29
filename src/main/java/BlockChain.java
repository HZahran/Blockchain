import java.util.ArrayList;

public class BlockChain {
    ArrayList<Block> ledger = new ArrayList<Block>();

    public ArrayList<Block> getLedger() {
        return ledger;
    }

    public void setLedger(ArrayList<Block> ledger) {
        this.ledger = ledger;
    }

    public void addBlock(Block block) {
        ledger.add(block);
    }

    public void addBlock(int indx, Block block) {
        ledger.add(indx, block);
    }

    public Block getBlock(int indx) {
        return ledger.get(indx);
    }

    public int getlongestChainLength(){
        return ledger.size() - 1;
    }
}
