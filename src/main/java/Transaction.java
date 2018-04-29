public class Transaction {
	private static int UID = 0;
	private int id;
	private Node sender;
	private Node reciever;
	private int amount;
	private byte [] signature;

	public Transaction(Node sender, Node reciever, int amount) {
		this.id = UID++;
		this.sender = sender;
		this.reciever = reciever;
		this.amount = amount;
	}
	
	public int getId() {
		return id;
	}

	public Node getSender() {
		return sender;
	}

	public void setSender(Node sender) {
		this.sender = sender;
	}

	public Node getReciever() {
		return reciever;
	}

	public void setReciever(Node reciever) {
		this.reciever = reciever;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setSignature(byte [] signature) {
		this.signature = signature;
	}

	@Override
	public boolean equals(Object obj) {
		return this.id == ((Transaction) obj).id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id +" " + amount + " " + sender +" "+ reciever + " " + signature; 
	}

}
