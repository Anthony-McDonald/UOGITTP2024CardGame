package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;

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
	private int health;
	private int mana;

	private boolean userOwned;

	public Player(boolean userOwned) {
		super();
		this.health = 20;
		this.mana = 0;
		this.userOwned = userOwned;
	}
	public Player(int health, int mana) {
		super();
		this.health = health;
		this.mana = mana;
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
