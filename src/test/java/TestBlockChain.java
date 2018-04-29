import org.junit.BeforeClass;

import java.util.ArrayList;

public class TestBlockChain {

    @BeforeClass
    public static void setup(){
        Main main = new Main;
        // Small Network
        String[] ourTeam = {"Rania","Mariam","H","Baher"};

        main.users = new ArrayList<Node>();

        // Joining the network
        for (int i = 0; i < ourTeam.length; i++)
            main.join(ourTeam[i]);

        // Printing the graph
        System.out.println("Connections..");
        System.out.println();
        for (int i = 0; i < users.size(); i++) {
            String res = users.get(i) + " ---> " + users.get(i).getPeers();
            System.out.println(res);
        }

        System.out.println();
        System.out.println("------------------------");
        System.out.println();
    }
}
