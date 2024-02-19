package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import org.checkerframework.checker.units.qual.C;
import utils.OrderedCardLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A basic representation of the Player. A player
 * has health and mana.
 *
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	private ArrayList<Card> hand = new ArrayList<>();
	private ArrayList<Card> discardPile = new ArrayList<>();

	private List<Card> playerDeck = new ArrayList<>();
	private int health;
	private int mana;
    private boolean userOwned;
    private Hand handObject;

	private OrderedCardLoader orderedCardLoader = new OrderedCardLoader();

	public Player(boolean userOwned) {
		super();
		this.health = 20;
		this.mana = 0;
        this.userOwned = userOwned;
        this.handObject = new Hand(this);

		if (userOwned) {
			this.playerDeck = OrderedCardLoader.getPlayer1Cards(1);
		} else {
			this.playerDeck = OrderedCardLoader.getPlayer2Cards(1);
		}
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
	}

    public void setDiscardPile(ArrayList<Card> discardPile) {
        this.discardPile = discardPile;
    }

	public void printDeck() {
		for (Card card : this.getPlayerDeck()) {
			System.out.println(card.getCardname());
		}
	}

	public List<Card> getPlayerDeck() {
		return playerDeck;
	}

	public void setPlayerDeck(List<Card> playerDeck) {
		this.playerDeck = playerDeck;
	}

	public Hand getHandObject() {
		return handObject;
	}


	public void setHandObject(Hand handObject) {
		this.handObject = handObject;
	}

	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}

	public void addToDiscardPile(Card card) {
		this.discardPile.add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}

	public void addToHand(Card card) {
		this.getHandObject().drawToHand(card);
	}


	public void removeCard(Card card) {
		this.hand.remove(card);
	}

	public int getHealth() {
		return health;
	}
	public void setHealth(int health, ActorRef out) {
		this.health = health;
		if (this.userOwned){ //if human
			BasicCommands.setPlayer1Health(out, this);
		}else{
			BasicCommands.setPlayer2Health(out, this);
		}
	}

	public int getMana() {
		return mana;
	}
	public void setMana(int mana, ActorRef out) {
		if (mana>9){ //to prevent player mana from increasing over 9.
			mana = 9;
		}
		this.mana = mana;
		if (this.userOwned){
			BasicCommands.setPlayer1Mana(out, this);
		}else{
			BasicCommands.setPlayer2Mana(out, this);
		}
	}

	public boolean isUserOwned() {
		return userOwned;
	}

	public void setUserOwned(boolean userOwned) {
		this.userOwned = userOwned;
	}
}