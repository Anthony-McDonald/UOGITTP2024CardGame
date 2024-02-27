package structures.AI;

import structures.basic.Tile;

public abstract class UnitAction {
    Tile targetTile;
    boolean isActionPossible;

    public Tile getTargetTile() {
        return targetTile;
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
    }

    public boolean isActionPossible() {
        return isActionPossible;
    }

    public void setActionPossible(boolean actionPossible) {
        isActionPossible = actionPossible;
    }
}
