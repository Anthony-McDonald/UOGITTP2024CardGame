package structures.AI;

import structures.basic.Tile;

/**
 * This is a wrapper class that wraps a Tile. It implements comparable to allow for Tiles to be sorted
 * based on their distance from the AI avatar.
 */
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
