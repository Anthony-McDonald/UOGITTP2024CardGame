package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Avatar;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.OpeningGambit;
import structures.basic.Tile;
import structures.basic.Unit;
/**
* This is the class for Silverguard Squire's implementation
*/
public class SilverguardSquire extends Creature implements OpeningGambit{

    public SilverguardSquire(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 1;
        this.currentHealth = 1;
        this.maxHealth = currentHealth;
    }
    /**
     * The openingGambit method increase the health and attack of the AI's avatar by 1
     * The method will be triggered if the AI's avatar in front or behind of Silverguard Squire
     * @param out
     * @param gameState
     */
	@Override
	public void openingGambit(ActorRef out, GameState gameState) {
		Avatar ownerAvatar = gameState.getPlayer2().getAvatar();

        // Get the tile of the AI's avatar
        Tile ownerAvatarTile = ownerAvatar.getTile();

        // Check if there's a unit directly in front (left) of the avatar
        Tile leftAdjacentTile = gameState.getBoard().getTile(ownerAvatarTile.getTilex() - 1, ownerAvatarTile.getTiley());
        if (leftAdjacentTile != null && leftAdjacentTile.getUnit() != null  &&
                !leftAdjacentTile.getUnit().isUserOwned()) {
            leftAdjacentTile.getUnit().setAttack(leftAdjacentTile.getUnit().getAttack() + 1, out);
            leftAdjacentTile.getUnit().setMaxHealth(leftAdjacentTile.getUnit().getMaxHealth() + 1);
            System.out.println("Silverguard Squire Opening Gambit Triggered");
        }

        // Check if there's a unit directly behind (right) of the avatar
        Tile rightAdjacentTile = gameState.getBoard().getTile(ownerAvatarTile.getTilex() + 1, ownerAvatarTile.getTiley());
        if (rightAdjacentTile != null && rightAdjacentTile.getUnit() != null &&
                !rightAdjacentTile.getUnit().isUserOwned()) {
            rightAdjacentTile.getUnit().setAttack(rightAdjacentTile.getUnit().getAttack() + 1, out);
            rightAdjacentTile.getUnit().setMaxHealth(rightAdjacentTile.getUnit().getMaxHealth() + 1);
            System.out.println("Silverguard Squire Opening Gambit Triggered");
        }
    }

    @Override
    public void summon(ActorRef out, Tile tile, GameState gameState){
        super.summon(out,tile,gameState);
        this.openingGambit(out,gameState);
    }
}
