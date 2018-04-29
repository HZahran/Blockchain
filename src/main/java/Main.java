import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.ArrayList;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Main {
	public static ArrayList<Node> users;

	// Joining the network
	public static void join(String userName) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException {
		
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
	
	// Simulating the network
	
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, InvalidAlgorithmParameterException, SignatureException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		
		// Large Network
		String[] names = {"RaniaWael", "Mr.H", "Bebo", "Mariam","Hagar"
				,"Esraa","Hatem","Anwar","Israa","Omar","Soliman","Khaled","Ashraf","Kamola","Sagheer", "Nada", "Yara"};

		// Small Network
		String[] ourTeam = {"Rania","Mariam","H","Baher"}; 
 		
		users = new ArrayList<Node>();
		
		// Joining the network
		for (int i = 0; i < ourTeam.length; i++) 
			join(ourTeam[i]);
		
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
		
		// Creating hard coded transactions
		users.get(1).sendTransaction(users.get(0), 5);
		users.get(2).sendTransaction(users.get(0), 10);
		
		// Printing the announced transactions at each node
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i) + " is having these transactions: " );
			users.get(i).printCurrentBlock();
			System.out.println();
		}
		
	}
}
