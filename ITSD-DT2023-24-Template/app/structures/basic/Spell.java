package structures.basic;

public class Spell extends Card {

	int id;
	String cardname;
	int manacost;
	MiniCard miniCard;
	BigCard bigCard;
	boolean isCreature;
	boolean isUserOwned;

	public Spell (int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, boolean isUserOwned) {
		this.id = id;
		this.cardname = cardname;
		this.manacost = manacost;
		this.miniCard = miniCard;
		this.bigCard = bigCard;
		this.isCreature = isCreature;
		this.isUserOwned = isUserOwned;
	}



	public void spellEffect(MoveableUnit unit){
		
	}
	
	public void spellEffect(){
		
	}
}
