package meepleControl;

import gameControl.Board;
import javafx.scene.image.ImageView;
import tileControl.Tile;


public class Meeple extends ImageView {

    private boolean placed = false;

    public void placeMeeple(Tile tileToPlaceUpon) {
        placed = true;
    }

    public void removeFromBoard() {
        placed = false;
        Board.removeMeeple(this);
    }

    public boolean isPlaced() {
        return placed;
    }
}
