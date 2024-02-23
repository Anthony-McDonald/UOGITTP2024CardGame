package allCards;

import commands.BasicCommands;
import structures.basic.Avatar;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.Deathwatch;
import structures.basic.MiniCard;
import structures.basic.Unit;

public class Shadowdancer extends Creature implements Deathwatch{
private Avatar avatar;
//had to make a reference to avatar as I dont want to change the signature of the Deathwatch interface method

    public Shadowdancer(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

	@Override
	public void deathWatch() {
		// TODO Auto-generated method stub
		int newHealth = avatar.getCurrentHealth()+1;
		avatar.setCurrentHealth(newHealth, null);
		
		//need AI class done so i can -1 health to its avatar
		
	}
}
