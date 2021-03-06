package persons;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import Paillier.Paillier;
import Paillier.PaillierPublicKey;
import RSA.RSA;
import RSA.RSAKey;
import RSA.RSAPrivateKey;
import RSA.RSAPublicKey;

public class Alice {
	
	//public questions
	public final String Q0 = "?";
	public final String Q1 = "How many ?";
	public final String Q3 = "How long ?";
	public final String Q2 = "How ??";
	public final String Q4 = "How many ?!";
	public final String Q5 = "How many ???";
	public final String Q6 = "Why ?";
	public final String Q7 = "How long ?!";
	public final String Q8 = "How long ???";
	public final String Q9 = "Joker ?";
	public final List<String> questionsList = new ArrayList<String>();
	
	//private answers
	private final BigInteger A0 = new BigInteger("23");
	private final BigInteger A1 = new BigInteger("5");
	private final BigInteger A2 = new BigInteger("8");
	private final BigInteger A3 = new BigInteger("34");
	private final BigInteger A4 = new BigInteger("3");
	private final BigInteger A5 = new BigInteger("4");
	private final BigInteger A6 = new BigInteger("1");
	private final BigInteger A7 = new BigInteger("30");
	private final BigInteger A8 = new BigInteger("12");
	private final BigInteger A9 = new BigInteger("22");
	private final List<BigInteger> answersList = new ArrayList<BigInteger>();
	public List<BigInteger> cryptedAnswersList;
	
	public PaillierPublicKey publicKey;
	private Paillier cryptosytem;
	private BigInteger cryptedBobsChoice;
	
	public Alice(PaillierPublicKey publicKey, BigInteger cryptedBobsChoice) {
		initLists();
		this.cryptedBobsChoice = cryptedBobsChoice;
		this.publicKey = publicKey;
		cryptosytem = new Paillier();
		cryptedAnswersList = new ArrayList<BigInteger>();
		
		for(int i = 0; i < answersList.size(); i++) {
			cryptedAnswersList.add( cryptosytem.Encrypt(publicKey, answersList.get(i)) );
		}
	}
	
	public List<BigInteger> maskAnswers() {
		List<BigInteger> maskedAnswersList = new ArrayList<BigInteger>();
		BigInteger k = new BigInteger("0");
		BigInteger n = publicKey.getN();
		for(BigInteger cryptedAnswer : cryptedAnswersList) {
			BigInteger M = cryptedBobsChoice.multiply( cryptosytem.Encrypt(publicKey, k.negate()) );
			BigInteger rand = new BigInteger("" + Math.round(Math.random() * 500000));
			rand = rand.mod(n);
			M = M.modPow(rand, n.pow(2));
			BigInteger maskedAnswer = M.multiply(cryptedAnswer).mod(n.pow(2));
			maskedAnswersList.add(maskedAnswer);
			k = k.add(new BigInteger("1"));
		}
		return maskedAnswersList;
	}
	
	private void initLists() {
		questionsList.add(Q0);
		questionsList.add(Q1);
		questionsList.add(Q2);
		questionsList.add(Q3);
		questionsList.add(Q4);
		questionsList.add(Q5);
		questionsList.add(Q6);
		questionsList.add(Q7);
		questionsList.add(Q8);
		questionsList.add(Q9);

		answersList.add(A0);
		answersList.add(A1);
		answersList.add(A2);
		answersList.add(A3);
		answersList.add(A4);
		answersList.add(A5);
		answersList.add(A6);
		answersList.add(A7);
		answersList.add(A8);
		answersList.add(A9);
	}
}