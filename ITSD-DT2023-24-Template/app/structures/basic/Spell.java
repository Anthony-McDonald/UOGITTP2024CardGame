package structures.basic;

public class Spell extends Card {
	private boolean isUserOwned;


	public Spell (int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
	}


	@Override
	public int getId() {
		return id;
	}

	@Override
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String getCardname() {
		return cardname;
	}

	@Override
	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	@Override
	public int getManacost() {
		return manacost;
	}

	@Override
	public void setManacost(int manacost) {
		this.manacost = manacost;
	}

	@Override
	public MiniCard getMiniCard() {
		return miniCard;
	}

	@Override
	public void setMiniCard(MiniCard miniCard) {
		this.miniCard = miniCard;
	}

	@Override
	public BigCard getBigCard() {
		return bigCard;
	}

	@Override
	public void setBigCard(BigCard bigCard) {
		this.bigCard = bigCard;
	}

	@Override
	public boolean isCreature() {
		return isCreature;
	}

	@Override
	public void setCreature(boolean creature) {
		isCreature = creature;
	}

	public boolean isUserOwned() {
		return isUserOwned;
	}

	public void setUserOwned(boolean userOwned) {
		isUserOwned = userOwned;
	}

	public void spellEffect(MoveableUnit unit){
		
	}

	public void spellEffect(Tile tile) {

	}
	
	public void spellEffect(){
		
	}
}
