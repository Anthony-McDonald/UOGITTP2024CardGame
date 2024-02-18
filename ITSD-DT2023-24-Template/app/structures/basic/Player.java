package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;

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
    private boolean userOwned;
    private Hand handObject;

	public Player(boolean userOwned) {
		super();
		this.health = 20;
		this.mana = 0;
        this.userOwned = userOwned;
        this.handObject = new Hand(this);
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
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