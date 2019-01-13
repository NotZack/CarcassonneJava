package playerControl;

import gameControl.Board;

public class CameraControl {

    private static final int MOVEMENTOFFSET = 10;

    private static boolean
            north = false,
            south = false,
            east = false,
            west = false;

    /**
     * Updates the camera movement based off of the direction set to true.
     */
    public static void updateCamera() {
        //DecideActions
        if (north) Board.getBoardNode().setTranslateY(Board.getBoardNode().getTranslateY() + MOVEMENTOFFSET);
        if (east) Board.getBoardNode().setTranslateX(Board.getBoardNode().getTranslateX() - MOVEMENTOFFSET);
        if (south) Board.getBoardNode().setTranslateY(Board.getBoardNode().getTranslateY() - MOVEMENTOFFSET);
        if (west) Board.getBoardNode().setTranslateX(Board.getBoardNode().getTranslateX() + MOVEMENTOFFSET);
    }

    static void setNorth(boolean moving) {
        north = moving;
    }

    static void setEast(boolean moving) {
        east = moving;
    }

    static void setSouth(boolean moving) {
        south = moving;
    }

    static void setWest(boolean moving) {
        west = moving;
    }
}
