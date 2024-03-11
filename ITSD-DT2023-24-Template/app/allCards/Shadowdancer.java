package allCards;

import java.util.ArrayList;


import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Avatar;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.Deathwatch;
import structures.basic.MiniCard;
import structures.basic.Unit;
import structures.basic.*;
/**
* This is the class for Shadowdancer's implementation
*/
public class Shadowdancer extends Creature implements Deathwatch{

//had to make a reference to avatar as I dont want to change the signature of the Deathwatch interface method

    public Shadowdancer(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
		this.userOwned = true;
		this.attack = 5;
		this.currentHealth = 4;
		this.maxHealth = currentHealth;
    }
    /**
     * The deathWatch method deal 1 damage to the aiAvatar and heal playerAvater for 1
     * @param out
     * @param gameState
     */
	@Override
	public void deathWatch(ActorRef out, GameState gameState) {
		// TODO Auto-generated method stub
		
	  
	    Avatar playerAvatar = gameState.getPlayer1().getAvatar();
	   
	    Avatar aiAvatar = gameState.getPlayer2().getAvatar();

	    // Deal 1 damage to the AI's avatar
	    int newAiAvatarHealth = aiAvatar.getCurrentHealth() - 1;
	    aiAvatar.setCurrentHealth(newAiAvatarHealth, out, gameState);

	    // Heal the player's avatar by 1 HP
	    int newPlayerAvatarHealth = playerAvatar.getCurrentHealth() + 1;
	    playerAvatar.setCurrentHealth(newPlayerAvatarHealth, out, gameState);
	    
	    System.out.println("Shadowdancer Deathwatch Activated");
		
	    
	    //Not sure if my way above is better or not.
		/*int newHealth;
		MoveableUnit avatar = null;
		ArrayList<MoveableUnit>AiUnits = gameState.getBoard().friendlyUnits(false);
		for(MoveableUnit unit: AiUnits) {
			if(unit instanceof Avatar) {
				avatar = unit;
			}
		}
		newHealth = avatar.getCurrentHealth()-1;
		avatar.setCurrentHealth(newHealth, out, gameState); //NEED TO CHANGE NULL REFERENCES
		
		//need AI class done so i can -1 health to its avatar
		*/
	}
}
