package meepleControl;

import tileControl.Tile;

import java.util.ArrayList;

public class MeepleHandler {

    private ArrayList<Meeple> meepleSet = new ArrayList<>();

    public void createMeepleSet() {
        for (int i = 0; i < 7; i++) {
            meepleSet.add(new Meeple());
        }
    }

    public void placeMeeple(Tile tileToPlaceUpon) {
        for (int i = 0; i < meepleSet.size(); i++) {
            if (!meepleSet.get(i).isPlaced())
                meepleSet.get(i).placeMeeple(tileToPlaceUpon);
        }
    }

    public void returnMeeple(Tile tileToRemoveFollower) {
        tileToRemoveFollower.removeMeeple().removeFromBoard();
    }
}
