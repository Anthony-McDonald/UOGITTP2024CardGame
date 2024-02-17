package structures.basic;

import java.util.ArrayList;
/**
 * A basic representation of of the Player. A player
 * has health and mana.
 *
 * @author Dr. Richard McCreadie
 *
 */
public class Player {

	private ArrayList<Card> hand = new ArrayList<Card>();
	private ArrayList<Card> discardPile = new ArrayList<Card>();
	private int health;
	private int mana;

	public Player() {
		super();
		this.health = 20;
		this.mana = 0;
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
	}

	public ArrayList<Card> getDiscardPile() {
		return discardPile;
	}

	public void addToDiscardPile(Card card) {
		this.getDiscardPile().add(card);
	}

	public ArrayList<Card> getHand() {
		return hand;
	}

	public void setHand(ArrayList<Card> hand) {
		this.hand = hand;
	}
	public void addToHand(Card card) {
		this.getHand().add(card);
	}

	public void removeCard(Card card) {
		this.getHand().remove(card);
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
