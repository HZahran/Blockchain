import java.util.ArrayList;

public class BlockChain {
    private ArrayList<Block> ledger = new ArrayList<Block>();

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

    public int getlongestChainLength() {
        return ledger.size() - 1;
    }

    public boolean isEqual(BlockChain otherBlockChain) {

        if (ledger.size() != otherBlockChain.getLedger().size())
            return false;

        for (int i = 0; i < otherBlockChain.getLedger().size(); i++) {
            if (!ledger.get(i).equals(otherBlockChain.getLedger().get(i)))
                return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ledger.toString();
    }
}
