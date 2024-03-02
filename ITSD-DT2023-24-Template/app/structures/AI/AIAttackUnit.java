package structures.AI;

import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public class AIAttackUnit extends UnitAction{
    private MoveableUnit enemyUnit;
    private int actionScore = 7; //adjust this to adjust starting weight

    public AIAttackUnit(MoveableUnit actionTaker, GameState gameState, MoveableUnit enemyUnit){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.enemyUnit = enemyUnit;
        if (isAttackDangerous(enemyUnit)){

        }

    }


}
