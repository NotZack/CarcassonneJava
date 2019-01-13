package playerControl;

import UIControl.StaticUI;
import aiControl.AIAction;
import gameControl.Board;
import org.jetbrains.annotations.Contract;
import tileControl.Tile;

import java.util.ArrayList;
import java.util.Collections;

public class Turn {

    private static ArrayList<Player> turnOrder = new ArrayList<>();

    private static int turnCounter = 0;

    private static Player currentPlayerTurn;

    private static Tile turnTile;

    public static void init() {
        ArrayList allPlayers = PlayerHandler.getAllPlayers();
        Collections.shuffle(allPlayers);
        turnOrder = allPlayers;

        setTurnTile(Tile.createRandomTile());

        currentPlayerTurn = turnOrder.get(turnCounter);
        Board.executePossiblePlacements(turnTile);
    }

    public static void executeTurn() {
        if (currentPlayerTurn.isHuman())
            return;
        else
            AIAction.calculateAction(currentPlayerTurn);
    }

    public static void passTurn() {
        turnCounter = (turnCounter == turnOrder.size() + 1) ? turnCounter + 1 : turnCounter;
        currentPlayerTurn = turnOrder.get(turnCounter);

        setTurnTile(Tile.createRandomTile());
        StaticUI.updateUI();

        Board.executePossiblePlacements(turnTile);
    }

    private static void setTurnTile(Tile tileToSet) {
        turnTile = tileToSet;
    }

    @Contract(pure = true)
    public static Player getCurrentTurn() {
        return currentPlayerTurn;
    }

    @Contract(pure = true)
    public static Tile getTurnTile() {
        return turnTile;
    }


    @Contract(pure = true)
    public static int getTurnNumber() {
        return turnCounter;
    }
}