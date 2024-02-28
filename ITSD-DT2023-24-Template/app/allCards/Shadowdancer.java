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
public class Shadowdancer extends Creature implements Deathwatch{

//had to make a reference to avatar as I dont want to change the signature of the Deathwatch interface method

    public Shadowdancer(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
		this.userOwned = true;
		this.attack = 5;
		this.currentHealth = 4;
		this.maxHealth = currentHealth;
    }

	@Override
	public void deathWatch(ActorRef out, GameState gameState) {
		// TODO Auto-generated method stub
		int newHealth;
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
		
	}
}
