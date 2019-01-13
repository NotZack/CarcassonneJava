package playerControl;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class PlayerHandler {

    private static int numberOfHumanPlayers = 1;
    private static int numberOfAIPlayers = 0;

    private static ArrayList<Player> allPlayers = new ArrayList<>();

    public static void init() {
        for (int i = 0; i < numberOfHumanPlayers; i++) {
            allPlayers.add(Player.createHumanPlayer());
        }
        for (int j = 0; j < numberOfAIPlayers; j++) {
            allPlayers.add(Player.createAIPlayer());
        }
    }

    @Contract(pure = true)
    static ArrayList getAllPlayers() {
        return allPlayers;
    }
}
