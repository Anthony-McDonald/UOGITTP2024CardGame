package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import utils.OrderedCardLoader;

import java.util.ArrayList;
import java.util.Collections;
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
    private CardConverter cardConverterObject;

	private final OrderedCardLoader orderedCardLoader = new OrderedCardLoader();

	public Player(boolean userOwned) {
		super();
		this.health = 20;
		this.mana = 0;
        this.userOwned = userOwned;
        this.cardConverterObject = new CardConverter(this);

		if (userOwned) {
			this.playerDeck = OrderedCardLoader.getPlayer1Cards(2);
		} else {
			this.playerDeck = OrderedCardLoader.getPlayer2Cards(2);
		}

		this.playerDeck = shuffleCards(convertDeck());

		int startingHandSize = 3;
		for (int i = 0; i < startingHandSize; i++) {
			drawCard();
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

	private List<Card> convertDeck() {
		List<Card> internalList = this.getPlayerDeck();

		for (int i = 0; i < this.getPlayerDeck().size(); i++) {
			internalList.set(i, this.getHandObject().cardDifferentiator(internalList.get(i)));
		}

		return internalList;
	}

	private List<Card> shuffleCards(List<Card> cardList) {
//		System.out.println("BEFORE SHUFFLE");
//		for (Card card : cardList) {
//			System.out.println(card);
//		}
		List<Card> internalList = cardList;
		Collections.shuffle(internalList);
		return internalList;
	}

	public void printDeck() {
		for (Card card : this.getPlayerDeck()) {
			if (card != null) {
				System.out.println(card.getClass() + " main ");
			} else {
				System.out.println("card is null");
			}
		}
	}

	public List<Card> getPlayerDeck() {
		return playerDeck;
	}

	public void setPlayerDeck(List<Card> playerDeck) {
		this.playerDeck = playerDeck;
	}

	public CardConverter getHandObject() {
		return cardConverterObject;
	}


	public void setHandObject(CardConverter cardConverterObject) {
		this.cardConverterObject = cardConverterObject;
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

	public void drawCard() {
		if (this.getPlayerDeck().size() > 0 ) {
			Card cardAtTopOfDeck = this.getPlayerDeck().get(this.getPlayerDeck().size() - 1);

			if (this.getHand().size() < 5) {
				this.getHand().add(cardAtTopOfDeck);
			} else {
				this.getDiscardPile().add(cardAtTopOfDeck);
			}
			this.getPlayerDeck().remove(this.getPlayerDeck().size() - 1);
		} else {
			System.out.println("deck is empty");
		}

	}

	public void printHand() {
		System.out.println("===HANDPRINT===");
		for (Card card : this.getHand()) {
			System.out.println(card);
		}
		System.out.println("===HANDPRINT-DONE===");
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