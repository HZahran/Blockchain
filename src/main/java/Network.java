import java.security.*;
import java.util.ArrayList;

public class Network {
    public ArrayList<Node> users;

    public Network(String[] names) throws InvalidKeyException, SignatureException, NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        // Small Network

        users = new ArrayList<Node>();

        // Joining the network
        for (int i = 0; i < names.length; i++)
            addUser(names[i]);

    }

    // Joining the network
    public void addUser(String userName) throws NoSuchProviderException, NoSuchAlgorithmException {

        int n = (int) ((Math.random() * (users.size() - 1)) + 1);
        Node node = new Node(userName);
        for (int i = 0; i < n; i++) {
            while (true) {
                int idx = (int) (Math.random() * users.size());
                if (!node.getPeers().contains(users.get(idx))) {
                    node.getPeers().add(users.get(idx));
                    users.get(idx).getPeers().add(node);
                    break;
                }
            }
        }
        users.add(node);
    }

    @Override
    public String toString() {
        // Printing the graph
        String res = "Network : \n";
        for (int i = 0; i < users.size(); i++)
            res += users.get(i) + " ---> " + users.get(i).getPeers() + "\n";

        return res;
    }
}
