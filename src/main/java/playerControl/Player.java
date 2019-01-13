package playerControl;

import meepleControl.MeepleHandler;

public class Player {

    private boolean humanity;

    private static int placedMeeples = 0;
    private static int notPlacedMeeples = 7;

    private int points = 1;

    MeepleHandler playerMeeples = new MeepleHandler();

    static Player createHumanPlayer() {
        Player newHuman = new Player();
        newHuman.humanity = true;
        newHuman.playerMeeples.createMeepleSet();
        return newHuman;
    }

    static Player createAIPlayer() {
        Player newAI = new Player();
        newAI.humanity = false;
        newAI.playerMeeples.createMeepleSet();
        return newAI;
    }

    public int getPlacedMeeplesNum() {
        return placedMeeples;
    }

    public int getNotPlacedMeeplesNum() {
        return notPlacedMeeples;
    }

    boolean isHuman() {
        return humanity;
    }

    public int getPoints() {
        return points;
    }
}
