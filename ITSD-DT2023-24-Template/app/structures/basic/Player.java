package structures.basic;

import java.util.ArrayList;

/**
 * A basic representation of the Player. A player
 * has health and mana.
 *
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	private ArrayList<Class<? extends Card>> hand = new ArrayList<>();
	private ArrayList<Class<? extends Card>> discardPile = new ArrayList<>();
	private int health;
	private int mana;

	private Hand handObject;

	public Player() {
		this.health = 20;
		this.mana = 0;
		this.handObject = new Hand(this);
	}


	public void setDiscardPile(ArrayList<Class<? extends Card>> discardPile) {
		this.discardPile = discardPile;
	}

	public Hand getHandObject() {
		return handObject;
	}


	public void setHandObject(Hand handObject) {
		this.handObject = handObject;
	}

	public ArrayList<Class<? extends Card>> getDiscardPile() {
		return discardPile;
	}

	public void addToDiscardPile(Class<? extends Card> card) {
		this.discardPile.add(card);
	}

	public ArrayList<Class<? extends Card>> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Class<? extends Card>> hand) {
		this.hand = hand;
	}

	public void addToHand(Class<? extends Card> card) {
		this.getHandObject().drawToHand(card);
	}

	private boolean isCardClass(Class<? extends  Card> card) {
		return Card.class.isAssignableFrom(card);
	}

	public void removeCard(Class<? extends Card> card) {
		this.hand.remove(card);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}
}