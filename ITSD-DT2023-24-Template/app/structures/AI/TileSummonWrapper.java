package structures.AI;

import structures.basic.Tile;

public class TileSummonWrapper implements Comparable<TileSummonWrapper> {
    private double distanceAIAvatar;
    private Tile tile;
    public TileSummonWrapper(Tile tile, double distanceAIAvatar){
        this.tile = tile;
        this.distanceAIAvatar = distanceAIAvatar;
    }

    @Override
    public int compareTo(TileSummonWrapper other) {
        return Double.compare(this.distanceAIAvatar, other.getDistanceAIAvatar());
    }

    public double getDistanceAIAvatar() {
        return distanceAIAvatar;
    }

    public Tile getTile() {
        return tile;
    }
}
