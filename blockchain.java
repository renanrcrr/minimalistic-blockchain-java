import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

class Block {
    private int index;
    private long timestamp;
    private String previousHash;
    private String hash;
    private List<Transaction> transactions = new ArrayList<>();

    public Block(int index, long timestamp, String previousHash, List<Transaction> transactions) {
        this.index = index;
        this.timestamp = timestamp;
        this.previousHash = previousHash;
        this.transactions = transactions;
        this.hash = calculateHash();
    }

    private String calculateHash() {
        String dataToHash = index + previousHash + Long.toString(timestamp) + transactions.toString();
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(dataToHash.getBytes());
            StringBuilder hashString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hashString.append(Integer.toHexString(0xFF & hashByte));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Other getters and setters
}

class Transaction {
    private String from;
    private String to;
    private double amount;

    public Transaction(String from, String to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    // Getters and setters
}

public class Blockchain {
    private List<Block> chain = new ArrayList<>();
    private int difficulty = 2;

    public void addBlock(Block newBlock) {
        if (chain.isEmpty()) {
            newBlock.setPreviousHash("0");
        } else {
            Block previousBlock = chain.get(chain.size() - 1);
            newBlock.setPreviousHash(previousBlock.getHash());
        }
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    // Other methods for validation, mining, etc.
}
