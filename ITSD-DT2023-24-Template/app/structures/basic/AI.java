package structures.basic;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Avatar;
import java.util.ArrayList;

public class AI extends Player  {
	private ArrayList<Class<? extends Card>> hand = new ArrayList<>();
	private ArrayList<Class<? extends Card>> discardPile = new ArrayList<>();
	private int health;
	private int mana;
    private boolean userOwned;
    
	public AI(int health, int mana) {
		super(health, mana);
	}
	
	public AI(boolean userOwned) {
		super(userOwned);
	}
	public void aiMoved(ActorRef out) {
		BasicCommands.moveUnitToTile(out, null, null);
	}
	

	

}
